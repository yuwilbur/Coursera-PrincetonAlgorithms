import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private class Coord2D {
    public int x;
    public int y;
    public Coord2D(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }
  
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
    // (1,1) is origin so -1 is required
    Coord2D coord = new Coord2D(x - 1, y - 1);
    checkBoundaries(coord);
    m_isOpen[coord.x][coord.y] = true;
    tryUnion(coord,new Coord2D(coord.x + 1, coord.y));
    tryUnion(coord,new Coord2D(coord.x - 1, coord.y));
    tryUnion(coord,new Coord2D(coord.x, coord.y + 1));
    tryUnion(coord,new Coord2D(coord.x, coord.y - 1));
  }
  
  public boolean isOpen(int x, int y) {
    // (1,1) is origin so -1 is required
    Coord2D coord = new Coord2D(x - 1, y - 1);
    return isOpen(coord);
  }
  
  public boolean isFull(int x, int y) {
    // (1,1) is origin so -1 is required
    Coord2D coord = new Coord2D(x - 1, y - 1);
    if (!isOpen(coord))
      return false;
    return isFull(coord);
  }
  
  public boolean percolates() {
    // Check if it's connected to any of the bottom row cells
    int x = m_dimension - 1;
    for(int y = 0; y < m_dimension; y++) {
      if (isFull(new Coord2D(x,y)))
        return true;
    }
    return false;
  }
  
  private boolean isOpen(Coord2D coord) {
    checkBoundaries(coord);
    return m_isOpen[coord.x][coord.y];
  }
  
  private boolean isFull(Coord2D coord) {
    checkBoundaries(coord);
    if (coord.x == 0)
      return true;
    // Check if it's connected to any of the top row cells
    int p = convertXYtoIndex(coord);
    int x = 0;
    for(int y = 0; y < m_dimension; y++) {
      int q = convertXYtoIndex(new Coord2D(x, y));
      if (m_quickUnion.connected(p,q))
        return true;
    }
    return false;
  }
  
  private void checkBoundaries(Coord2D coord) {
    if (!isWithinBounds(coord))
      throw new java.lang.IndexOutOfBoundsException("(" + coord.x + "," + coord.y + ") outside of (" + m_dimension + "," + m_dimension + ")");
  }
  
  private boolean isWithinBounds(Coord2D coord) {
    if (coord.x < 0 || coord.x >= m_dimension || coord.y < 0 || coord.y >= m_dimension)
      return false;
    else
      return true;
  }
  
  private int convertXYtoIndex(Coord2D coord) {
    return (coord.y * m_dimension + coord.x);
  }
  
  private void tryUnion(Coord2D coord_p, Coord2D coord_q) {
    if (!isWithinBounds(coord_p) || !isWithinBounds(coord_q))
      return;
    
    if (isOpen(coord_p) && isOpen(coord_q)) {
      int p = convertXYtoIndex(coord_p);
      int q = convertXYtoIndex(coord_q);
      m_quickUnion.union(p,q);
    }
  }
  
  public static void main(String[] args) {
    int dim = 10;
    Percolation percolation = new Percolation(dim);
    while(!percolation.percolates()) {
      int x = StdRandom.uniform(dim) + 1;
      int y = StdRandom.uniform(dim) + 1;
      percolation.open(x,y);
    }
  }
}