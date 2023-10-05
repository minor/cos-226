Programming Assignment 3: Autocomplete


/* *****************************************************************************
 *  Describe how your firstIndexOf() method in BinarySearchDeluxe.java
 *  finds the first index of a key that is equal to the search key.
 **************************************************************************** */

It is a regular binary search; however, instead of stopping when the key is
found, this method saves the location of the found key but continues searching
the left half of the array to try and find another instance of the key. The
methods will run until completion, so that after the loop, it has the last
found instance of the key saved (and since we're iteratively searching the left
half, will always be the first instance of the key). In other words, we're
finding the first match of the key, storing the index, and then continuing with
the binary search on the left half of the array to find if there are more
matches that are "before" the one we have already found. This continues until
the key is no longer found, and the first value of the key is stored and
returned.

/* *****************************************************************************
 *  Identify which sorting algorithm (if any) that your program uses in the
 *  Autocomplete constructor and instance methods. Choose from the following
 *  options:
 *
 *    none, selection sort, insertion sort, mergesort, quicksort, heapsort
 *
 *  If you are using an optimized implementation, such as Arrays.sort(),
 *  select the principal algorithm.
 **************************************************************************** */

Autocomplete() : mergesort

allMatches() : Arrays.sort() => mergesort (because it is a reference array)

numberOfMatches() : none

/* *****************************************************************************
 *  How many compares (in the worst case) does each of the operations in the
 *  Autocomplete data type make, as a function of both the number of terms n
 *  and the number of matching terms m? Use Big Theta notation to simplify
 *  your answers.
 *
 *  Recall that with Big Theta notation, you should discard both the
 *  leading coefficients and lower-order terms, e.g., Theta(m^2 + m log n).
 **************************************************************************** */

Autocomplete():     Theta( n log n )

allMatches():       Theta( n log n )

numberOfMatches():  Theta(  log n  )

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */

none

/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */

Soring with a comparator until we realized that Arrays.sort() is nlogn in the
worst case as opposed to n^2 when it is used for reference arrays.

/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on how much you learned from doing the assignment, and whether
 *  you enjoyed doing it.
 **************************************************************************** */

