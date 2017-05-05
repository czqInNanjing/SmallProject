import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Source URL http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 *
 * @author Qiang
 * @since 29/04/2017
 */
public class KdTree {

    private Node root;             // root of KdTree
    private List<Point2D> rangeList;
    private Node nearest;

    private class Node {
        private Point2D key;           // sorted by key
        private boolean isVertical;    // whether the Node is a vertical split node
        private Node left;
        private Node right;
        private int size;

        Node(Point2D key, boolean isVertical, int size) {
            this.key = key;
            this.isVertical = isVertical;
            this.size = size;
        }
    }


    /**
     * construct an empty set of points
     */
    public KdTree() {
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * number of points in the set
     *
     * @return
     */
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new NullPointerException("Point can not be null");
        if (!contains(p)) {
            root = insert(root, p, true);
        }

    }


    private Node insert(Node x, Point2D point2D, boolean isVertical) {
        if (x == null) return new Node(point2D, isVertical, 1);
        assert isVertical == x.isVertical;
        if (x.isVertical) {
            if (point2D.x() < x.key.x()) {
                x.left = insert(x.left, point2D, false);
            } else {
                x.right = insert(x.right, point2D, false);
            }
        } else {
            if (point2D.y() < x.key.y()) {
                x.left = insert(x.left, point2D, true);
            } else {
                x.right = insert(x.right, point2D, true);
            }
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;

    }

    /**
     * does the set contain point p?
     *
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new NullPointerException("Point can not be null");


        return contains(p, root);
    }

    private boolean contains(Point2D p, Node x) {
        if (x == null) return false;
        if (x.key.compareTo(p) == 0)
            return true;
        else if (x.isVertical) {
            if (p.x() < x.key.x())
                return contains(p, x.left);
            else
                return contains(p, x.right);
        } else {
            if (p.y() < x.key.y())
                return contains(p, x.left);
            else
                return contains(p, x.right);
        }


    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    private void draw(Node x) {
        if (x != null) {
            StdDraw.setPenRadius(0.03);
            x.key.draw();
            StdDraw.setPenRadius(0.01);
            if (x.isVertical) {
                StdDraw.setPenColor(Color.red);
                StdDraw.line(x.key.x(), 0, x.key.x(), 1);
            } else {
                StdDraw.setPenColor(Color.blue);
                StdDraw.line(0, x.key.y(), 1, x.key.y());
            }
//            StdDraw.clear();
            draw(x.left);
            draw(x.right);
        }
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
        rangeList = new ArrayList<>();
        range(rect, root);
        return rangeList;
    }

    private void range(RectHV rect, Node x) {
        if (x == null) return;
        if (rect.contains(x.key)) {
            rangeList.add(x.key);
        }


        if (x.isVertical) {
            if (rect.xmin() >= x.key.x()) {
                range(rect, x.right);
            } else if (rect.xmax() < x.key.x()) {
                range(rect, x.left);
            } else {
                range(rect, x.left);
                range(rect, x.right);
            }
        } else {
            if (rect.ymin() >= x.key.y()) {
                range(rect, x.right);
            } else if (rect.ymax() < x.key.y()) {
                range(rect, x.left);
            } else {
                range(rect, x.left);
                range(rect, x.right);
            }
        }


    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new NullPointerException("Point can not be null");
        if (isEmpty())
            return null;
        nearest = root;
        nearest(p, root);
        return nearest.key;
    }

    private void nearest(Point2D p, Node x) {
        if (x == null) return;
        if (p.distanceTo(x.key) < p.distanceTo(nearest.key)) {
            nearest = x;
        }
        if (x.isVertical) {
            if (p.x() < x.key.x()) {
                nearest(p, x.left);
                if (Math.abs(p.x() - x.key.x()) < p.distanceTo(nearest.key)) {
                    nearest(p, x.right);
                }
            } else {
                nearest(p, x.right);
                if (Math.abs(p.x() - x.key.x()) < p.distanceTo(nearest.key)) {
                    nearest(p, x.left);
                }
            }

        } else {
            if (p.y() < x.key.y()) {
                nearest(p, x.left);
                if (Math.abs(p.y() - x.key.y()) < p.distanceTo(nearest.key)) {
                    nearest(p, x.right);
                }
            } else {
                nearest(p, x.right);
                if (Math.abs(p.y() - x.key.y()) < p.distanceTo(nearest.key)) {
                    nearest(p, x.left);
                }
            }


        }


    }


    public static void main(String[] args) {
    }

}
