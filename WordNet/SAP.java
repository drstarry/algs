// Shortest ancestral path. An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length.
// Corner cases.  All methods should throw a java.lang.NullPointerException if any argument is null. All methods should throw a java.lang.IndexOutOfBoundsException if any argument vertex is invalid—not between 0 and G.V() - 1.

// Performance requirements.  All methods (and the constructor) should take time at most proportional to E + V in the worst case, where E and V are the number of edges and vertices in the digraph, respectively. Your data type should use space proportional to E + V.

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;

public class SAP {

    private Digraph g;
    private int root;

    private class sapUnit {

    }
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new NullPointerException();
        }
        g = G;
        for (int i = 0; i < g.V(); i++) {
            if (g.outdegree(i) == 0) {
                root = i;
                break;
            }
        }
    }

    private void swap(int v, int w) {
        int temp = v;
        v = w;
        w = temp;
    }

    private String hashCode(int v, int w) {
        if (v > w) {
            swap(v, w);
        }
        return Integer.toString(v) + "&" + Integer.toString(w);
    }

    private boolean isValidArg(int a) {
        if (a < 0 || a > g.V() - 1) {
            return false;
        }
        else
            return true;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValidArg(v) || !isValidArg(w)) {
            throw new IndexOutOfBoundsException();
        }

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValidArg(v) || !isValidArg(w)) {
            throw new IndexOutOfBoundsException();
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        for (int vv: v) {
            for (int ww: w) {

            }
        }
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }

        for (int vv: v) {
            for (int ww: w) {

            }
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
