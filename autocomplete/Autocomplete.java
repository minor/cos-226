import edu.princeton.cs.algs4.Merge;

import java.util.Arrays;

public class Autocomplete {

    // instance variable for the terms
    private Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new IllegalArgumentException("input can't be null");

        for (Term term : terms) {
            if (term == null)
                throw new IllegalArgumentException("input term can't be null");
        }

        // Creating a copy of terms array to not modify the original
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++)
            this.terms[i] = terms[i];
        // sorting in "lexographic order"
        Merge.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("input can't be null");

        Term prefixTerm = new Term(prefix, 0);

        int first = BinarySearchDeluxe.firstIndexOf(terms, prefixTerm,
                                                    Term.byPrefixOrder(
                                                            prefix.length()));
        int last = BinarySearchDeluxe.lastIndexOf(terms, prefixTerm,
                                                  Term.byPrefixOrder(
                                                          prefix.length()));

        // if there is no instance of the prefix, return an empty array
        if (first == -1)
            return new Term[0];

        // create a copied subarray of the terms with the prefix
        Term[] matchingValues = new Term[last - first + 1];

        // copy over values from the other array
        for (int i = first; i <= last; i++)
            matchingValues[i - first] = terms[i];

        // sort by weight
        Arrays.sort(matchingValues, Term.byReverseWeightOrder());


        return matchingValues;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("input can't be null");

        Term prefixTerm = new Term(prefix, 0);

        int first = BinarySearchDeluxe.firstIndexOf(terms, prefixTerm,
                                                    Term.byPrefixOrder(
                                                            prefix.length()));
        int last = BinarySearchDeluxe.lastIndexOf(terms, prefixTerm,
                                                  Term.byPrefixOrder(
                                                          prefix.length()));
        if (first == -1)
            return 0;
        return last - first + 1;
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

        Autocomplete test = new Autocomplete(testArray);
        System.out.println("abc:" + test.numberOfMatches("abc"));
        print(test.allMatches("abc"));

        System.out.println("bbc:" + test.numberOfMatches("bbc"));
        print(test.allMatches("bbc"));

        System.out.println("zza:" + test.numberOfMatches("zza"));
        print(test.allMatches("zza"));


    }

}
