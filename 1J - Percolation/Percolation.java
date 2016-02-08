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
    Coord2D coord_p = new Coord2D(x - 1, y - 1);
    checkBoundaries(coord_p);
    Coord2D coord_q = new Coord2D(coord_p.x,coord_p.y);
    m_isOpen[coord_p.x][coord_p.y] = true;
    
    coord_q.x = coord_p.x + 1;
    coord_q.y = coord_p.y;
    tryUnion(coord_p,coord_q);
    
    coord_q.x = coord_p.x - 1;
    coord_q.y = coord_p.y;
    tryUnion(coord_p,coord_q);
    
    coord_q.x = coord_p.x;
    coord_q.y = coord_p.y + 1;
    tryUnion(coord_p,coord_q);
    
    coord_q.x = coord_p.x;
    coord_q.y = coord_p.y - 1;
    tryUnion(coord_p,coord_q);
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
    Coord2D coord = new Coord2D(m_dimension - 1, 0);
    for(coord.y = 0; coord.y < m_dimension; coord.y++) {
      if (isFull(coord)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean isOpen(Coord2D coord) {
    checkBoundaries(coord);
    return m_isOpen[coord.x][coord.y];
  }
  
  private boolean isFull(Coord2D coord_p) {
    checkBoundaries(coord_p);
    if (coord_p.x == 0)
      return true;
    // Check if it's connected to any of the top row cells
    Coord2D coord_q = new Coord2D(coord_p.x, coord_p.y);
    int p = convertXYtoIndex(coord_p);
    coord_q.x = 0;
    for(coord_q.y = 0; coord_q.y < m_dimension; coord_q.y++) {
      int q = convertXYtoIndex(coord_q);
      if (m_quickUnion.connected(p,q)) {
        return true;
      }
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
}