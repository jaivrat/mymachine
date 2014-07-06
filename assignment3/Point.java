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

import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder(this);
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    
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
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        
        //non degenerte
        if(this.x != that.x)
        {
            return (double) (that.y - this.y)/(double)(that.x - this.x);
        }
        
        //Degenerate - here x's same, y same
        if(this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else //y diff, x same
            return Double.POSITIVE_INFINITY;
        
    }
    
    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.y < that.y)
            return +1;
        else if(this.y > that.y)
            return -1;
        else
        {
            if(this.x < that.x)
                return +1;
            else
                return -1;
        }
    }
    
    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class SlopeOrder implements Comparator<Point> 
    { 
        private Point mInvoker;
        
        SlopeOrder(Point invokingPoint)
        {
            mInvoker = invokingPoint;
        }
        
        public int compare(Point q1, Point q2) 
        {    
            Double slope1 = mInvoker.slopeTo(q1);
            Double slope2 = mInvoker.slopeTo(q2);
            if(slope1 < slope2)
                return +1;
            else
                return -1;
        }
    }
    
    
    // unit test
    public static void main(String[] args) {
       
        Point[] points = new Point[6];
        int i = 0;
        points[i++] = new Point(2,1);
        points[i++] = new Point(0,1);
        points[i++] = new Point(3,3);
        points[i++] = new Point(-3,2);
        points[i++] = new Point(0,0);  
        points[i++] = new Point(0,0);
        
        java.util.Arrays.sort(points);
        for(int j=0; j < points.length; ++j)
            System.out.println(points[j]);
        
        System.out.println("-----");
        java.util.Arrays.sort(points, new Point(0,0).SLOPE_ORDER);
        for(int j=0; j < points.length; ++j)
            System.out.println(points[j]);
        
        Point p1 = new Point(2,5);
        Point p2 = new Point(2,6);
        System.out.println("Slope = " + p1.slopeTo(p2));
    }
}