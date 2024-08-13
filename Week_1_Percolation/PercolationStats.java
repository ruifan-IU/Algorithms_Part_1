/* *****************************************************************************
 *  Name:              Rui Fan
 *  Coursera User ID:  123456
 *  Last modified:     08/12/2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholds;
    private double confidence = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid input.");
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;

                while (per.isOpen(row, col)) {
                    row = StdRandom.uniformInt(n) + 1;
                    col = StdRandom.uniformInt(n) + 1;
                }

                per.open(row, col);
            }
            thresholds[i] = (double) per.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        int t = thresholds.length;

        return mean - (confidence * stddev / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        int t = thresholds.length;

        return mean + (confidence * stddev / Math.sqrt(t));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats perStats = new PercolationStats(n, trials);
        double mean = perStats.mean();
        double stddev = perStats.stddev();
        double confidenceLo = perStats.confidenceLo();
        double confidenceHi = perStats.confidenceHi();

        System.out.println("mean = " + mean);
        System.out.println("stddev = " + stddev);
        System.out.println(
                "95% confidence interval = [" + confidenceLo + ", " + confidenceHi + "]");
    }
}
