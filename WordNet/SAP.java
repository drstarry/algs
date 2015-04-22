// Shortest ancestral path. An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length.
// Corner cases.  All methods should throw a java.lang.NullPointerException if any argument is null. All methods should throw a java.lang.IndexOutOfBoundsException if any argument vertex is invalid—not between 0 and G.V() - 1.

// Performance requirements.  All methods (and the constructor) should take time at most proportional to E + V in the worst case, where E and V are the number of edges and vertices in the digraph, respectively. Your data type should use space proportional to E + V.

import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.util.Hashtable;
import java.util.HashSet;

public class SAP {

    private Digraph g;
    private int root;
    private Hashtable<String, sapUnit> sapTable;

    private class sapUnit {
        private int v; // vertice
        private String id; // indentify whose ancestor
        private int length; // the length of LCA path
        public sapUnit(String id, int v, int length) {
            if (id == null) {
                throw new NullPointerException();
            }
            this.v = v;
            this.id = id;
            this.length = length;
        }
        public int v() {
            return this.v;
        }
        public int len() {
            return this.length;
        }
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
        sapTable = new Hashtable<String, sapUnit>();
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

    // compute the LCA between v and w
    private void computePairLCA(int v, int w) {
        String id = hashCode(v, w);
        if (!sapTable.containsKey(id)) {
            BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(g, v);
            BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(g, w);
            int v2wLen = Integer.MAX_VALUE;
            int w2vLen = Integer.MAX_VALUE;
            int cur = w;
            int ancestor = -1;
            int length = Integer.MAX_VALUE;
            Queue<Integer> bfsQ = new Queue<Integer>();
            HashSet<Integer> visited = new HashSet<Integer>();
            bfsQ.enqueue(cur);

            if (bfsV.hasPathTo(w)) {
                v2wLen = bfsV.distTo(w);
                length = v2wLen;
                ancestor = w;
            }
            if (bfsW.hasPathTo(v)) {
                w2vLen = bfsW.distTo(v);
                if (w2vLen < length) {
                    length = w2vLen;
                    ancestor = v;
                }
            }
            while (!bfsQ.isEmpty()) {
                cur = bfsQ.dequeue();
                visited.add(cur);
                if (visited.size() > g.V()) {
                    break;
                }
                if(bfsV.hasPathTo(cur)) {
                    int curLen = bfsV.distTo(cur) + bfsW.distTo(cur);
                    // StdOut.println("cur:" + cur + " len:" + curLen);
                    if (curLen < length) {
                        length = curLen;
                        ancestor = cur;
                    }
                    // else if (curLen > length) {
                    //     break;
                    // }
                }
                for (int vAdj: g.adj(cur)) {
                    if (!visited.contains(vAdj)) {
                        bfsQ.enqueue(vAdj);
                    }
                    // StdOut.println("adj:" + vAdj);
                }
            }

            if (ancestor == -1) {
                sapTable.put(id, new sapUnit(id, -1, -1));
            }
            else
                sapTable.put(id, new sapUnit(id, ancestor, length));
        }
    }

    private int firstOfIterable(Iterable<Integer> a) {
        for (int v : a)
            return v;
        return Integer.MAX_VALUE;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (!isValidArg(v) || !isValidArg(w)) {
            throw new IndexOutOfBoundsException();
        }
        String id = hashCode(v, w);
        computePairLCA(v, w);
        return sapTable.get(id).len();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (!isValidArg(v) || !isValidArg(w)) {
            throw new IndexOutOfBoundsException();
        }
        String id = hashCode(v, w);
        computePairLCA(v, w);
        return sapTable.get(id).v();
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        int len = Integer.MAX_VALUE;
        for (int vv: v) {
            for (int ww: w) {
                String id = hashCode(vv, ww);
                computePairLCA(vv, ww);
                if (sapTable.get(id).len() != -1)
                    len = Math.min(len, sapTable.get(id).len());
            }
        }
        if (len == Integer.MAX_VALUE) {
            return -1;
        }
        return len;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) {
            throw new NullPointerException();
        }
        int len = Integer.MAX_VALUE;
        int ver = -1;
        for (int vv: v) {
            for (int ww: w) {
                String id = hashCode(vv, ww);
                computePairLCA(vv, ww);
                if (len > sapTable.get(id).len() && sapTable.get(id).len() != -1) {
                    len = sapTable.get(id).len();
                    ver = sapTable.get(id).v();
                }
            }
        }
        return ver;
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
}
