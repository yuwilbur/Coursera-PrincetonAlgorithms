import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF quickUnion;
  private boolean[] isOpen;
  private boolean[] isFull;
  private int dimension;
  private int indexStart;
  private boolean percolates;
  
  public Percolation(int dimension) {
    if (dimension <= 0)
      throw new java.lang.IllegalArgumentException("Dimension out of bounds.");
    int surfaceArea = dimension * dimension;
    // Add 2 extra nodes for beginning and ending positions
    this.quickUnion = new WeightedQuickUnionUF(surfaceArea + 1);
    this.isOpen = new boolean[surfaceArea];
    this.isFull = new boolean[surfaceArea];
    this.dimension = dimension;
    this.indexStart = surfaceArea;
    this.percolates = false;
  }
  
  public void open (int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    // This cell is already open
    if (isOpen[index])
      return;
    isOpen[index] = true;
    
    tryUnion(x, y, x - 1, y);
    tryUnion(x, y, x + 1, y);
    tryUnion(x, y, x, y - 1);
    tryUnion(x, y, x, y + 1);
    
    // Link the top cell with the starting (hidden) cell
    if (x == 1) {
      quickUnion.union(index, indexStart);
    }
    
    // Check if percolation has occurred
    for(int i = isOpen.length - dimension; i < isOpen.length; i++) {
      if(isOpen[i] && quickUnion.connected(i, indexStart)) {
        this.percolates = true;
      }
    }
  }
  
  public boolean isOpen(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    return isOpen[index];
  }
  
  public boolean isFull(int x, int y) {
    checkBoundaries(x, y);
    int p = convertXYtoIndex(x, y);
    if (!isFull[p] && isOpen[p]) {
      if (quickUnion.connected(p, indexStart)) {
        isFull[p] = true;
      }
    } 
    return isFull[p];
  }
  
  public boolean percolates() {
    return percolates;
  }
  
  private void checkBoundaries(int x, int y) {
    if (!isWithinBounds(x, y))
      throw new java.lang.IndexOutOfBoundsException("(" + x + "," + y + ") out of bounds.");
  }
  
  private boolean isWithinBounds(int x, int y) {
    if (x < 1 || x > dimension || y < 1 || y > dimension)
      return false;
    else
      return true;
  }
  
  private int convertXYtoIndex(int x, int y) {
    return ((x - 1) * dimension + (y - 1));
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