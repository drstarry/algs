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

public class Fast {

    private int N;
    private ArrayList<Point> pArray;
    private ArrayList<Point> sortedpArray;
    private ArrayList<ArrayList<Point>> collinearArray;

    private Fast(String filename) {
        In in = new In(filename);
        N = in.readInt();
        pArray = new ArrayList<Point>(N);
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            pArray.add(p);
        }
        collinearArray = new ArrayList<ArrayList<Point>>();
        sortedpArray = new ArrayList<Point>(N);
        sortedpArray = sortByPoint(pArray, 0, pArray.size() - 1);
    }

    // shuffle the array to get better performance
    private void shuffle(ArrayList<Point> arr) {
        int index;
        Point temp;
        Random random = new Random();
        for (int i = arr.size() - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = arr.get(index);
            arr.set(index, arr.get(i));
            arr.set(i, temp);
        }
    }

    private void compute() {
        //shuffle(sortedpArray);
        for (int i = 0; i < N; i++) {
            Point p0 = sortedpArray.get(i); // the current origin point
            sortBySlope(sortedpArray, p0, i+1, N-1);
        }
    }

    // implement a Dijskstra 3-way partationing algorithms
    private void sortBySlope(ArrayList<Point> array, Point p0, int start, int end) {
        if (start >= end) {
            return;
        }
        Point pv = array.get(start); // pivot of comparision with origin p0
        int lt = start; // the cursor for: less thant pivot
        int gt = end; // the cursor for: greater than pivot
        int et = start; // the cursor for: equal to pivot
        while (et < end) {
            int cmp = p0.SLOPE_ORDER.compare(array.get(et), pv);
            if (cmp < 0) {
                Point temp = pv;
                pv = array.get(et++);
                array.set(lt++, temp);
            }
            else if (cmp > 0) {
                Point temp = array.get(et);
                array.set(et, array.get(gt));
                array.set(gt++, temp);
            }
            else {
                et++;
            }
        }
        if (Math.abs(lt-et) >= 2) {
            ArrayList<Point> group = sortByPoint(array, lt, et);
            group.add(0, p0);
            collinearArray.add(group);
        }
        sortBySlope(array, p0, start, lt-1);
        sortBySlope(array, p0, et+1, end);
    }

    private void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        for (Point p: sortedpArray) {
            p.draw();
        }

        for (ArrayList<Point> group: collinearArray) {
            group.get(0).drawTo(group.get(group.size()-1));
        }

        //display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    private void output() {
        for (ArrayList<Point> sortedGroup: collinearArray) {
            StdOut.print(sortedGroup.get(0).toString());
            for (int i = 1; i < sortedGroup.size(); i++) {
                StdOut.print(" -> " + sortedpArray.get(i).toString());
            }
            StdOut.print("\n");
        }
    }

    private ArrayList<Point> sortByPoint(ArrayList<Point> array, int start, int end) {
        if (end == start) {
            return new ArrayList<Point>(Arrays.asList(array.get(start)));
        }
        int half = (start + end)/2;
        ArrayList<Point> left = sortByPoint(array, start, half);
        ArrayList<Point> right = sortByPoint(array, half+1, end);

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
        Fast b = new Fast(filename);
        b.compute();
        b.output();
        b.draw();
    }
}
