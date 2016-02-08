import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
  private double m_mean;
  private double m_stddev;
  private double m_confidenceLo;
  private double m_confidenceHi;
  
  public PercolationStats(int N, int T) {
    if (N <= 0)
      throw new java.lang.IllegalArgumentException("N needs to be positive.");
    if (T <= 0)
      throw new java.lang.IllegalArgumentException("T needs to be positive.");
    
    m_mean = 0;
    m_stddev =0;
    m_confidenceLo = 0;
    m_confidenceHi = 0;
    
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
    
    m_mean = StdStats.mean(percolationThreshold);
    m_stddev = StdStats.stddev(percolationThreshold);
    m_confidenceLo = m_mean - 1.96 * m_stddev / Math.sqrt(T);
    m_confidenceHi = m_mean + 1.96 * m_stddev / Math.sqrt(T);
  }
  
  public double mean() {
    return m_mean;
  }
  
  public double stddev() {
    return m_stddev;
  }
  
  public double confidenceLo() {
    return m_confidenceLo;
  }
  
  public double confidenceHi() {
    return m_confidenceHi;
  }
  
  public static void main(String[] args) {
    PercolationStats stats = new PercolationStats(200, 10);
    System.out.println("mean = " + stats.mean());
    System.out.println("stddev = " + stats.stddev());
    System.out.println("confidence = " + stats.confidenceLo() + "," + stats.confidenceHi());
  }
}