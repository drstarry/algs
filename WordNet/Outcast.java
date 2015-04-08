// Measuring the semantic relatedness of two nouns. Semantic relatedness refers to the degree to which two concepts are related. Measuring semantic relatedness is a challenging problem. For example, most of us agree that George Bush and John Kennedy (two U.S. presidents) are more related than are George Bush and chimpanzee (two primates). However, not most of us agree that George Bush and Eric Arthur Blair are related concepts. But if one is aware that George Bush and Eric Arthur Blair (aka George Orwell) are both communicators, then it becomes clear that the two concepts might be related.

// We define the semantic relatedness of two wordnet nouns A and B as follows:

// distance(A, B) = distance is the minimum length of any ancestral path between any synset v of A and any synset w of B.
// This is the notion of distance that you will use to implement the distance() and sap() methods in the WordNet data type.

// Outcast detection. Given a list of wordnet nouns A1, A2, ..., An, which noun is the least related to the others? To identify an outcast, compute the sum of the distances between each noun and every other one:

// di   =   dist(Ai, A1)   +   dist(Ai, A2)   +   ...   +   dist(Ai, An)
// and return a noun At for which dt is maximum.

public class Outcast {
   public Outcast(WordNet wordnet)         // constructor takes a WordNet object
   public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcast
   public static void main(String[] args)  // see test client below {

   }
}
