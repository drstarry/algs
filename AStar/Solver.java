// Also, create an immutable data type Solver with the following API:
// To implement the A* algorithm, you must use the MinPQ data type from algs4.jar for the priority queue(s).
// Corner cases.  The constructor should throw a java.lang.NullPointerException if passed a null argument.

import java.lang.*;
import java.util.*;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)

    private class GameTree {
        private MinPQ<SearchNode> priorityPQ; // a minpq to track search node
        private Map<String, String> comeFrom; // a map to track the optimal path
        private Map<String, Integer> costSoFar; // a list to track current moves
        private int moveNum;
        private String goal;
        private boolean isSolvable;
        private Map<String, Board> boardMap;

        public GameTree(Board initial) {
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
        }

        public boolean aStar() {
            while (!priorityPQ.isEmpty()) {
                SearchNode current = priorityPQ.delMin();
                Board curBoard = current.getCurBoard();

                if (curBoard.isGoal()) {
                    goal = curBoard.toString().intern();
                    moveNum = current.getCurMove();
                    isSolvable = true;
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
            }
            return isSolvable;
        }

        public int minStep() {
            if (isSolvable)
                return moveNum;
            else
                return -1;
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

    private class SearchNode implements Comparable<SearchNode> {
        private Board current;
        private Integer priority;
        private Integer moveNum;

        public SearchNode(Board current, int priority, int moveNum)
        {
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

        @Override
        public String toString() {
            return priority+" ";
        }

        @Override
        public boolean equals(Object n) {
            return this.toString() == n.toString();
        }

    }

    private int moveMin;
    private List<Board> solutionTree;
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException();
        }
        Board initialTwin = initial.twin();

        GameTree tree = new GameTree(initial);
        tree.aStar();
        moveMin = tree.minStep();
        solutionTree = tree.boardTree();

        // I don't even know why we use such twin??
        // GameTree treeTwin = new GameTree(initialTwin);
        // treeTwin.aStar();
    }

    //is the initial board solvable?
    public boolean isSolvable() {
        return moveMin != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moveMin;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionTree;
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
