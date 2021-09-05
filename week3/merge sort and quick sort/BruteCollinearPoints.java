import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final Point[] points;
//    private int segsCount = -1;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pts) {

        if (pts == null) {
            throw new IllegalArgumentException();
        }

        int len = pts.length;

        points = new Point[len];

        for (int i = 0; i < len; i++) {

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

        Arrays.sort(points);
        segments();

    }

    // the number of line segments
    public int numberOfSegments() {

//        if (segsCount >= 0) {
//            return segsCount;
//        }
//
//        segments();
//        return segsCount;
        return segments().length;
    }

    // the line segments
    public LineSegment[] segments() {

//        segsCount = 0;
        ArrayList<LineSegment> segList = new ArrayList<LineSegment>();

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {

                        double pToq = points[p].slopeTo(points[q]);
                        double pTor = points[p].slopeTo(points[r]);
                        double pTos = points[p].slopeTo(points[s]);

                        if (pToq == pTor && pTor == pTos) {
                            segList.add(new LineSegment(points[p], points[s]));
//                            segsCount++;
                        }
                    }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}






