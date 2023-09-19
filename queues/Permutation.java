import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        // define k from user input
        int k = Integer.parseInt(args[0]);

        // create a randomized queue
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // while not empty
        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }

        // print out information through dequeue
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
