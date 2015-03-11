/*
Brute force. Write a program Brute.java that examines 4 points at a time and checks whether they all lie on the same line segment, printing out any such line segments to standard output and drawing them using standard drawing. To check whether the 4 points p, q, r, and s are collinear, check whether the slopes between p and q, between p and r, and between p and s are all equal.

The order of growth of the running time of your program should be N^4 in the worst case and it should use space proportional to N.
*/
import java.lang.*;
import java.util.*;

public class Brute {

    private int N;
    private Point[] sortedpArray;
    private ArrayList<int[]> collinearArray;

    private Brute(String filename) {
        In in = new In(filename);
        N = in.readInt();
        sortedpArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            sortedpArray[i] = p;
        }
        collinearArray = new ArrayList<int[]>();
        Arrays.sort(sortedpArray, new PointComparator());
    }

    @SuppressWarnings("hiding")
    private class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            return p1.compareTo(p2);
        }
    }

    private void compare() {
        for (int i = 0; i+1 < N; i++) {
            for (int j = i+1; j+1 < N; j++) {
                for (int p = j + 1; p+1 < N; p++) {
                    for (int q = p + 1; q < N; q++) {
                        Point p0 = sortedpArray[i];
                        Point p1 = sortedpArray[j];
                        Point p2 = sortedpArray[p];
                        Point p3 = sortedpArray[q];
                        if (p0.SLOPE_ORDER.compare(p1,p2) == 0 && p0.SLOPE_ORDER.compare(p2,p3) == 0 ) {
                            int[] group = new int[4];
                            group[0] = i;
                            group[1] = j;
                            group[2] = p;
                            group[3] = q;
                            collinearArray.add(group);
                        }
                    }
                }
            }
        }
    }

    private void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        if (sortedpArray.length > 0)
            for (Point p: sortedpArray) {
                p.draw();
            }

        if (collinearArray.size() > 0)
            for (int[] group: collinearArray) {
                sortedpArray[group[0]].drawTo(sortedpArray[group[3]]);
            }

        //display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    private void output() {
        for (int[] sortedGroup: collinearArray) {
            StdOut.print(sortedpArray[sortedGroup[0]].toString());
            for (int i = 1; i < 4; i++) {
                StdOut.print(" -> " + sortedpArray[sortedGroup[i]].toString());
            }
            StdOut.print("\n");
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        Brute b = new Brute(filename);
        b.compare();
        b.output();
        b.draw();
    }
}
