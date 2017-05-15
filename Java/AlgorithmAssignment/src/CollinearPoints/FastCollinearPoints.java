package CollinearPoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implementation of judging whether 4 or more points are in the same line more faster than the CollinearPoints.BruteCollinearPoints
 * exceed limit time, do not know why
 * @author Qiang
 * @since 16/04/2017
 */
public class FastCollinearPoints {

    private Point[] points;
    private LineSegment[] segments;
    private int numOfSegments;
    /**
     * keep the slopes of each line segment in segments, to help judge whether we have save the same line segment
     */
    private double[] slopes;
    /**
     * keep the destinations of each line segment in segments, to help judge whether we have save the same line segment
     */
    private Point[] destination;
    /**
     * keep the len of each line segment in segments, to help judge which short line to remove when has duplicate lines
     */
    private int[] lineLen;
    private int capacity;

    public FastCollinearPoints(Point[] points) {
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

        capacity = points.length / 2;

        segments = new LineSegment[capacity];
        slopes = new double[capacity];
        destination = new Point[capacity];
        lineLen = new int[capacity];
        computeNumber();


    }

    private void computeNumber() {

        Arrays.sort(points);
        int size = points.length;

        // aux array to store the sorted array
        Point[] aux = new Point[size];
        for (int i = 0; i < size - 1; i++) {  // only to size - 1
            int copySize = 0;
            for (int j = i; j < size; j++) {
                aux[copySize++] = points[j];
            }

            Arrays.sort(aux, 0, copySize, points[i].slopeOrder());

            int startOrder = -1;
            int sameSize = 0;
            boolean hasFoundSame = false;
            double lastSlope = aux[1].slopeTo(aux[0]);
            for (int j = 2; j < copySize; j++) {
                double newSlope = aux[j].slopeTo(points[i]);
                if (compareDouble(newSlope, lastSlope)) {
                    sameSize++;
                    if (!hasFoundSame) {
                        hasFoundSame = true;
                        startOrder = j - 1;
                    }

                } else {
                    if (hasFoundSame) {
                        if (sameSize >= 2) {
                            destination[numOfSegments] = aux[startOrder + sameSize];
                            slopes[numOfSegments] = lastSlope;
                            lineLen[numOfSegments] = sameSize;
                            segments[numOfSegments++] = new LineSegment(aux[0], aux[startOrder + sameSize]);

                            if (numOfSegments == capacity) {
                                resize();
                            }
                        }


                        sameSize = 0;
                        startOrder = -1;
                        hasFoundSame = false;

                    }
                    lastSlope = newSlope;
                }
            }

            if (hasFoundSame) {
                if (sameSize >= 2) {
                    destination[numOfSegments] = aux[startOrder + sameSize];
                    slopes[numOfSegments] = lastSlope;
                    lineLen[numOfSegments] = sameSize;
                    segments[numOfSegments++] = new LineSegment(aux[0], aux[startOrder + sameSize]);
                }
                if (numOfSegments == capacity) {
                    resize();
                }

            }


        }

        removeDuplicate();
    }

    private void removeDuplicate() {
        List<Integer> linesToSaved = new ArrayList<>(numOfSegments);
        boolean[] hasBeenChecked = new boolean[numOfSegments];


        for (int i = 0; i < numOfSegments; i++) {
            if (hasBeenChecked[i])
                continue;

            int maxLen = lineLen[i];
            int maxLenPos = i;

            for (int j = i; j < numOfSegments; j++) {
                if (compareDouble(slopes[i], slopes[j]) && destination[i].compareTo(destination[j]) == 0) {
                    hasBeenChecked[j] = true;
                    if (lineLen[j] > maxLen) {
                        maxLen = lineLen[j];
                        maxLenPos = j;
                    }
                }
            }
            linesToSaved.add(maxLenPos);
        }

        numOfSegments = linesToSaved.size();
        LineSegment[] newSegments = new LineSegment[numOfSegments];
        for (int i = 0; i < numOfSegments; i++) {
            newSegments[i] = segments[linesToSaved.get(i)];
        }
        slopes = null;
        destination = null;
        segments = newSegments;
    }

    private void resize() {

        int newCapacity = capacity * 4;
        Point[] newDestination = new Point[newCapacity];
        double[] newSlopes = new double[newCapacity];
        LineSegment[] newSegments = new LineSegment[newCapacity];
        int[] lines = new int[newCapacity];
        for (int i = 0; i < capacity; i++) {
            newDestination[i] = destination[i];
            newSlopes[i] = slopes[i];
            newSegments[i] = segments[i];
            lines[i] = lineLen[i];
        }
        destination = newDestination;
        slopes = newSlopes;
        segments = newSegments;
        lineLen = lines;
        capacity = newCapacity;

    }


    public int numberOfSegments() {
        return numOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numOfSegments);
    }

    private boolean compareDouble(double d1, double d2) {
        return (d1 == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) || Math.abs(d1 - d2) < 0.00000001;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input200.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (CollinearPoints.Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//            segment.draw();
        }
        StdOut.println("************************");
//        CollinearPoints.BruteCollinearPoints collinearPoints = new CollinearPoints.BruteCollinearPoints(points);
//        for (CollinearPoints.LineSegment segment : collinearPoints.segments()) {
//            StdOut.println(segment);
////            segment.draw();
//        }
//        StdDraw.show();
    }

}
