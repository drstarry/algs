// Corner cases.  All methods and the constructor should throw a java.lang.NullPointerException if any argument is null. The constructor should throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG. The distance() and sap() methods should throw a java.lang.IllegalArgumentException unless both of the noun arguments are WordNet nouns.
// Performance requirements.  Your data type should use space linear in the input size (size of synsets and hypernyms files). The constructor should take time linearithmic (or better) in the input size. The method isNoun() should run in time logarithmic (or better) in the number of nouns. The methods distance() and sap() should run in time linear in the size of the WordNet digraph. For the analysis, assume that the number of nouns per synset is bounded by a constant.

public class WordNet {

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms)

   // returns all WordNet nouns
   public Iterable<String> nouns() {

   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {

   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {

   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {

   }

   // do unit testing of this class
   public static void main(String[] args) {

   }
}
