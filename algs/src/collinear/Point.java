package collinear;

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if(this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;
        }
        if(this.y == that.y){
            return 0;
        }
        if(this.x == that.x){
            return Double.POSITIVE_INFINITY;
        }
        double num = (that.y-this.y);
        double denum = (that.x-this.x);
        return num/denum;
    }

    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return 0;
        } else if (this.y < that.y || (this.y == that.y && this.x < that.x) ) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Point thisPoint = this;

        class BySlopeOrder implements Comparator<Point>
        {
            public int compare(Point v, Point w) {
                if (thisPoint.slopeTo(v) < thisPoint.slopeTo(w)) {
                    return -1;
                }
                if (thisPoint.slopeTo(v) > thisPoint.slopeTo(w)) {
                    return 1;
                }
                return 0;
            }
        }

        return new BySlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }



    /**
     * Unit tests the collinear.Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}