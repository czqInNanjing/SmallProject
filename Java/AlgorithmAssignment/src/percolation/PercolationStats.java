package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author Qiang
 * @since 11/04/2017
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private int trials;
    private int n;


    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.n = n;
        compute();
    }

    private void compute() {

        double[] needSitesToPercolate = new double[trials];
        int totalSize = n * n;
        for (int i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int nextToOpen = StdRandom.uniform(totalSize);
                percolation.open(nextToOpen / n + 1, nextToOpen % n + 1);
            }

            needSitesToPercolate[i] = percolation.numberOfOpenSites() / (double) totalSize;

        }

        mean = StdStats.mean(needSitesToPercolate);
        stddev = StdStats.stddev(needSitesToPercolate);
        confidenceLo = mean - 1.96 * stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96 * stddev / Math.sqrt(trials);

    }


    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {


    }


}
