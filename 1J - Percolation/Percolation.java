import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF m_quickUnion;
  private boolean[][] m_isOpen;
  private int m_dimension;
    
  public Percolation(int dimension) {
    if (dimension <= 0)
      throw new java.lang.IllegalArgumentException("Dimension out of bounds.");
    m_quickUnion = new WeightedQuickUnionUF(dimension * dimension);
    m_isOpen = new boolean[dimension][dimension];
    m_dimension = dimension;
  }
  
  public void open (int x, int y) {
    checkBoundaries(x,y);
    m_isOpen[x][y] = true;
    tryUnion(x,y,x+1,y);
    tryUnion(x,y,x-1,y);
    tryUnion(x,y,x,y+1);
    tryUnion(x,y,x,y-1);
  }
  
  public boolean isOpen(int x, int y) {
    checkBoundaries(x,y);
    return m_isOpen[x][y];
  }
  
  public boolean isFull(int x, int y) {
    checkBoundaries(x,y);
    if (y == 0)
      return true;
    // Check if it's connected to any of the top row cells
    int p = convertXYtoIndex(x,y);
    for(int x_check = 0; x_check < m_dimension; ++x_check) {
      int q = convertXYtoIndex(x_check, 0);
      if (m_quickUnion.connected(p,q))
        return true;
    }
    return false;
  }
  
  public boolean percolates() {
    // Check if it's connected to any of the bottom row cells
    for(int x_check = 0; x_check < m_dimension; ++x_check) {
      if (isFull(x_check,m_dimension-1))
        return true;
    }
    return false;
  }
  
  private void checkBoundaries(int x, int y) {
    if (!isWithinBounds(x,y))
      throw new java.lang.IndexOutOfBoundsException("(" + x + "," + y + ") outside of (" + m_dimension + "," + m_dimension + ")");
  }
  
  private boolean isWithinBounds(int x, int y) {
    if (x < 0 || x >= m_dimension || y < 0 || y >= m_dimension)
      return false;
    else
      return true;
  }
  
  private int convertXYtoIndex(int x, int y) {
    return (y * m_dimension + x);
  }
  
  private void tryUnion(int x, int y, int x_check, int y_check) {
    if (!isWithinBounds(x, y) || !isWithinBounds(x_check, y_check))
      return;
    
    if (isOpen(x,y) && isOpen(x_check,y_check)) {
      int p = convertXYtoIndex(x,y);
      int q = convertXYtoIndex(x_check, y_check);
      m_quickUnion.union(p,q);
    }
  }
  
  public static void main(String[] args) {
    int dim = 10;
    Percolation percolation = new Percolation(dim);
    while(!percolation.percolates()) {
      int x = StdRandom.uniform(dim);
      int y = StdRandom.uniform(dim);
      percolation.open(x,y);
    }
  }
}