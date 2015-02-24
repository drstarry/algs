/*
A faster, sorting-based solution. Remarkably, it is possible to solve the problem much faster than the brute-force solution described above. Given a point p, the following method determines whether p participates in a set of 4 or more collinear points.

Think of p as the origin.
For each other point q, determine the slope it makes with p.
Sort the points according to the slopes they makes with p.
Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these points, together with p, are collinear.
Applying this method for each of the N points in turn yields an efficient algorithm to the problem. The algorithm solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.
*/
import java.lang.*;
import java.util.*;

public class Brute {

    private int N;
    private Point[] pArray;
    private int[] sortedpArray;
    private ArrayList<int[]> collinearArray;

    public Fast(String filename) {
        In in = new In(filename);
        N = in.readInt();
        pArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArray[i] = p;
        }
        collinearArray = new ArrayList<int[]>();
        sortedpArray = new int[N];
        for (int i = 0; i < N; i++) {
            sortedpArray[i] = i;
        }
        sortedpArray = sortPoints(sortedpArray, 0, sortedpArray.length - 1);
    }

    public void compare() {
        for (int i = 0; i+1 < N; i++) {
            //StdOut.println(i);
            for (int j = i+1; j+1 < N; j++) {
                // StdOut.println(j);
                for (int p = j + 1; p+1 < N; p++) {
                    // StdOut.println(p);
                    for (int q = p + 1; q < N; q++) {
                        // StdOut.println(q);
                        Point p0 = pArray[sortedpArray[i]];
                        Point p1 = pArray[sortedpArray[j]];
                        Point p2 = pArray[sortedpArray[p]];
                        Point p3 = pArray[sortedpArray[q]];
                        if (p0.SLOPE_ORDER.compare(p1,p2) == 0 && p0.SLOPE_ORDER.compare(p2,p3) == 0 ) {
                            int[] group = new int[4];
                            group[0] = sortedpArray[i];
                            group[1] = sortedpArray[j];
                            group[2] = sortedpArray[p];
                            group[3] = sortedpArray[q];
                            collinearArray.add(group);
                        }
                    }
                }
            }
        }
    }

    public void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        for (Point p: pArray) {
            p.draw();
        }

        for (int[] group: collinearArray) {
            pArray[group[0]].drawTo(pArray[group[3]]);
        }

        //display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    public void output() {
        for (int[] sortedGroup: collinearArray) {
            StdOut.print(pArray[sortedGroup[0]].toString());
            for (int i = 1; i < 4; i++) {
                StdOut.print(" -> " + pArray[sortedGroup[i]].toString());
            }
            StdOut.print("\n");
        }
    }

    private int[] sort(ArrayList<Type> array, int start, int end) {
        if (end == start) {
            return new Type[]{array[start]};
        }
        int half = (start + end)/2;
        ArrayList<Type> left = sort(array, start, half);
        ArrayList<Type> right = sort(array, half+1, end);

        ArrayList<Type> sorted = new Type[end-start+1];
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size() && k < sorted.size()) {
            if (left[i].compareTo(right[j]) == -1) {
                sorted[k++] = left[i++];
            }
            else {
                sorted[k++] = right[j++];
            }
        }
        while (i < left.size() && k < array.size()) {
            sorted[k++] = left[i++];
        }
        while (j < right.size() && k < array.size()) {
            sorted[k++] = right[j++];
        }

        return sorted;
    }

    public static void main(String[] args) {
        String filename = args[0];
        Brute b = new Brute(filename);
        b.compare();
        b.output();
        b.draw();
    }
}
