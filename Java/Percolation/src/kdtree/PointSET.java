package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Qiang
 * @since 29/04/2017
 */
public class PointSET {

    private Set<Point2D> points;


    /**
     * construct an empty set of points
     */
    public PointSET() {
        points = new TreeSet<>();
    }


    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return points.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException("CollinearPoints.Point can not be null");

        points.add(p);

    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("CollinearPoints.Point can not be null");


        return points.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        points.forEach(Point2D::draw);
    }

    /**
     * all points that are inside the rectangle
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new NullPointerException("RectHV can not be null");
        List<Point2D> insidePoints = new ArrayList<>(points.size() / 2);


        for (Point2D point2D : points) {
            if (rect.contains(point2D)) {
                insidePoints.add(point2D);
            }
        }

        return insidePoints;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("CollinearPoints.Point can not be null");

        if (isEmpty()) {
            return null;
        }

        Point2D nearest = null;
        double distance = Double.MAX_VALUE;

        for (Point2D point2D : points) {
            if (point2D.distanceTo(p) < distance) {
                nearest = point2D;
                distance = point2D.distanceTo(p);
            }
        }

        return nearest;
    }

    public static void main(String[] args) {
    }

}
