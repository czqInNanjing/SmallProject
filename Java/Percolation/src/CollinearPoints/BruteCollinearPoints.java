package CollinearPoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

/**
 * @author Qiang
 * @since 16/04/2017
 */
public class BruteCollinearPoints {


    private Point[] points;
    private LineSegment[] segments;
    private int numOfSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }


        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.points = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            if (point == null) {
                throw new NullPointerException();
            }
        }
        segments = new LineSegment[points.length*10];
        computeNumber();
    }

    private void computeNumber() {
        Arrays.sort(points);
        int size = points.length;
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                for (int k = j + 1; k < size; k++) {
                    for (int l = k + 1; l < size; l++) {

                        double slope1 = points[i].slopeTo(points[j]);
                        double slope2 = points[i].slopeTo(points[k]);
                        double slope3 = points[i].slopeTo(points[l]);

                        if (compareDouble(slope1, slope2) && compareDouble(slope1, slope3)) {

                            segments[numOfSegments] = new LineSegment(points[i], points[l]);
                            numOfSegments++;
                        }


                    }

                }

            }
        }


    }

    public int numberOfSegments() {
        return numOfSegments;
    }


    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numOfSegments);
    }

    private boolean compareDouble(double d1, double d2) {
        return d1 == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY  || Math.abs(d1 - d2) < 0.00000001;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("vertical25.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
