/*
Brute force. Write a program Brute.java that examines 4 points at a time and checks whether they all lie on the same line segment, printing out any such line segments to standard output and drawing them using standard drawing. To check whether the 4 points p, q, r, and s are collinear, check whether the slopes between p and q, between p and r, and between p and s are all equal.

The order of growth of the running time of your program should be N^4 in the worst case and it should use space proportional to N.
*/
import java.lang.*;
import java.util.*;

public class Brute {

    private int N;
    private ArrayList<Point> pArray;
    //private int[] sortedpArray;
    private ArrayList<Point> sortedpArray;
    private ArrayList<int[]> collinearArray;

    Private Brute(String filename) {
        In in = new In(filename);
        N = in.readInt();
        pArray = new ArrayList<Point>(N);
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArray.add(p);
        }
        collinearArray = new ArrayList<int[]>();
        sortedpArray = new ArrayList<Point>(N);
        sortedpArray = sort(pArray, 0, pArray.size() - 1);
    }

    Private void compare() {
        for (int i = 0; i+1 < N; i++) {
            for (int j = i+1; j+1 < N; j++) {
                for (int p = j + 1; p+1 < N; p++) {
                    for (int q = p + 1; q < N; q++) {
                        Point p0 = sortedpArray.get(i);
                        Point p1 = sortedpArray.get(j);
                        Point p2 = sortedpArray.get(p);
                        Point p3 = sortedpArray.get(q);
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

    Private void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        for (Point p: sortedpArray) {
            p.draw();
        }

        for (int[] group: collinearArray) {
            sortedpArray.get(group[0]).drawTo(sortedpArray.get(group[3]));
        }

        //display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    Private void output() {
        for (int[] sortedGroup: collinearArray) {
            StdOut.print(sortedpArray.get(sortedGroup[0]).toString());
            for (int i = 1; i < 4; i++) {
                StdOut.print(" -> " + sortedpArray.get(sortedGroup[i]).toString());
            }
            StdOut.print("\n");
        }
    }

    Private ArrayList<Point> sort(ArrayList<Point> array, int start, int end) {
        if (end == start) {
            return new ArrayList<Point>(Arrays.asList(array.get(start)));
        }
        int half = (start + end)/2;
        ArrayList<Point> left = sort(array, start, half);
        ArrayList<Point> right = sort(array, half+1, end);

        ArrayList<Point> sorted = new ArrayList<Point>(end-start+1);
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size() && k < end-start+1) {
            if (left.get(i).compareTo(right.get(j)) == -1) {
                sorted.add(left.get(i++));
                k++;
            }
            else {
                sorted.add(right.get(j++));
                k++;
            }
        }
        while (i < left.size() && k < end-start+1) {
            sorted.add(left.get(i++));
            k++;
        }
        while (j < right.size() && k < end-start+1) {
            sorted.add(right.get(j++));
            k++;
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
