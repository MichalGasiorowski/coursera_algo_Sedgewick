/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michal
 */
public class PercolationStats {
    
    private final double[] results;
    
    public PercolationStats(final int N, final int T) {
        // perform T independent computational experiments on an N-by-N grid
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("bad arguments!");
        }
        results = new double[T];
        int[] sampledPair;
        for (int i = 0; i < T; i++) {
            Percolation per = new Percolation(N);
            int acc = 0;
            while (!per.percolates()) {
                acc++;
                sampledPair = sampleRand(per, N);
                per.open(sampledPair[0], sampledPair[1]);
            }
            results[i] = (double) acc/(N*N);
        }
    }
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);
    }
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results); 
    }
    
    private int[] sampleRand(Percolation per, int gS) {
        int i, j;
        while (true) {
            i = StdRandom.uniform(1, gS +1);
            j = StdRandom.uniform(1, gS +1);
            if (!per.isOpen(i, j)) {
                return new int[]{i, j};
            }
        }   
    }
    
    public static void main(String[] args) {
        // test client, described below
        int N = StdIn.readInt();
        int T = StdIn.readInt();
//        int N =3;
//        int T = 100;
        PercolationStats ps = new PercolationStats(N, T);
        double mean = ps.mean();
        double stdev = ps.stddev();
        double std95 = (1.96*stdev) / Math.sqrt(T);
        
        StdOut.println("mean                    =" + mean);
        StdOut.println("stddev                  =" + stdev);
        StdOut.println("95% confidence interval ="  
                      + (mean - std95) + ", " + (mean + std95));
        
    } 
}
