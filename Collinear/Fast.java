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
    private Point[] pArray;
    private Point[] sortedpArray;
    private ArrayList<Point[]> collinearArray;

    private Fast(String filename) {
        In in = new In(filename);
        N = in.readInt();
        pArray = new Point[N];
        sortedpArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            sortedpArray[i] = p;
            pArray[i] = p;
        }
        collinearArray = new ArrayList<Point[]>();
        pArray = sortByPoint(pArray, 0, pArray.length - 1);
        sortedpArray = sortByPoint(sortedpArray, 0, sortedpArray.length - 1);
    }

    // shuffle the array to get better performance
    // private void shuffle(ArrayList<Point> arr) {
    //     int index;
    //     Point temp;
    //     Random random = new Random();
    //     for (int i = arr.length - 1; i > 0; i--)
    //     {
    //         index = random.nextInt(i + 1);
    //         temp = arr.get(index);
    //         arr.set(index, arr[i]);
    //         arr.set(i, temp);
    //     }
    // }

    private void compute() {
        //shuffle(sortedpArray);
        for (int i = 0; i+1 < N; i++) {
            Point p0 = sortedpArray[i]; // the current origin point
            Arrays.sort(sortedpArray, i+1, N-1, p0.SLOPE_ORDER);
            int low=i+1, high=i+2, cmp = 1, j = i+1;
            while (high < N) {
                cmp = p0.SLOPE_ORDER.compare(sortedpArray[low], sortedpArray[high]);
                // /
                if (cmp==0 && high == N-1) {
                    if (high-low>=2) {
                        addResult(p0, low, high);
                    }
                }
                if (cmp != 0) {
                    if (high-low>=3) {
                        addResult(p0, low, high-1);
                    }
                    low = high;
                }
                high++;
            }
            sortedpArray = sortByPoint(sortedpArray, 0, sortedpArray.length - 1);
        }
    }

    private void addResult(Point p0, int start, int end) {
        Point[] group = new Point[end-start+2];
        group[0] = p0;
        boolean hasGroup = false;
        for (int i = 1; i < group.length; i++, start++) {
            group[i] = sortedpArray[start];
        }
        for (Point[] oldGroup: collinearArray) {
            if (oldGroup[0].SLOPE_ORDER.compare(group[0], group[1]) == 0 && oldGroup[1].SLOPE_ORDER.compare(group[0], group[1]) == 0) {
                if (oldGroup.length < group.length) {
                    collinearArray.remove(oldGroup);
                }
                else
                    hasGroup = true;
                break;
            }
        }
        if (!hasGroup) {
            sortByPoint(group, 0, group.length-1);
            collinearArray.add(group);
        }
    }

    // implement a Dijskstra 3-way partationing algorithms
    // private void sortBySlope(Point[] array, Point p0, int start, int end) {
    //     if (start >= end) {
    //         return;
    //     }
    //     Point pv = array[start]; // pivot of comparision with origin p0
    //     int lt = start; // the cursor for: less thant pivot
    //     int gt = end; // the cursor for: greater than pivot
    //     int et = start + 1; // the cursor for: equal to pivot
    //     while (et <= gt) {
    //         int cmp = p0.SLOPE_ORDER.compare(array[et], pv);
    //         if (cmp < 0) {
    //             Point temp = pv;
    //             pv = array[et++];
    //             array[lt++] = temp;
    //         }
    //         else if (cmp > 0) {
    //             Point temp = array[et];
    //             array[et] = array[gt];
    //             array[gt--] = temp;
    //         }
    //         else {
    //             et++;
    //         }
    //     }
    //     // if ((gt-lt) >= 3) {
    //     //     StdOut.println(gt-lt);
    //     //     ArrayList<Point> newGroup = sortByPoint(array, lt, gt);
    //     //     newGroup.add(0, p0);
    //     //     int i;
    //     //     for (i = 0; i < collinearArray.length; i++) {
    //     //         if (collinearArray[i].containsAll(newGroup))
    //     //             break;
    //     //     }
    //     //     if (i == collinearArray.length)
    //     //         collinearArray.add(newGroup);
    //     //     StdOut.println("!!!");
    //     //     for (int j=0; j<newGroup.length; j++) {
    //     //         StdOut.println(newGroup[j]);
    //     //     }
    //     //     StdOut.println("!!!");
    //     // }
    //     sortBySlope(array, p0, start, lt-1);
    //     sortBySlope(array, p0, gt+1, end);
    // }

    private void draw() {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        for (Point p: sortedpArray) {
            p.draw();
        }

        for (Point[] group: collinearArray) {
            group[0].drawTo(group[group.length-1]);
        }

        //display to screen all at once
        StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
    }

    private void output() {
        for (Point[] sortedGroup: collinearArray) {
            for (int i = 0; i < sortedGroup.length - 1 ; i++) {
                StdOut.print(sortedGroup[i].toString() + " -> ");
            }
            StdOut.print(sortedGroup[sortedGroup.length-1].toString() + "\n");
        }
    }

    private Point[] sortByPoint(Point[] array, int start, int end) {
        if (end == start) {
            return new Point[] {array[start]};
        }
        int half = (start + end)/2;
        Point[] left = sortByPoint(array, start, half);
        Point[] right = sortByPoint(array, half+1, end);

        Point[] sorted = new Point[end-start+1];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length && k < end-start+1) {
            if (left[i].compareTo(right[j]) == -1) {
                sorted[k++] = left[i++];
            }
            else {
                sorted[k++] = right[j++];
            }
        }
        while (i < left.length && k < end-start+1) {
            sorted[k++] = left[i++];
        }
        while (j < right.length && k < end-start+1) {
            sorted[k++] = right[j++];
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
