import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF quickUnion;
  private WeightedQuickUnionUF fullUnion;
  // STATES
  // bit[0] = isOpen
  // bit[1] = isFull
  private byte[] state;
  private byte isOpen;
  private byte isFull;
  private int dimension;
  private int indexStart;
  private int indexEnd;
  private boolean percolates;
  
  public Percolation(int dimension) {
    if (dimension <= 0)
      throw new java.lang.IllegalArgumentException("Dimension out of bounds.");
    int surfaceArea = dimension * dimension;
    // Add 2 extra nodes for beginning and ending positions
    this.quickUnion = new WeightedQuickUnionUF(surfaceArea + 2);
    this.fullUnion = new WeightedQuickUnionUF(surfaceArea + 1);
    this.state = new byte[surfaceArea];
    this.isOpen = Byte.parseByte("00000001", 2);
    this.isFull = Byte.parseByte("00000010", 2);
    this.dimension = dimension;
    this.indexStart = surfaceArea;
    this.indexEnd = surfaceArea + 1;
    this.percolates = false;
    
    for (int p = 0; p < dimension; p++) {
      quickUnion.union(p, indexStart);
      fullUnion.union(p, indexStart);
    }
    
    for (int p = surfaceArea - dimension; p < surfaceArea; p++) {
      quickUnion.union(p, indexEnd);
    }
  }
  
  public void open(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    // This cell is already open
    if ((state[index] & isOpen) > 0)
      return;
    state[index] |= isOpen;
    
    tryUnion(x, y, x - 1, y);
    tryUnion(x, y, x + 1, y);
    tryUnion(x, y, x, y - 1);
    tryUnion(x, y, x, y + 1);
    
    // Check if percolation has occurred
    if (!this.percolates && quickUnion.connected(indexStart, indexEnd)) {
      this.percolates = true;
    }
  }
  
  public boolean isOpen(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    return (state[index] > 0);
  }
  
  public boolean isFull(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    if ((state[index] & isOpen) > 0 && (state[index] & isFull) == 0) {
      if (fullUnion.connected(index, indexStart)) {
        state[index] |= isFull;
      }
    } 
    return (state[index] & isFull) > 0;
  }
  
  public boolean percolates() {
    return percolates;
  }
  
  private void checkBoundaries(int x, int y) {
    if (!isWithinBounds(x, y))
      throw new java.lang.IndexOutOfBoundsException("(" + x + "," + y + ") out of bounds.");
  }
  
  private boolean isWithinBounds(int x, int y) {
    return !(x < 1 || x > dimension || y < 1 || y > dimension);
  }
  
  private int convertXYtoIndex(int x, int y) {
    return ((x - 1) * dimension + (y - 1));
  }
  
  private void tryUnion(int x1, int y1, int x2, int y2) {
    if (!isWithinBounds(x1, y1) || !isWithinBounds(x2, y2))
      return;
    
    int p = convertXYtoIndex(x1, y1);
    int q = convertXYtoIndex(x2, y2);
    if (state[p] > 0 && state[q] > 0) {
      quickUnion.union(p, q);
      fullUnion.union(p, q);
    }
  }
}