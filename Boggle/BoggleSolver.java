// The Boggle game. Boggle is a word game designed by Allan Turoff and distributed by Hasbro. It involves a board made up of 16 cubic dice, where each die has a letter printed on each of its sides. At the beginning of the game, the 16 dice are shaken and randomly distributed into a 4-by-4 tray, with only the top sides of the dice visible. The players compete to accumulate points by building valid words out of the dice according to the following rules:

// A valid word must be composed by following a sequence of adjacent dice—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.
// A valid word can use each die at most once.
// A valid word must contain at least 3 letters.
// A valid word must be in the dictionary (which typically does not contain proper nouns).

// word length   points
// 0–2             0
// 3–4             1
// 5               2
// 6               3
// 7               5
// 8+              11

// ------------------------------progress--------------------------------------
// These are purely suggestions for how you might make progress. You do not have to follow these steps.

// Familiarize yourself with the BoggleBoard.java data type.
// Use a standard set data type to represent the dictionary, e.g., a SET<String>, a TreeSet<String>, or a HashSet<String>.
// Create the data type BoggleSolver. Write a method based on depth-first search to enumerate all strings that can be composed by following sequences of adjacent dice. That is, enumerate all simple paths in the Boggle graph (but there is no need to explicitly form the graph). For now, ignore the special two-letter sequence Qu.
// Now, implement the following critical backtracking optimization: when the current path corresponds to a string that is not a prefix of any word in the dictionary, there is no need to expand the path further. To do this, you will need to create a data structure for the dictionary that supports the prefix query operation: given a prefix, is there any word in the dictionary that starts with that prefix?
// Deal with the special two-letter sequence Qu.

// ------------------------------optimize--------------------------------------
// You will likely need to optimize some aspects of your program to pass all of the performance points (which are, intentionally, more challenging on this assignment). Here are a few ideas:

