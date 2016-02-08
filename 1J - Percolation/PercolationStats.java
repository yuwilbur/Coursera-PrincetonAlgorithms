import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;
  
  public PercolationStats(int N, int T) {
    if (N <= 0)
      throw new java.lang.IllegalArgumentException("N needs to be positive.");
    if (T <= 0)
      throw new java.lang.IllegalArgumentException("T needs to be positive.");
    
    mean = 0;
    stddev =0;
    confidenceLo = 0;
    confidenceHi = 0;
    
    int maxIterationToPercolate = N * N;
    double[] percolationThreshold = new double[T];
    for(int i = 0; i < T; i++) {
      Percolation percolation = new Percolation(N);
      int iterationsToPercolate = 0;
      while(!percolation.percolates()) {
        int x = StdRandom.uniform(N) + 1;
        int y = StdRandom.uniform(N) + 1;
        while (percolation.isOpen(x,y)) {
          x = StdRandom.uniform(N) + 1;
          y = StdRandom.uniform(N) + 1;
        }
        percolation.open(x,y);
        iterationsToPercolate++;
      }
      percolationThreshold[i] = (double)iterationsToPercolate / (double)maxIterationToPercolate;
    }
    
    mean = StdStats.mean(percolationThreshold);
    stddev = StdStats.stddev(percolationThreshold);
    confidenceLo = mean - 1.96 * stddev / Math.sqrt(T);
    confidenceHi = mean + 1.96 * stddev / Math.sqrt(T);
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
    PercolationStats stats = new PercolationStats(200, 100);
    System.out.println("mean = " + stats.mean());
    System.out.println("stddev = " + stats.stddev());
    System.out.println("confidence = " + stats.confidenceLo() + "," + stats.confidenceHi());
  }
}