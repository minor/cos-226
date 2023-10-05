import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a,
                                         Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("no arguments can be null");
        }

        int low = 0;
        int high = a.length - 1;
        int potentialValue = -1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                high = mid - 1;
            }
            else if (compare > 0) {
                low = mid + 1;
            }
            else {
                potentialValue = mid;
                high = mid - 1;
            }
        }
        return potentialValue;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null) {
            throw new IllegalArgumentException("either array or key cannot be null");
        }

        int low = 0;
        int high = a.length - 1;
        int potentialValue = -1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) {
                high = mid - 1;
            }
            else if (compare > 0) {
                low = mid + 1;
            }
            else {
                potentialValue = mid;
                low = mid + 1;
            }
        }
        return potentialValue;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // create an array of Strings
        Integer[] arr = { 1, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5, 5, 5, 5, 5, 6, 7, 8, 8, 9 };

        // key to search for
        int key = 5;

        // Create a comparator for Integer objects
        Comparator<Integer> comparator = Integer::compareTo;

        // call methods & store results
        int resultFirst = firstIndexOf(arr, key, comparator);
        int resultLast = lastIndexOf(arr, key, comparator);

        System.out.println("First index of " + key + " is at index: " + resultFirst);
        System.out.println("Last index of " + key + " is at index: " + resultLast);
    }

}
