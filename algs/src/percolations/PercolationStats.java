package percolations;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] percolationThresholds;
    private double trials;

    public PercolationStats(int n, int trials){
        if(n <=0 || trials <= 0){
            throw new IllegalArgumentException("invalid location");
        }
        this.trials = trials;
        percolationThresholds = new double[trials];

        for (int i=0; i<=trials-1; i++) {
            percolationThresholds[i] = ((double) performExperiment(n))/(n*n);

        }
    }

    private int performExperiment(int n) {
        Percolation perco = new Percolation(n);

        while(!perco.percolates() ){
            perco.open(StdRandom.uniform(1,n+1) ,StdRandom.uniform(1,n+1));
        }

        return perco.numberOfOpenSites();

    }

    public double mean() {

        return StdStats.mean(percolationThresholds);
    }
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }
    public double confidenceLo() {
        double num = stddev()*1.96;
        double denum = Math.sqrt(trials);
        double second = num/denum;
        return mean() - second;
    }
    public double confidenceHi() {
        double num = stddev()*1.96;
        double denum = Math.sqrt(trials);
        double second = num/denum;
        return mean() + second;
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats ps = new PercolationStats(n,trials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}
