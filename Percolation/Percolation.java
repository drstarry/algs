/****************************************************************************
Percolation algorithm.

Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

    Initialize all sites to be blocked.

    Repeat the following until the system percolates:

        Choose a site (row i, column j) uniformly at random among all blocked sites.

        Open the site (row i, column j).

    The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.
 *
 ****************************************************************************/

import MyUtils.*;

public class Percolation {

    public int[][] grid; // the grid

    /*
    create N-by-N grid, with all sites blocked
    (Corner cases.  By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.
    The constructor should take time proportional to N^2)
    */
    public Percolation(int N) {
        for (int i=1; i<=N; i++)
            for (int j=1; j<=N; j++)
            {
                grid[i][j] = 0;
            }
    }

    /*
    open site (row i, column j) if it is not open already
    */
    public void open(int i, int j) {
        if (grid[i][j]!=1) {
            grid[i][j] = 1;
        }
   }

    /*
    is site (row i, column j) open?
    */
    public boolean isOpen(int i, int j) {
        return (grid[i][j] == 1);
    }

    /*
    is site (row i, column j) full?
    */
    public boolean isFull(int i, int j) {
        return (grid[i][j] == 0);
    }

    /*
    does the system percolate?
    */
    public boolean percolates()
    {
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Hello, World");
    }
}
