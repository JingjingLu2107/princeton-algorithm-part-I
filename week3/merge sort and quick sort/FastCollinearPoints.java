import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final Point[] points;
    //    private int segsCount = -1;
    private final int len;


    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] pts) {

        if (pts == null) {
            throw new IllegalArgumentException();
        }

        int temlen = pts.length;

        points = new Point[temlen];

        for (int i = 0; i < temlen; i++) {

            if (pts[i] == null) {
                throw new IllegalArgumentException();
            }

            for (int j = 0; j < i; j++) {
                if (points[j].compareTo(pts[i]) == 0) {
                    throw new IllegalArgumentException();
                }
            }

            points[i] = pts[i];
        }

        len = points.length;

        Arrays.sort(points);
        segments();

    }

    // the number of line segments
    public int numberOfSegments() {

        return segments().length;
    }

    // the line segments
    public LineSegment[] segments() {

//        segsCount = 0;

        ArrayList<LineSegment> segList = new ArrayList<LineSegment>();

        Point[] ptsCopy = new Point[points.length];

        for (int i = 0; i < len; i++) {
            ptsCopy[i] = points[i];
        }


        for (int p = 0; p < points.length; p++) {
            Arrays.sort(ptsCopy, points[p].slopeOrder());

            ArrayList<Point> linePoints = new ArrayList<Point>();

            int currentPosition = 2;
            int firstScopePosition = 1;

            double currentSlope = ptsCopy[0].slopeTo(ptsCopy[firstScopePosition]);
            boolean isValidScope = true;
            linePoints.clear();

            //check whether the firstScopePosition's point is valid or not

            if (ptsCopy[0].compareTo(ptsCopy[firstScopePosition]) < 0) {
                linePoints.add(ptsCopy[firstScopePosition]);
            } else {
                isValidScope = false;
            }

            while (currentPosition < len) {

                // to check whether the current point has same slope as the firstScope position
                if (ptsCopy[0].slopeTo(ptsCopy[currentPosition]) == currentSlope) {

                    // if yes, continue to check whether the current position is less then the p point.

                    //  the current point is larger than the P point.
                    if (ptsCopy[0].compareTo(ptsCopy[currentPosition]) < 0) {
                        if (isValidScope) {
                            linePoints.add(ptsCopy[currentPosition]);
                        }

                        // the current point is less than the P point. that means the line is counted already.
                    } else {
                        isValidScope = false;
                    }

                    // check whether the position reached the end of the array
                    if (currentPosition == len - 1) {

                        // add the valid line containing at least 4 points into segLis array

                        if (isValidScope && (currentPosition - firstScopePosition + 1) >= 3) {

                            Point[] linePointsArray = new Point[linePoints.size()];
                            linePoints.toArray(linePointsArray);
                            Arrays.sort(linePointsArray);
                            segList.add(new LineSegment(ptsCopy[0], linePointsArray[linePointsArray.length - 1]));
//                            segsCount++;
                        }
                    }

                    currentPosition++;


                    // if not, reset firstScopePosition to new value. reset valid scope and current scope
                } else {

                    // add the valid line containing at least 4 points into segLis array
                    if (isValidScope && (currentPosition - firstScopePosition) >= 3) {

                        Point[] linePointsArray = new Point[linePoints.size()];
                        linePoints.toArray(linePointsArray);
                        Arrays.sort(linePointsArray);
                        segList.add(new LineSegment(ptsCopy[0], linePointsArray[linePointsArray.length - 1]));
//                        segsCount++;
                    }

                    // reset valid scope
                    isValidScope = true;
                    firstScopePosition = currentPosition;
                    currentSlope = ptsCopy[0].slopeTo(ptsCopy[firstScopePosition]);
                    linePoints.clear();

                    //check whether the firstScopePosition's point is valid or not
                    if (ptsCopy[0].compareTo(ptsCopy[firstScopePosition]) < 0) {
                        linePoints.add(ptsCopy[firstScopePosition]);
                    } else {
                        isValidScope = false;
                    }

                    currentPosition++;
                }
            }

        }

        LineSegment[] segsArray = new LineSegment[segList.size()];
        segList.toArray(segsArray);

        return segsArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}






