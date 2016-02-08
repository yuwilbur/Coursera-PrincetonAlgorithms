import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF quickUnion;
  // STATES
  // bit[0] = isOpen
  // bit[1] = isConnectedToTop (Effectively Full)
  // bit[2] = isConnectedToBot 
  private byte[] state;
  private byte isOpen;
  private byte connectsTop;
  private byte connectsBot;
  
  private int dimension;
  private boolean percolates;
  
  public Percolation(int dimension) {
    if (dimension <= 0)
      throw new java.lang.IllegalArgumentException("Dimension out of bounds.");
    int surfaceArea = dimension * dimension;
    // Add 2 extra nodes for beginning and ending positions
    this.quickUnion = new WeightedQuickUnionUF(surfaceArea);
    this.state = new byte[surfaceArea];
    this.isOpen = Byte.parseByte("00000001", 2);
    this.connectsTop = Byte.parseByte("00000010", 2);
    this.connectsBot = Byte.parseByte("00000100", 2);
    this.dimension = dimension;
    this.percolates = false;
    
    for (int i = 0; i < dimension; i++) {
      setConnectsTop(i);
    }
    
    for (int i = surfaceArea - dimension; i < surfaceArea; i++) {
      setConnectsBot(i);
    }
  }
  
  public void open(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    // This cell is already open
    if (isOpen(index))
      return;
    setOpen(index);
    
    tryUnion(x, y, x - 1, y);
    tryUnion(x, y, x + 1, y);
    tryUnion(x, y, x, y - 1);
    tryUnion(x, y, x, y + 1);
    
    int root = quickUnion.find(index);
    if (connectsTop(root) && connectsBot(root)) {
      this.percolates = true;
    }
  }
  
  public boolean isOpen(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    return isOpen(index);
  }
  
  public boolean isFull(int x, int y) {
    checkBoundaries(x, y);
    int index = convertXYtoIndex(x, y);
    return isOpen(index) && connectsTop(quickUnion.find(index));
  }
  
  public boolean percolates() {
    return percolates;
  }
  
  private boolean isOpen(int index) { return (state[index] & isOpen) > 0; }
  private void setOpen(int index) { state[index] |= isOpen; }
  private boolean connectsTop(int index) { return (state[index] & connectsTop) > 0; }
  private void setConnectsTop(int index) { state[index] |= connectsTop; }
  private boolean connectsBot(int index) { return (state[index] & connectsBot) > 0; }
  private void setConnectsBot(int index) { state[index] |= connectsBot; }
  
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
    if (isOpen(q)) {
      int pRoot = quickUnion.find(p);
      int qRoot = quickUnion.find(q);
      if (connectsTop(pRoot) || connectsTop(qRoot)) {
        setConnectsTop(pRoot);
        setConnectsTop(qRoot);
      }
      if (connectsBot(pRoot) || connectsBot(qRoot)) {
        setConnectsBot(pRoot);
        setConnectsBot(qRoot);
      }
      quickUnion.union(pRoot, qRoot);
    }
  }
}