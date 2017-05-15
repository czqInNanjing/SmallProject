package exer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given n points on a 2D plane, find the maximum number of points that lie on the same straight line.
 *
 * @author Qiang
 * @since 13/05/2017
 */
public class MaxPointsOnALine {


    public int maxPoints(Point[] points) {
        if (points == null || points.length == 0)
            return 0;

        if (points.length == 1)
            return 1;


        int max = 2;
        List<Double> doubles = new ArrayList<>(points.length);
        int samePoint = 0;
        for (int i = 0; i < points.length; i++) {


            for (int j = i + 1; j < points.length; j++) {
                if (points[i].x == points[j].x && points[i].y == points[j].y)
                    samePoint++;
                else
                    doubles.add(slope(points[i], points[j]));
            }
            Collections.sort(doubles);
            int tempMax = 2;
            int count = 2;
            for (int j = 1; j < doubles.size(); j++) {
                if (compare(doubles.get(j), doubles.get(j - 1))) {
                    count++;
                } else {
                    if (count > tempMax)
                        tempMax = count;
                    count = 2;

                }
            }
            if (count > tempMax)
                tempMax = count;

            if (doubles.size() != 0)
                tempMax += samePoint;
            else
                tempMax = samePoint + 1;

            if (tempMax > max)
                max = tempMax;

            samePoint = 0;
            doubles.clear();
        }

        return max;
    }

    private boolean compare(double d1, double d2) {
        if (d1 == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) {
            return true;
        }

        if (d1 == 0 && d2 == 0)
            return true;
        return (d1 - d2) < 0.0000000000000000001;
    }

    private double slope(Point p1, Point p2) {
        if (p1.x == p2.x)
            return Double.POSITIVE_INFINITY;

        return (p1.y - p2.y) / (double) (p1.x - p2.x);


    }


    public static void main(String[] a) {
        Point p1 = new Point(2 ,3);
        Point[] ps = {new Point(2,3), new Point(94911151,94911150), new Point(94911152,94911151)};
        MaxPointsOnALine maxPointsOnALine = new MaxPointsOnALine();

        System.out.println(maxPointsOnALine.maxPoints(ps));
    }
}