// Make sure that you have implemented the critical backtracking optimization described above. This is, by far, the most important step—several orders of magnitude!
// Think about whether you can implement the dictionary in a more efficient manner. Recall that the alphabet consists of only the 26 letters A through Z.
// Exploit that fact that when you perform a prefix query operation, it is usually alomst identical to the previous prefix query, except that it is one letter longer.
// Precompute the Boggle graph, i.e., the set of cubes adjancent to each cube. But don't necessarily use a heavyweight Graph object.
// Consider a nonrecursive implemention of the prefix query operation.
// Consider a nonrecursive implemention of depth-first search.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BoggleSolver
{
    private BoggleDictionary boggleTrie;

    private class DictionaryNode {
        private char c; // character
        private DictionaryNode left, mid, right; // left, middle, and right subtries
        private String val; // value associated with string
    }

    private class BoggleDictionary {
        private DictionaryNode root;

        public BoggleDictionary() {
        }

        public void put(String s) {
            root = put(root, s, s, 0);
        }

        private DictionaryNode put(DictionaryNode x, String s, String val, int d) {
            char c = s.charAt(d);
            if (x == null) {
                x = new DictionaryNode();
                x.c = c;
            }
            if (c < x.c)
                x.left = put(x.left, s, val, d);
            else if (c > x.c)
                x.right = put(x.right, s, val, d);
            else if (d < s.length() - 1)
                x.mid = put(x.mid, s, val, d + 1);
            else
                x.val = val;
            return x;
        }

        private boolean contains(String key) {
            return get(key) != null;
        }

        private String get(String key) {
            if (key == null)
                throw new NullPointerException();
            if (key.length() == 0)
                throw new IllegalArgumentException("key must have length >= 1");
            DictionaryNode x = get(root, key, 0);
            if (x == null)
                return null;
            return x.val;
        }

        // return subtrie corresponding to given key
        private DictionaryNode get(DictionaryNode x, String key, int d) {
            if (x == null)
                return null;
            char c = key.charAt(d);
            if (c < x.c)
                return get(x.left, key, d);
            else if (c > x.c)
                return get(x.right, key, d);
            else if (d < key.length() - 1)
                return get(x.mid, key, d + 1);
            else
                return x;

            // Stack<DictionaryNode> stack = new Stack<DictionaryNode>();
            // stack.push(x);
            // while (!stack.isEmpty()) {
            //     char c = key.charAt(d);
            //     x = stack.pop();
            //     while (c < x.c) {
            //         if (x.left == null)
            //             return null;
            //         stack.push(x.left);
            //     }
            //     while (c > x.c) {
            //         if (x.right == null)
            //             return null;
            //         stack.push(x.right);
            //     }
            //     else if (d < key.length() - 1) {
            //         if (x.mid == null)
            //             return null;
            //         stack.push(x.mid);
            //         d++;
            //     }
            //     else
            //         return x;
            // }
            // return x;
        }
    }

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        boggleTrie = new BoggleDictionary();
        for (int val = 0; val < dictionary.length; val++)
            boggleTrie.put(dictionary[val]);
    }

    private class BoggleWord {
        private HashSet<String> words;
        private Hashtable<String, Character> bd;
        private Hashtable<String, Set<String>> surrounds;
        private int rows, cols;

        public BoggleWord(BoggleBoard board) {
            words = new HashSet<String>();
            bd = new Hashtable<String, Character>();
            surrounds = new Hashtable<String, Set<String>>();
            rows = board.rows();
            cols = board.cols();
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++) {
                    bd.put(getHash(i, j), board.getLetter(i, j));
                    surrounds.put(getHash(i, j), getSurrounds(i, j));
                }
        }

        private String getValue(char s) {
            if (s == 'Q')
                return "QU";
            else
                return Character.toString(s);
        }

        private String getHash(int x, int y) {
            return x + " " + y;
        }

        private int getX(String hashCode) {
            return Integer.parseInt(hashCode.split(" ")[0]);
        }

        private int getY(String hashCode) {
            return Integer.parseInt(hashCode.split(" ")[1]);
        }

        private String word(ArrayList<String> visited) {
            StringBuilder str = new StringBuilder();
            for (String s: visited)
                str.append(getValue(bd.get(s)));
            return str.toString();
        }

        public void buildWord(int x, int y) {
            ArrayList<String> visited = new ArrayList<String>();
            String start = getHash(x, y);
            String suffix = getValue(bd.get(start));
            DictionaryNode curNode = boggleTrie.get(boggleTrie.root, getValue(bd.get(start)), 0);
            if (curNode != null)
                dfs(curNode, start, start, visited);
        }

        private void dfs(DictionaryNode curNode, String start, String cur, ArrayList<String> visited) {

            visited.add(cur);
            String str = curNode.val;

            if (str != null && str.length() > 2 && !words.contains(str)) {
                words.add(str);
            }

            for (String next: surrounds.get(cur)) {
                if (!visited.contains(next)) {
                    String prefix = getValue(bd.get(next));
                    DictionaryNode nextNode = boggleTrie.get(curNode.mid, prefix, 0);
                    if (nextNode != null)
                        dfs(nextNode, start, next, visited);
                }
            }

            visited.remove(cur);
        }

        private Set<String> getSurrounds(int row, int col) {
            Set<String> surrounds = new HashSet<String>();
            for (int i = row - 1; i <= row + 1; i++)
                for (int j = col - 1; j <= col + 1; j++)
                    if (i >= 0 && i < rows && j >= 0 && j < cols && !(i == row && j == col)) {
                        surrounds.add(getHash(i, j));
                    }
            return surrounds;
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        BoggleWord bw = new BoggleWord(board);
        for (int i = 0; i < board.rows(); i++)
            for (int j = 0; j < board.cols(); j++) {
                bw.buildWord(i, j);
            }
        ArrayList<String> words = new ArrayList<String>(bw.words);
        Collections.sort(words);
        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (boggleTrie.get(word) != null) {
            int len  = word.length();
            switch(len) {
                case 1: case 2: return 0;
                case 3: case 4: return 1;
                case 5: return 2;
                case 6: return 3;
                case 7: return 5;
                default: return 11;
            }
        }
        return 0;
    }

    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        long start = System.nanoTime();
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        long elapsedTime = System.nanoTime();
        StdOut.println("Score = " + score);
        StdOut.println("Time = " + elapsedTime/1000000);
    }
}
