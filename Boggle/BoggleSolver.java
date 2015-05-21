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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.ArrayList;

public class BoggleSolver
{
    private TST<Integer> dictTrie;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dictTrie = new TST<Integer>();
        for (int val = 0; val < dictionary.length; val++)
            dictTrie.put(dictionary[val], val);
    }

    private class BoggleWord {
        private Hashtable<String, Set<String>> words;
        private Hashtable<String, Character> bd;
        private int rows, cols;

        public BoggleWord(BoggleBoard board) {
            words = new Hashtable<String, Set<String>>();
            bd = new Hashtable<String, Character>();
            rows = board.rows();
            cols = board.cols();
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    bd.put(getHash(i, j), board.getLetter(i, j));
        }

        private String getValue(char s) {
            if (s == 'Q')
                return "QU";
            else
                return Character.toString(s);
        }

        public Set<String> getWords(int x, int y) {
            return words.get(getHash(x,y));
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
            words.put(start, new HashSet<String>());
            dfs(start, start, visited);
        }

        private void dfs(String start, String cur, ArrayList<String> visited) {
            visited.add(cur);
            String str = word(visited);
            if (!dictTrie.isPrefix(str)) {
                return;
            }

            if (str.length() > 2 && dictTrie.get(str)!=null && !words.get(start).contains(str)) {
                words.get(start).add(str);
            }
            for (String next: getSurrounds(cur)) {
                if (!visited.contains(next))
                    dfs(start, next, new ArrayList<String>(visited));
            }
        }

        private Set<String> getSurrounds(String hashCode) {
            int _i = getX(hashCode);
            int _j = getY(hashCode);
            Set<String> surrounds = new HashSet<String>();
            for (int i = _i - 1; i <= _i + 1; i++)
                for (int j = _j - 1; j <= _j + 1; j++)
                    if (i >= 0 && i < rows && j >= 0 && j < cols && !(i == _i && j == _j)) {
                        surrounds.add(getHash(i, j));
                    }
            return surrounds;
        }

    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<String>();
        BoggleWord bw = new BoggleWord(board);
        for (int i = 0; i < board.rows(); i++)
            for (int j = 0; j < board.cols(); j++) {
                bw.buildWord(i, j);
                words.addAll(bw.getWords(i, j));
            }
        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (dictTrie.get(word) != null) {
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
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
