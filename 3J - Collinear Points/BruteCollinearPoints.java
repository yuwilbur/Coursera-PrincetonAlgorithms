import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
  private LineSegment[] segments;
  
  public BruteCollinearPoints(Point[] points) {
    segments = new LineSegment[0];
    for(int i = 0; i < points.length; i++) {
      for(int j = i + 1; j < points.length; j++) {
        Double slope = points[i].slopeTo(points[j]);
        System.out.print(i + "." + j + "::" + slope + "::");
        Point lowerPoint = points[i];
        Point upperPoint = points[i];
        if (upperPoint.compareTo(points[j]) < 0)
          upperPoint = points[j];
        else if (lowerPoint.compareTo(points[j]) > 0)
          lowerPoint = points[j];
        int count = 2;
        for(int k = j + 1; k < points.length; k++) {
          if (points[j].slopeTo(points[k]) == slope) {
            System.out.print(points[j].slopeTo(points[k]) + " ");
            //addSegment(new LineSegment(points[j], points[k]));
            count++;
            if (upperPoint.compareTo(points[k]) < 0)
              upperPoint = points[k];
            else if (lowerPoint.compareTo(points[k]) > 0)
              lowerPoint = points[k];
          }
        }        
        if (count >= 4) {
          System.out.print("A:" + lowerPoint.slopeTo(upperPoint) + " ");
          upperPoint.draw();
          lowerPoint.draw();
          addSegment(new LineSegment(upperPoint, lowerPoint));
        }
        System.out.println("");
      }
    }
  }
  
  public int numberOfSegments() {
    return segments.length;
  }
  
  public LineSegment[] segments() {
    return segments;
  }
  
  private void addSegment(LineSegment segment) {
    LineSegment[] newSegments = new LineSegment[segments.length + 1];
    for(int i = 0; i < segments.length; ++i) {
      newSegments[i] = segments[i];
    }
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
    StdDraw.setPenRadius(0.02);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();
    
    // print and draw the line segments
    StdDraw.setPenRadius(0.01);
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
  }
}