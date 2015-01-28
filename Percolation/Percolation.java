/****************************************************************************

Percolation algorithm.

prepare for data and basic operation of the grid

(Corner cases.  By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.
The constructor should take time proportional to N^2)

****************************************************************************/

public class Percolation {

    public int[][] grid; // the grid, percolation model
    public WeightedUuickUiounUF gridConnection; // the connection model
    public int n; // N

    /*
    create N-by-N grid, with all sites blocked
    */
    public Percolation(int N) {
        if (N<=0) {
            throw java.lang.IllegalArgumentException;
        }
        n = N;
        gridConnection = WeightedUuickUiounUF(N*N+2); //0 is top virtual site, N^2+1 is bottom virtual site
        for (int i=1; i<=N; i++)
            for (int j=1; j<=N; j++)
            {
                grid[i][j] = 0;
            }
    }

    /*
    return the  (i, j) of the site given its number
    */
    public int[2] mapToIndex(int siteID) {
        int[2] site = [0, 0];
        int j = siteID%n;
        int i = (siteID-i)/n;
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
    public int[4] neighbers(int i, int j) {
        int[4] neighbers;
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
            throw java.lang.IndexOutOfBoundsException;
        }
        if (grid[i][j]!=1) {
            grid[i][j] = 1;
            nerighbers = neighbers(i, j)
            for (int index=0; index<4; index++) {

            }
        }
    }

    /*
     site (row i, column j) open?
    */
    public boolean isOpen(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw java.lang.IndexOutOfBoundsException;
        }
        return (grid[i][j] == 1);
    }

    /*
    is site (row i, column j) full?
    */
    public boolean isFull(int i, int j) {
        if (i>n || j>n || i<1 || j<1) {
            throw java.lang.IndexOutOfBoundsException;
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
        Percolation p = new Percolation(args[1]);

    }
}
