import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF quickUnion;
  private boolean[] isOpen;
  //private boolean[] isFull;
  private int dimension;
    
  public Percolation(int dimension) {
    if (dimension <= 0)
      throw new java.lang.IllegalArgumentException("Dimension out of bounds.");
    int surfaceArea = dimension * dimension;
    quickUnion = new WeightedQuickUnionUF(surfaceArea);
    isOpen = new boolean[surfaceArea];
    //isFull = new boolean[surfaceArea];
    this.dimension = dimension;
  }
  
  public void open (int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    isOpen[index] = true;
    
    tryUnion(x, y, x - 1, y);
    tryUnion(x, y, x + 1, y);
    tryUnion(x, y, x, y - 1);
    tryUnion(x, y, x, y + 1);
  }
  
  public boolean isOpen(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    return isOpen[index];
  }
  
  public boolean isFull(int x, int y) {
    checkBoundaries(x, y);
    int p = convertXYtoIndex(x, y);
    if (!isOpen[p])
      return false;
    if (x == 1)
      return true;
    checkBoundaries(x, y);
    for(int i = 1; i <= dimension; i++) {
      int q = convertXYtoIndex(1, i);
      if (quickUnion.connected(p, q))
        return true;
    }
    return false;
  }
  
  public boolean percolates() {
    for(int y = 1; y <= dimension; y++) {
      if (isFull(dimension, y)) {
        return true;
      }
    }
    return false;
  }
  
  private void checkBoundaries(int x, int y) {
    if (!isWithinBounds(x, y))
      throw new java.lang.IndexOutOfBoundsException("(" + x + "," + y + ") out of bounds. Should be within (1,1)~(" + dimension + "," + dimension + ")");
  }
  
  private boolean isWithinBounds(int x, int y) {
    if (x < 1 || x > dimension || y < 1 || y > dimension)
      return false;
    else
      return true;
  }
  
  private int convertXYtoIndex(int x, int y) {
    return ((y - 1) * dimension + (x - 1));
  }
  
  private void tryUnion(int x1, int y1, int x2, int y2) {
    if (!isWithinBounds(x1, y1) || !isWithinBounds(x2, y2))
      return;
    
    int p = convertXYtoIndex(x1, y1);
    int q = convertXYtoIndex(x2, y2);
    if (isOpen[p] && isOpen[q]) {
      quickUnion.union(p,q);
    }
  }
}