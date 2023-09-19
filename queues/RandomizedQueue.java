import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // define instance variables
    private Item[] queue; // create array of type Item for the randomized queue
    private int size = 0; // variable for the array size

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[2]; // "ugly" type-casting
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("The item is"
                                                                     + "null");
        if (size == queue.length) {
            resize(2 * queue.length);
        }
        queue[size++] = item;
    }

    // resize function
    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity]; // "ugly" type-casting
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp; // make queue equal new "temp" array
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("");
        }
        int randomIndex = StdRandom.uniformInt(size);
        Item removedItem = queue[randomIndex];
        queue[randomIndex] = queue[--size];
        queue[size] = null;

        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }

        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue[StdRandom.uniformInt(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int curr; // the current index
        private int[] randomItems; // array of items that are randomized

        // creating the randomized iterator
        public RandomizedIterator() {
            randomItems = new int[size];
            for (int i = 0; i < size; i++) {
                randomItems[i] = i;
            }
            StdRandom.shuffle(randomItems);
            curr = 0;
        }

        public boolean hasNext() {
            return curr != randomItems.length;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue[randomItems[curr++]];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // test isEmpty() and size()
        System.out.println("Size of Randomized Queue: " + queue.size());
        System.out.println("Queue Empty Status: " + queue.isEmpty());

        // test enqueue
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("A");

        // test sample/dequeue

        System.out.println("Sample: " + queue.sample());
        System.out.println("Dequeue: " + queue.dequeue());

        // test randomized iterator
        System.out.println("Randomized order: ");
        for (String item : queue) {
            System.out.println(item);
        }

        // re-test isEmpty() and size() for differences
        System.out.println("Size of Randomized Queue: " + queue.size());
        System.out.println("Queue Empty Status: " + queue.isEmpty());
    }

}
