import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // define instance variables
    private Node first = null; // variable for first node in linked list
    private Node last = null; // variable for last node in linked list
    private int size; // size of the linked list

    private class Node {
        // define variables in node
        private Item item; // node for current item
        private Node next; // node for next node that it's linked to
        private Node prev; // node for previous node that it's linked to
    }

    // construct an empty deque
    public Deque() {
        // initialize all values to null and 0
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
        // return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Can't add null argument");
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = first;
        newNode.prev = null;

        if (isEmpty())
            last = newNode;
        else
            first.prev = newNode;
        first = newNode;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Can't add null argument");
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = null;
        newNode.prev = last;
        if (isEmpty())
            first = newNode;
        else
            last.next = newNode;
        last = newNode;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("Nothing to remove. The deque"
                                                     + "is empty.");
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("Nothing to remove. The deque"
                                                     + "is empty.");
        Item item = last.item;
        last = last.prev;
        size--;
        if (last != null)
            last.next = null;
        else
            first = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // create a LinkedIterator class that implements Iterator
    private class LinkedIterator implements Iterator<Item> {
        private Node curr = first;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (hasNext()) {
                Item item = curr.item;
                curr = curr.next;
                return item;
            }
            else {
                throw new NoSuchElementException("No next element.");
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        Deque<Object> newDeque = new Deque<>();

        // test isEmpty() and size()
        System.out.println("Size of Deque: " + newDeque.size());
        System.out.println("Deque Empty Status: " + newDeque.isEmpty());

        // test addLast, addFirst, removeLast, and removeFirst
        newDeque.addLast("a");
        newDeque.addLast("u");
        newDeque.addLast("r");
        newDeque.addLast("i");
        newDeque.addLast("s");
        newDeque.addFirst("s");
        newDeque.addFirst("c");
        newDeque.removeFirst();
        newDeque.addLast("6");
        newDeque.removeLast();
        newDeque.addLast("h"); // prints out "saurish" (my name)

        // test iterator
        for (Object i : newDeque) {
            System.out.println(i);
        }

        // re-test isEmpty() and size() for differences
        System.out.println("Size of Deque: " + newDeque.size());
        System.out.println("Deque Empty Status: " + newDeque.isEmpty());
    }

}
