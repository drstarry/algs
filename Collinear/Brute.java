/*
Brute force. Write a program Brute.java that examines 4 points at a time and checks whether they all lie on the same line segment, printing out any such line segments to standard output and drawing them using standard drawing. To check whether the 4 points p, q, r, and s are collinear, check whether the slopes between p and q, between p and r, and between p and s are all equal.

The order of growth of the running time of your program should be N^4 in the worst case and it should use space proportional to N.
*/
import java.lang.*;
import java.util.*;

public class Brute {

    private int N;
    private Point[] pArray;
    private int[] sortedpArray;
    private ArrayList<int[]> collinearArray;

    public Brute(String filename) {
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

    private int[] sortPoints(int[] array, int start, int end) {
        if (end == start) {
            return new int[]{array[start]};
        }
        int half = (start + end)/2;
        int[] left = sortPoints(array, start, half);
        int[] right = sortPoints(array, half+1, end);

        int[] sorted = new int[end-start+1];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length && k < sorted.length) {
            if (pArray[left[i]].compareTo(pArray[right[j]]) == -1) {
                sorted[k++] = left[i++];
            }
            else {
                sorted[k++] = right[j++];
            }
        }
        while (i < left.length && k < array.length) {
            sorted[k++] = left[i++];
        }
        while (j < right.length && k < array.length) {
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
