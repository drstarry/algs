// Board and Solver data types. Organize your program by creating an immutable data type Board with the following API:
// Corner cases.  You may assume that the constructor receives an N-by-N array containing the N^2 integers between 0 and N^2 − 1, where 0 represents the blank square.

// Performance requirements.  Your implementation should support all Board methods in time proportional to N^2 (or better) in the worst case.

import java.lang.Math;
import java.util.*;

public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)

    private int[][] board;
    private int N;
    private Set<String> steps;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
        N = blocks[0].length;
        board = new int[N][N];
        steps = new HashSet<String>();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                board[i][j] = blocks[i][j];
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int num = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (board[i][j] != 0 && !self(i, j)) {
                    num ++;
                }
        return num;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                if (board[i][j] != 0 && !self(i, j)) {
                    int _i = (board[i][j] - 1)/dimension();
                    int _j = board[i][j] - _i*dimension() - 1;
                    int dis = Math.abs(i-_i) + Math.abs(j-_j);
                    distance += dis;
                }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal()  {
        return manhattan() == 0;
    }

    private boolean self(int i, int j) {
        return board[i][j] == i * dimension() + j + 1;
    }

    // a boad that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] twin_board = new int[dimension()][dimension()];
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension(); j++)
                twin_board[i][j] = board[i][j];
        for (int i = 0; i < dimension(); i++)
            for (int j = 0; j < dimension()-1; j++) {
                if (twin_board[i][j]!=0 && twin_board[i][j+1]!=0) {
                    int temp = twin_board[i][j];
                    twin_board[i][j] = twin_board[i][j+1];
                    twin_board[i][j+1] = temp;
                    return new Board(twin_board);
                }
            }
        return new Board(twin_board);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            throw new NullPointerException();
        }
        return toString().equals(y.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new boardIterable(board);
    }

    @SuppressWarnings("hiding")
    private class boardIterable implements Iterable<Board> {

        private List<Board> boardNeighbors = new ArrayList<Board>();

        public boardIterable(int[][] board) {
            int i0=dimension()-1, j0=dimension()-1;
            for (int i = 0; i < dimension(); i++)
                for (int j = 0; j < dimension(); j++) {
                    if (board[i][j] == 0) {
                        i0 = i;
                        j0 = j;
                        break;
                    }
                }
            getNeighbors(i0, j0, board);
        }

        @Override
        public Iterator<Board> iterator() {
            return boardNeighbors.iterator();
        }

        private void getNeighbors(int i0, int j0, int[][] board) {
            int N = dimension();
            List<int[]> blockNeighbors = new ArrayList<int[]>();
            if (i0-1 >= 0) {
                blockNeighbors.add(new int[]{i0-1,j0});
            }
            if (i0+1 < N) {
                blockNeighbors.add(new int[]{i0+1,j0});
            }
            if (j0-1 >= 0) {
                blockNeighbors.add(new int[]{i0,j0-1});
            }
            if (j0+1 < N) {
                blockNeighbors.add(new int[]{i0,j0+1});
            }
            for (int[] block: blockNeighbors) {
                int _i = block[0];
                int _j = block[1];
                int[][] blocks = new int[N][N];
                for (int i=0; i<N; i++)
                    for (int j=0; j<N; j++) {
                        blocks[i][j] = board[i][j];
                    }
                int temp = blocks[_i][_j];
                blocks[_i][_j] = blocks[i0][j0];
                blocks[i0][j0] = temp;
                Board b = new Board(blocks);
                if (!steps.contains(b.toString()))
                    boardNeighbors.add(b);
            }
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        String str = dimension() + "\n";
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                str += board[i][j];
                str += (j==dimension()-1)?"\n":"\t";
            }
        }
        return str;
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        Board board = new Board(blocks);
        StdOut.println(board);
        StdOut.println(board.twin());
        // for (Board b: board.neighbors()) {
        //     for (Board bb: b.neighbors()) {
        //         StdOut.println("!!!");
        //         StdOut.println(bb.manhattan());
        //         StdOut.print(bb);
        //     }
        // }
    }
}
