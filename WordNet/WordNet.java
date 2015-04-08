// Corner cases.  All methods and the constructor should throw a java.lang.NullPointerException if any argument is null. The constructor should throw a java.lang.IllegalArgumentException if the input does not correspond to a rooted DAG. The distance() and sap() methods should throw a java.lang.IllegalArgumentException unless both of the noun arguments are WordNet nouns.
// Performance requirements.  Your data type should use space linear in the input size (size of synsets and hypernyms files). The constructor should take time linearithmic (or better) in the input size. The method isNoun() should run in time logarithmic (or better) in the number of nouns. The methods distance() and sap() should run in time linear in the size of the WordNet digraph. For the analysis, assume that the number of nouns per synset is bounded by a constant.
// We define the semantic relatedness of two wordnet nouns A and B as follows:
// distance(A, B) = distance is the minimum length of any ancestral path between any synset v of A and any synset w of B.
// This is the notion of distance that you will use to implement the distance() and sap() methods in the WordNet data type.
// Outcast detection. Given a list of wordnet nouns A1, A2, ..., An, which noun is the least related to the others? To identify an outcast, compute the sum of the distances between each noun and every other one:
// di   =   dist(Ai, A1)   +   dist(Ai, A2)   +   ...   +   dist(Ai, An)
// and return a noun At for which dt is maximum.

// List of noun synsets. The file synsets.txt lists all the (noun) synsets in WordNet. The first field is the synset id (an integer), the second field is the synonym set (or synset), and the third field is its dictionary definition (or gloss). For example, the line
// 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire
// means that the synset { AND_circuit, AND_gate } has an id number of 36 and it's gloss is a circuit in a computer that fires only when all of its inputs fire. The individual nouns that comprise a synset are separated by spaces (and a synset element is not permitted to contain a space). The S synset ids are numbered 0 through S − 1; the id numbers will appear consecutively in the synset file.

// List of hypernyms. The file hypernyms.txt contains the hypernym relationships: The first field is a synset id; subsequent fields are the id numbers of the synset's hypernyms. For example, the following line
// 164,21012,56099
// means that the the synset 164 ("Actifed") has two hypernyms: 21012 ("antihistamine") and 56099 ("nasal_decongestant"), representing that Actifed is both an antihistamine and a nasal decongestant. The synsets are obtained from the corresponding lines in the file synsets.txt.

public class WordNet {

   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {

   }

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
