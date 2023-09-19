import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // final variable for confidence interval
    private static final double CONFIDENCE_95 = 1.96;
    private int trialCount; // number of trials
    private double[] trialResults; // results of the trials in array

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        trialCount = trials;
        trialResults = new double[trialCount];

        for (int trial = 0; trial < trialCount; trial++) {
            Percolation p = new Percolation(n);

            // continue opening sites until it percolates
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(0, n);
                int col = StdRandom.uniformInt(0, n);
                p.open(row, col);
            }

            // result = openSites/(n^2)
            double result = (double) p.numberOfOpenSites() / (n * n);

            // add result of trial to array
            trialResults[trial] = result;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((CONFIDENCE_95 * stddev()) / Math.sqrt(trialCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 10;
        int trialCount = 10;
        if (args.length >= 2) {
            n = Integer.parseInt(args[0]);
            trialCount = Integer.parseInt(args[1]);
        }
        Stopwatch timer = new Stopwatch(); // create timer
        PercolationStats stats = new PercolationStats(n, trialCount);

        // print statements
        StdOut.println("mean()           = " + stats.mean());
        StdOut.println("stddev()         = " + stats.stddev());
        StdOut.println("confidenceLow()  = " + stats.confidenceLow());
        StdOut.println("confidenceHigh() = " + stats.confidenceHigh());
        StdOut.println("elapsed time     = " + timer.elapsedTime());
    }
}
