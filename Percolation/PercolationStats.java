/****************************************************************************
Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

    Initialize all sites to be blocked.

    Repeat the following until the system percolates:

        Choose a site (row i, column j) uniformly at random among all blocked sites.

        Open the site (row i, column j).

    The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.
 *
 ****************************************************************************/

public class PercolationStats {
    /*
    perform T independent experiments on an N-by-N grid
    */
    public PercolationStats(int N, int T) {

    }

    /*
    sample mean of percolation threshold
    */
    public double mean() {

    }

    /*
    sample standard deviation of percolation threshold
    */
    public double stddev() {

    }

    /*
    low  endpoint of 95% confidence interval
    */
    public double confidenceLo() {

    }

    /*
    high endpoint of 95% confidence interval
    */
    public double confidenceHi() {

    }

    public static void main(String[] args) {

    }

}
