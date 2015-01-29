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

    public Percolation p;
    public double[] thresholds; //a list T threshod
    public int n;
    public int t;
    public double stdDev;
    public double mean;
    public double confidenceLo;
    public double confidenceHi;

    public PercolationStats(int N, int T) {
        if (N<=0 || T<=0) {
            throw new IllegalArgumentException();
        }
        n = N;
        t = T;
        thresholds = new double[t];
        Arrays.fill(thresholds, 0.0);
        int[] site = {1, 1};
        for (int i=0; i<t; i++) {
            p = new Percolation(n);
            while(!p.percolates()) {
                if(n!=1)
                    site = p.mapToIndex(StdRandom.uniform(1, n*n));
                p.open(site[0], site[1]);
            }
            thresholds[i] = p.threshold();
        }
        compute();
    }

    /*
    prepare for the values
    */
    public void compute() {
        mean = StdStats.mean(thresholds);
        stdDev = StdStats.stddev(thresholds);
        confidenceLo = mean - 1.96*stdDev/Math.sqrt(t);
        confidenceHi = mean + 1.96*stdDev/Math.sqrt(t);
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

