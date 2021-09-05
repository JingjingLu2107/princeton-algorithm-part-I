/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] openPercent;
    final private int t;
    private double parameter;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        parameter = 1.96;

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid constructor arguments");
        }

        t = trials;

        openPercent = new double[t];

        for (int i = 0; i < t; i++) {

            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }

            openPercent[i] = percolation.numberOfOpenSites() / (double) (n * n);

        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openPercent);
    }


    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openPercent);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - parameter * stddev() / Math.sqrt((double) t);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + parameter * stddev() / Math.sqrt((double) t);
    }


    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(
                Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println();
        System.out.printf("mean                         =  %f", percolationStats.mean());
        System.out.println();
        System.out.printf("stddev                       =  %f", percolationStats.stddev());
        System.out.println();
        System.out
                .printf(" %s %f %f %s", "95 % confidence interval     = [",
                        percolationStats.confidenceLo(),
                        percolationStats.confidenceHi(), "]");
        System.out.println();
    }
}
