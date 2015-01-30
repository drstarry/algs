/****************************************************************************
Monte Carlo simulation. To estimate the percolation threshold, consider the following computational experiment:

    Initialize all sites to be blocked.

    Repeat the following until the system percolates:

        Choose a site (row i, column j) uniformly at random among all blocked sites.

        Open the site (row i, column j).

    The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.

For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204th site is opened.

random:
// [a, b) ??? what lier!
 public static int uniform(int a, int b) {
        if (b <= a) throw new IllegalArgumentException("Invalid range");
        if ((long) b - a >= Integer.MAX_VALUE) throw new IllegalArgumentException("Invalid range");
        return a + uniform(b - a);
    }
 ****************************************************************************/

import java.lang.*;
import java.util.*;

public class PercolationStats {
    /*
    perform T independent experiments on an N-by-N grid
    */

    //private Percolation p;
    private int n;
    private int t;
    private double stdDev;
    private double mean;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int N, int T) {
        if (N<=0 || T<=0) {
            throw new IllegalArgumentException();
        }
        n = N;
        t = T;
        Percolation p;
        int[] site = {1, 1};
        double[] thresholds = new double[t]; //a list T threshod
        Arrays.fill(thresholds, 0.0);
        int opened = 0;
        for (int i=0; i<t; i++) {
            p = new Percolation(N);
            while(!p.percolates()) {
                if(n!=1) {
                    site = mapToIndex(StdRandom.uniform(1, n*n));
                    if (!p.isOpen(site[0], site[1])) {
                        p.open(site[0], site[1]);
                        opened++;
                    }
                }
            }
            thresholds[i] = (opened+0.0) / (n*n);
            opened = 0;
        }
        mean = StdStats.mean(thresholds);
        stdDev = StdStats.stddev(thresholds);
        confidenceLo = mean - 1.96*stdDev / Math.sqrt(t);
        confidenceHi = mean + 1.96*stdDev / Math.sqrt(t);
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
    sample mean of percolation threshold
    */
    public double mean() {
        return mean;
    }

    /*
    sample standard deviation of percolation threshold
    */
    public double stddev() {
        return stdDev;
    }

    /*
    low  endpoint of 95% confidence interval
    */
    public double confidenceLo() {
        return confidenceLo;
    }

    /*
    high endpoint of 95% confidence interval
    */
    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = "+ps.mean());
        System.out.println("stddev = "+ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
    }

}

