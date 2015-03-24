// Also, create an immutable data type Solver with the following API:
// To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue(s).
// Corner cases.  The constructor should throw a java.lang.NullPointerException if passed a null argument.

import java.lang.*;
import java.util.*;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    @SuppressWarnings("hiding")
    private class GameTree {
        private MinPQ<SearchNode> priorityPQ; // a minpq to track search node
        private Map<String, String> comeFrom; // a map to track the optimal path
        private Map<String, Integer> costSoFar; // a list to track current moves
        private int moveNum;
        private String goal;
        private boolean isSolvable;
        private Map<String, Board> boardMap;

        private MinPQ<SearchNode> priorityPQTwin; // a minpq to track search node
        private Map<String, String> comeFromTwin; // a map to track the optimal path
        private Map<String, Integer> costSoFarTwin; // a list to track current moves
        private int moveNumTwin;
        private String goalTwin;
        private boolean isSolvableTwin;
        private Map<String, Board> boardMapTwin;

        public GameTree(Board initial, Board initialTwin) {
            if (initial == null || initialTwin == null) {
                throw new NullPointerException();
            }
            moveNum = -1;
            comeFrom = new HashMap<String, String>();
            costSoFar = new HashMap<String, Integer>();
            priorityPQ = new MinPQ<SearchNode>();
            boardMap = new HashMap<String, Board>();
            SearchNode start = new SearchNode(initial, 0, 0);
            priorityPQ.insert(start);
            costSoFar.put(initial.toString().intern(), 0);
            isSolvable = false;
            String first = initial.toString().intern();
            boardMap.put(first, initial);
            comeFrom.put(first, null);

            moveNumTwin = -1;
            comeFromTwin = new HashMap<String, String>();
            costSoFarTwin = new HashMap<String, Integer>();
            priorityPQTwin = new MinPQ<SearchNode>();
            boardMapTwin = new HashMap<String, Board>();
            SearchNode startTwin = new SearchNode(initialTwin, 0, 0);
            priorityPQTwin.insert(startTwin);
            costSoFarTwin.put(initialTwin.toString().intern(), 0);
            isSolvableTwin = false;
            String firstTwin = initialTwin.toString().intern();
            boardMapTwin.put(firstTwin, initialTwin);
            comeFromTwin.put(firstTwin, null);
        }

        public void aStar() {
            while (!priorityPQ.isEmpty() && !priorityPQTwin.isEmpty()) {
                SearchNode current = priorityPQ.delMin();
                Board curBoard = current.getCurBoard();
                SearchNode currentTwin = priorityPQTwin.delMin();
                Board curBoardTwin = currentTwin.getCurBoard();

                if (curBoard.isGoal()) {
                    goal = curBoard.toString().intern();
                    moveNum = current.getCurMove();
                    isSolvable = true;
                    break;
                }
                if (curBoardTwin.isGoal()) {
                    goalTwin = curBoardTwin.toString().intern();
                    moveNumTwin = currentTwin.getCurMove();
                    isSolvableTwin = true;
                    break;
                }

                int curMove = current.getCurMove() + 1;
                for (Board nextBoard: curBoard.neighbors()) {

                    if (costSoFar.get(nextBoard.toString().intern())==null || curMove < costSoFar.get(nextBoard.toString().intern())) {

                        int priority = nextBoard.manhattan() + curMove;
                         // + nextBoard.hamming();
                        SearchNode next = new SearchNode(nextBoard, priority, curMove);
                        priorityPQ.insert(next);
                        comeFrom.put(nextBoard.toString().intern(), curBoard.toString().intern());
                        costSoFar.put(nextBoard.toString().intern(), curMove);

                        boardMap.put(nextBoard.toString().intern(), nextBoard);

                    }
                }

                int curMoveTwin = currentTwin.getCurMove() + 1;
                for (Board nextBoardTwin: curBoardTwin.neighbors()) {

                    if (costSoFarTwin.get(nextBoardTwin.toString().intern())==null || curMoveTwin < costSoFarTwin.get(nextBoardTwin.toString().intern())) {

                        int priorityTwin = nextBoardTwin.manhattan() + curMoveTwin;
                         // + nextBoard.hamming();
                        SearchNode nextTwin = new SearchNode(nextBoardTwin, priorityTwin, curMoveTwin);
                        priorityPQTwin.insert(nextTwin);
                        comeFrom.put(nextBoardTwin.toString().intern(), curBoardTwin.toString().intern());
                        costSoFarTwin.put(nextBoardTwin.toString().intern(), curMove);

                        boardMapTwin.put(nextBoardTwin.toString().intern(), nextBoardTwin);

                    }
                }
            }
        }

        public int minStep() {
            if (isSolvable)
                return moveNum;
            else
                return -1;
        }

        public boolean isSolvable() {
            return isSolvable;
        }

        public List<Board> boardTree() {
            List<Board> tree = new ArrayList<Board>();
            String cur = goal;
            while (cur != null) {
                tree.add(boardMap.get(cur));
                cur = comeFrom.get(cur);
            }
            Collections.reverse(tree);
            return tree;
        }

    }

    @SuppressWarnings("hiding")
    private class SearchNode implements Comparable<SearchNode> {
        private Board current;
        private Integer priority;
        private Integer moveNum;

        public SearchNode(Board current, int priority, int moveNum)
        {
            if (current == null) {
                throw new NullPointerException();
            }

            this.current = current;
            this.priority = priority;
            this.moveNum = moveNum;
        }

        public Board getCurBoard() {
            return current;
        }

        public int getCurMove() {
            return moveNum;
        }

        public int getPriority() {
            return priority;
        }

        @Override
        public int compareTo(SearchNode n) {
            if(this.priority > n.priority) {
                return 1;
            }
            else if(this.priority < n.priority) {
                return -1;
            }
            return 0;
        }

    }

    private int moveMin;
    private List<Board> solutionTree;
    private boolean isSolvable;
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        Board initialTwin = initial.twin();

        GameTree tree = new GameTree(initial, initialTwin);
        tree.aStar();
        isSolvable = tree.isSolvable();
        moveMin = tree.minStep();
        solutionTree = tree.boardTree();

        // I don't even know why we use such twin??
        // GameTree treeTwin = new GameTree(initialTwin);
        // treeTwin.aStar();
    }

    //is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moveMin;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable)
            return solutionTree;
        else
            return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
