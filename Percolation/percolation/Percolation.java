/****************************************************************************

Percolation algorithm.

prepare for data and basic operation of the grid

(Corner cases.  By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.
The constructor should take time proportional to N^2)

****************************************************************************/

import java.lang.*;

public class Percolation {

    private int[][] grid; // the grid, percolation model
    private WeightedQuickUnionUF gridConnection; // the connection model
    private int n; // N
    //private int[] bottomLine;
    //private int[] topLine;

    /*
    create N-by-N grid, with all sites blocked
    */
    public Percolation(int N) {
        if (N<=0) {
            throw new IllegalArgumentException();
        }
        n = N;
        grid = new int[n+1][n+1];
        gridConnection = new WeightedQuickUnionUF(N*N+1); //0 is top virtual site, N^2+1 is bottom virtual site
        for (int i=1; i<=N; i++)
            for (int j=1; j<=N; j++)
            {
                grid[i][j] = 0;
            }
        //bottomLine = new int[n]{}
    }

    /*
    return the  (i, j) of the site given its number
    */
    private int[] mapToIndex(int siteID) {
        int[] site = {0,0};
        int i, j;
        i = siteID/n;
        j = (siteID%n==0)?n:(siteID%n);
        site[0] = (j==n)?i:(i+1);
        site[1] = j;

        return site;
    }

    /*
    return the siteId of the site given its (i, j)
    */
    private int mapToId(int i, int j) {
        return (i-1)*n+j;
    }

    /*
    return a site's neighber nites, 3 or 4 sites, top, bottom, left, right respectively
    */
    private int[] neighbers(int i, int j) {
        int[] neighbers = {0, 0, 0, 0};
        neighbers[0] = (i==1)?0:mapToId(i-1, j);
        neighbers[1] = (i==n)?(n*n+1):mapToId(i+1, j);
        neighbers[2] = (j==1)?-1:mapToId(i, j-1);
        neighbers[3] = (j==n)?-1:mapToId(i, j+1);
        return neighbers;
    }

    /*
    open site (row i, column j) if it is not open already
    connect the open sites surround it
    */
    public void open(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen(i, j)) {
            grid[i][j] = 1;
            int[] neighbers = neighbers(i, j);
            int siteCenter = mapToId(i, j);
            int site;
            for (int index=0; index<4; index++) {
                site = neighbers[index];
                //only connected the open sites surrounding the center sites, virtual sites are always regarded open
                if ((site!=-1&&site!=n*n+1) && ((site==0) || isOpen(mapToIndex(site)[0], mapToIndex(site)[1]))) {
                    gridConnection.union(site, siteCenter);
                }
            }
        }
    }

    /*
     site (row i, column j) open?
    */
    public boolean isOpen(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        return (grid[i][j] != 0);
    }

    /*
    is site (row i, column j) full? what does full mean?
    */
    public boolean isFull(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw new IndexOutOfBoundsException();
        }
        int site = mapToId(i, j);
        return gridConnection.connected(0, site);
    }

    /*
    does the system percolate?
    check if the virtual top site and virtual bottom site connected
    */
    public boolean percolates()
    {
        for (int j = 1; j <= n; j++) {
            if (isOpen(n, j)) {
                int site = mapToId(n, j);
                if (gridConnection.connected(0, site))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(1,4);
        p.open(2,3);
        p.open(5,4);
        p.open(3,4);
        System.out.println(p.isFull(1,4));

    }
}
