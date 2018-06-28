package collinear;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int num = 0;
    private LineSegment[] segments;
    private ArrayList<Point> minsMax = new ArrayList<>();
    private ArrayList<Double> slopes = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {

        if (points == null){
            throw new IllegalArgumentException("arguments are null");
        }
        int n = points.length-1;

        checkForDuplicates(points, n);

        for (int i = 0; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                for (int k = j + 1; k <= n; k++) {
                    for (int l = k + 1; l <= n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[k]) == points[i].slopeTo(points[l])) {
                            Point[] p = new Point[4];
                            p[0] = points[i];
                            p[1] = points[j];
                            p[2] = points[k];
                            p[3] = points[l];

                            Arrays.sort(p);

                            addPointsToFormLine(p);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[minsMax.size()/2];

    }

    private void addPointsToFormLine(Point[] p) {
        boolean skip = false;
        for(int op=0; op<=minsMax.size()-1; op=op+2) {
            if(slopes.get(op) == p[0].slopeTo(p[3]))
            {
                if((p[0]==minsMax.get(op) && p[3]==minsMax.get(op+1))||(p[3]==minsMax.get(op) && p[0]==minsMax.get(op+1))){
                    skip = true;
                    break;
                }
                if(slopes.get(op)==minsMax.get(op).slopeTo(p[0]) && p[0].compareTo(minsMax.get(op)) < 0){
                    minsMax.set(op, p[0]);
                    skip = true;
                    break;
                }
                if(slopes.get(op) == p[3].slopeTo(minsMax.get(op+1)) && p[3].compareTo(minsMax.get(op+1)) > 0) {
                    minsMax.set(op + 1, p[3]);
                    skip = true;
                    break;
                }

                if(p[0].compareTo(minsMax.get(op)) == 0 || p[3].compareTo(minsMax.get(op+1)) == 0){
                    skip = true;
                    break;
                }

                if(slopes.get(op) == p[0].slopeTo(minsMax.get(op)) && slopes.get(op)==minsMax.get(op+1).slopeTo(p[3])){
                    skip = true;
                    break;
                }
            }
        }
        if(!skip)
        {
            minsMax.add(p[0]);
            minsMax.add(p[3]);
            slopes.add(p[0].slopeTo(p[3]));
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
        for(int i=0,j=0; j<=minsMax.size()-1; i++,j=j+2){
            segments[i] = new LineSegment(minsMax.get(j),minsMax.get(j+1));
        }
        return segments;
    }
}