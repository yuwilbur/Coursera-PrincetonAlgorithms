import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
  private class Line {
    public Line(Point point1, Point point2) {
      this.slope = point1.slopeTo(point2);
      if (this.slope != Double.POSITIVE_INFINITY) {
        this.intercept = point1.x * slope - point1.y;
      } else {
        this.intercept = point1.x;
      }
    }
    public boolean compareTo(Line line) {
      return (slope == line.slope && intercept == line.intercept);
    }
    double slope = 0.0;
    double intercept = 0.0;
  }
  private Line[] lines;
  private LineSegment[] segments;
  
  public BruteCollinearPoints(Point[] points) {
    if (points.length == 0)
      throw new java.lang.NullPointerException();
    
    lines = new Line[0];
    segments = new LineSegment[0];
    for(int i = 0; i < points.length; i++) {
      for(int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) // Equal points
          throw new java.lang.IllegalArgumentException();
        Line line = new Line(points[i], points[j]);
        boolean duplicateFound = false;
        for(int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
          if (line.compareTo(lines[lineIndex])) {
            duplicateFound = true;
          }
        }
        if (duplicateFound)
          continue;
        
        Double slope = points[i].slopeTo(points[j]);
        
        Point lowerPoint = points[i];
        Point upperPoint = points[i];
        if (upperPoint.compareTo(points[j]) < 0)
          upperPoint = points[j];
        else if (lowerPoint.compareTo(points[j]) > 0)
          lowerPoint = points[j];
        int count = 2;
        for(int k = j + 1; k < points.length; k++) {
          if (points[j].slopeTo(points[k]) == slope) {
            count++;
            if (upperPoint.compareTo(points[k]) < 0)
              upperPoint = points[k];
            else if (lowerPoint.compareTo(points[k]) > 0)
              lowerPoint = points[k];
          }
        }        
        if (count >= 4) {
          addSegment(upperPoint, lowerPoint);
        }
      }
    }
  }
  
  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    return segments;
  }
  
  private void addSegment(Point upperPoint, Point lowerPoint) {
    LineSegment segment = new LineSegment(upperPoint, lowerPoint);
    LineSegment[] newSegments = new LineSegment[segments.length + 1];
    Line line = new Line(upperPoint, lowerPoint);
    Line[] newLines = new Line[lines.length + 1];
    for(int i = 0; i < lines.length; ++i) {
      newLines[i] = lines[i];
      newSegments[i] = segments[i];
    }
    newLines[newLines.length - 1] = line;
    lines = newLines;
    newSegments[newSegments.length - 1] = segment;
    segments = newSegments;
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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
  }
}