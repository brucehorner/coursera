/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

/*
 * Your job is to add the following components.

    The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates.
    Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either
    y0 < y1 or if y0 = y1 and x0 < x1.

    The slopeTo() method should return the slope between the invoking point (x0, y0) and the argument
    point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the slope of a horizontal line 
    segment as positive zero [added 7/29]; treat the slope of a vertical line segment as positive infinity; 
    treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

    The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0). 
    Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
     is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical, and degenerate line segments 
     as in the slopeTo() method. 
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Point implements Comparable<Point> {

    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    
    // compare points by slope
	// The SLOPE_ORDER comparator should compare points by the slopes they make with the invoking point (x0, y0). 
    // Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
    // is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical, and degenerate line segments 
    // as in the slopeTo() method.
    private class BySlope implements Comparator<Point> {
        public int compare(Point a, Point b) {
        	double sa = Point.this.slopeTo(a);
        	double sb = Point.this.slopeTo(b);
        	if (sa < sb) return -1;
        	if (sa > sb) return 1;
        	return 0;
        }
    }
    
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    // The slopeTo() method should return the slope between the invoking point (x0, y0) and the argument
    // point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the slope of a horizontal line 
    // segment as positive zero [added 7/29]; treat the slope of a vertical line segment as positive infinity; 
    // treat the slope of a degenerate line segment (between a point and itself) as negative infinity.    
    public double slopeTo(Point that) {
    	boolean sameX = that.x == x; 
        if (that.y == y)
        	if (sameX)
        		return Double.NEGATIVE_INFINITY;
        	else
        		return (1.0 - 1.0) /  1.0;   // positive zero ( 0.0);
        if (sameX) return Double.POSITIVE_INFINITY;    	
    	return  ((double)that.y - (double)y) / ((double)that.x - (double)x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    // The compareTo() method should compare points by their y-coordinates, breaking ties by their x-coordinates.
    // Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either
    //    y0 < y1 or if y0 = y1 and x0 < x1. 
    // Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
    // integer as this object is less than, equal to, or greater than the specified object. 
    public int compareTo(Point that)
    {
    	// assume that is not null
    	if (y < that.y) return -1;
    	if (y > that.y) return 1;
    	if (x < that.x) return -1;
    	if (x > that.x) return 1;
    	return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args)
    {
    	List<Point> pts = new ArrayList<Point>();
        pts.add(new Point(0, 0));
        pts.add(new Point(10, 10));
        pts.add(new Point(-10, -10));
        pts.add(new Point(10, 5));
        pts.add(new Point(10, -5));
        pts.add(new Point(-7, -5));
        pts.add(new Point(-7, 5));
        
        System.out.println(pts.get(0).compareTo(pts.get(1)) == -1);
        System.out.println(pts.get(1).compareTo(pts.get(0)) == 1);
        System.out.println(pts.get(0).compareTo(pts.get(0)) == 0);
        System.out.println(pts.get(5).compareTo(pts.get(4)) == -1);
        System.out.println(pts.get(6).compareTo(pts.get(5)) == 1);
        

        System.out.println(pts.get(0).slopeTo(pts.get(1)) == 1.0);
        System.out.println(pts.get(1).slopeTo(pts.get(0)) == -1.0);
        System.out.println(pts.get(0).slopeTo(pts.get(0)) == Double.NEGATIVE_INFINITY);
        System.out.println(pts.get(5).slopeTo(pts.get(4)) == 0.0);
        System.out.println(pts.get(6).slopeTo(pts.get(5)) == Double.POSITIVE_INFINITY);
        System.out.println(pts.get(0).slopeTo(pts.get(3)) == 0.5);
        System.out.println(pts.get(0).slopeTo(pts.get(5)) == 5.0/7.0);
    }
}
