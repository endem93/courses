package collinear;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<Point> minsMax = new ArrayList<>();
    private ArrayList<Double> slopes = new ArrayList<>();
    
    public FastCollinearPoints(Point[] points){
        if (points == null){
            throw new IllegalArgumentException("arguments are null");
        }
        int n = points.length-1;
        Point[] points2 = points.clone();

        checkForDuplicates(points, n);
        for(Point p: points2)
        {
            Arrays.sort(points, p.slopeOrder());
            for(int i = 1; i<=n-2; i++){
                if(points[i].slopeTo(p)==points[i+1].slopeTo(p)&&points[i+1].slopeTo(p)==points[i+2].slopeTo(p)){
                    Point[] colins = new Point[4];
                    colins[0] = p;
                    colins[1] = points[i];
                    colins[2] = points[i+1];
                    colins[3] = points[i+2];

                    Arrays.sort(colins);

                    addPointsToFormLine(colins);
                }
            }
        }
    }


    private void addPointsToFormLine(Point[] p) {
        boolean skip = false;
        Point point0 = p[0];
        Point point3 = p[3];
        for(int op = 0; op<=minsMax.size()-1; op=op+2) {
            Double currentSlope = slopes.get(op);
            if(currentSlope == point0.slopeTo(point3))
            {
                Point currentMin = minsMax.get(op);
                Point currentMax = minsMax.get(op + 1);
                if((point0 == currentMin && point3 == currentMax)||(point3 == currentMin && point0 == currentMax)){
                    skip = true;
                    break;
                }
                if(currentSlope == currentMin.slopeTo(point0) && point0.compareTo(currentMin) < 0){
                    minsMax.set(op, point0);
                    skip = true;
                    break;
                }
                if(currentSlope == point3.slopeTo(currentMax) && point3.compareTo(currentMax) > 0) {
                    minsMax.set(op + 1, point3);
                    skip = true;
                    break;
                }

                if(point0.compareTo(currentMin) == 0 || point3.compareTo(currentMax) == 0){
                    skip = true;
                    break;
                }

                if(currentSlope == point0.slopeTo(currentMin) && currentSlope == currentMax.slopeTo(point3)){
                    skip = true;
                    break;
                }
            }
        }
        if(!skip)
        {
            minsMax.add(point0);
            minsMax.add(point3);
            slopes.add(point0.slopeTo(point3));
            slopes.add(-100.00);
        }
    }
    
    private void checkForDuplicates(Point[] points, int n) {
        for (int i = 0; i != points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("element is null");
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new IllegalArgumentException("element is null");
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("duplicate arguments given");
                }
            }
        }
    }

    public int numberOfSegments() {
        return minsMax.size()/2;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[minsMax.size()/2];
        for(int i=0,j=0; j<=minsMax.size()-1; i++,j=j+2){
            segments[i] = new LineSegment(minsMax.get(j),minsMax.get(j+1));
        }
        return segments;
    }
}
