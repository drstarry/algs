/****************************************************************************

Percolation algorithm.

prepare for data and basic operation of the grid

(Corner cases.  By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.
The constructor should take time proportional to N^2)

****************************************************************************/

import java.lang.*;

public class Percolation {

    public int[][] grid; // the grid, percolation model
    public WeightedQuickUnionUF gridConnection; // the connection model
    public int n; // N

    /*
    create N-by-N grid, with all sites blocked
    */
    public Percolation(int N) {
        if (N<=0) {
            throw new IllegalArgumentException();
        }
        n = N;
        System.out.println(n);
        //gridConnection = new WeightedQuickUnionUF(N*N+2); //0 is top virtual site, N^2+1 is bottom virtual site
        for (int i=1; i<=N; i++)
            for (int j=1; j<=N; j++)
            {
                grid[i-1][j-1] = 0;
            }
    }

    /*
    return the  (i, j) of the site given its number
    */
    public int[] mapToIndex(int siteID) {
        int[] site = {0,0};
        int i, j;
        i = siteID%n;
        j = (siteID-i)/n;
        site[0] = i+1;
        site[1] = j+1;
        return site;
    }

    /*
    return the siteId of the site given its (i, j)
    */
    public int mapToId(int i, int j) {
        return (i-1)*n+j-1;
    }

    /*
    return a site's neighber nites, 3 or 4 sites, top, bottom, left, right respectively
    */
    public int[] neighbers(int i, int j) {
        int[] neighbers = {0, 0, 0, 0};
        neighbers[0] = (i==1)?0:mapToId(i-1, j);
        neighbers[1] = (i==n)?(n*n+1):mapToId(i+1, j);
        neighbers[2] = (j==1)?-1:mapToId(i, j-1);
        neighbers[3] = (j==n)?-1:mapToId(i, j+1);
        return neighbers;
    }

    /*
    open site (row i, column j) if it is not open already
    connect the sites surround it
    */
    public void open(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        if (grid[i][j]!=1) {
            grid[i][j] = 1;
            int[] neighbers = neighbers(i, j);
            //for (int index=0; index<4; index++) {

            //}
        }
    }

    /*
     site (row i, column j) open?
    */
    public boolean isOpen(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        return (grid[i][j] == 1);
    }

    /*
    is site (row i, column j) full?
    */
    public boolean isFull(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        return (grid[i][j] == 0);
    }

    /*
    does the system percolate?
    check if the virtual top site and virtual bottom site connected
    */
    public boolean percolates()
    {
        return false;
    }

    public static void main(String[] args) {
        //int N = args[1];
        Percolation p = new Percolation(5);
        System.out.println(p.mapToId(1,4));
        System.out.println(p.mapToIndex(7));

    }
}
