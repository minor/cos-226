import java.util.Arrays;
import java.util.Comparator;

public class Term implements Comparable<Term> {

    // Record the weight of a Term given in constructor
    private long weight;
    // Record the query String of a Term given in constructor
    private String query;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null)
            throw new IllegalArgumentException("query cannot be null");
        if (weight < 0)
            throw new IllegalArgumentException("weight can't be negative");
        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term t1, Term t2) {
            return Long.compare(t2.weight, t1.weight);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0)
            throw new IllegalArgumentException("r can't be negative");
        return new PrefixOrder(r);
    }

    private static class PrefixOrder implements Comparator<Term> {
        // Number of first characters (r) to compare given in constructor
        private int prefixLength;

        // Constructor that assigned the argument r to prefixLength
        public PrefixOrder(int r) {
            prefixLength = r;
        }

        public int compare(Term t1, Term t2) {
            for (int i = 0; i < prefixLength; i++) {
                // Make sure the loop doesn't exceed the query length
                if (i >= t1.query.length() && i >= t2.query.length()) {
                    return 0;
                }
                if (i >= t1.query.length())
                    return -1;
                if (i >= t2.query.length())
                    return 1;

                // Compare the characters
                if (t1.query.charAt(i) < t2.query.charAt(i))
                    return -1;
                if (t1.query.charAt(i) > t2.query.charAt(i))
                    return 1;
            }
            return 0;
        }
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // Private methos to help print out the whole array of Terms for main()
    private static void print(Term[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + "  " + arr[i]);
        }
        System.out.println();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] testArray = new Term[8];

        testArray[0] = new Term("bbcx", 9);
        testArray[1] = new Term("abfd", 10);
        testArray[2] = new Term("accz", 15);
        testArray[3] = new Term("abce", 8);
        testArray[4] = new Term("abcd", 18);
        testArray[5] = new Term("abcc", 7);
        testArray[6] = new Term("abcb", 24);
        testArray[7] = new Term("abdd", 2);

        print(testArray);
        Arrays.sort(testArray, Term.byPrefixOrder(2));
        print(testArray);
        Arrays.sort(testArray, Term.byPrefixOrder(3));
        print(testArray);
        Arrays.sort(testArray);
        print(testArray);
        Arrays.sort(testArray, Term.byReverseWeightOrder());
        print(testArray);
    }

}
