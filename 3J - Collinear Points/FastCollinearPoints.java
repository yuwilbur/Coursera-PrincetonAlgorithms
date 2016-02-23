import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
  private LineSegment[] segments;
  private int[] upperArray;
  private int[] lowerArray;
  private int segmentsLength;
  
  public FastCollinearPoints(Point[] points) {
    if (points.length == 0)
      throw new java.lang.NullPointerException();
    
    upperArray = new int[1];
    lowerArray = new int[1];
    segments = new LineSegment[1];
    segmentsLength = 0;
    
    for(int i = 0; i < points.length; i++) {
      for(int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) // Equal points
          throw new java.lang.IllegalArgumentException();
        
        Double slope = points[i].slopeTo(points[j]);
        int lower = i;
        int upper = i;
        if (points[upper].compareTo(points[j]) < 0)
          upper = j;
        if (points[lower].compareTo(points[j]) > 0)
          lower = j;
        int count = 2;
        for(int k = j + 1; k < points.length; k++) {
          if (points[j].slopeTo(points[k]) == slope) {
            count++;
            if (points[upper].compareTo(points[k]) < 0)
              upper = k;
            if (points[lower].compareTo(points[k]) > 0)
              lower = k;
          }
        }        
        if (count >= 4) {
          boolean isDuplicate = false;
          for(int k = 0; k < segmentsLength; k++) {
            if (upper == upperArray[k] && lower == lowerArray[k]) {
              isDuplicate = true;
              break;
            } 
          }
          if (!isDuplicate)
            addSegment(points[upper], points[lower], upper, lower);
        }
      }
    }
    
    LineSegment[] newSegments = new LineSegment[segmentsLength];
    for(int i = 0; i < segmentsLength; ++i) {
      newSegments[i] = segments[i];
    }
    segments = newSegments;
  }
  
  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    return segments;
  }
  
  private void addSegment(Point upperPoint, Point lowerPoint, int upper, int lower) {
    if (segmentsLength >= segments.length) {
      int newSegmentsLength = segments.length * 2;
      LineSegment[] newSegments = new LineSegment[newSegmentsLength];
      int[] newUpperArray = new int[newSegmentsLength];
      int[] newLowerArray = new int[newSegmentsLength];
      for(int i = 0; i < segments.length; ++i) {
        newSegments[i] = segments[i];
        newUpperArray[i] = upperArray[i];
        newLowerArray[i] = lowerArray[i];
      }
      segments = newSegments;
      upperArray = newUpperArray;
      lowerArray = newLowerArray;
    }
    segments[segmentsLength] = new LineSegment(upperPoint, lowerPoint);
    upperArray[segmentsLength] = upper;
    lowerArray[segmentsLength] = lower;
    segmentsLength++;
  }
  
  public static void main(String[] args) {
    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }
    
    // draw the points
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.setPenRadius(0.01);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();
    
    // print and draw the line segments
    StdDraw.setPenRadius(0.005);
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
  }
}