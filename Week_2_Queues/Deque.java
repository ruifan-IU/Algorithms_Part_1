import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("cannot add null to the front");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        first.next = oldFirst;
        if (last == null) {
            last = first;
        }
        size = size + 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("cannot add null to the back");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        size = size + 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new java.util.NoSuchElementException(
                "cannot remove from the front of empty deque");
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        size = size - 1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new java.util.NoSuchElementException(
                "cannot remove from the back of empty deque");
        Item item = last.item;
        if (first == last) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        size = size - 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!this.hasNext()) throw new java.util.NoSuchElementException("no item to return");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        int size0 = deque.size();
        boolean isEmpty0 = deque.isEmpty();

        deque.addFirst(0);
        int size1 = deque.size();
        boolean isEmpty1 = deque.isEmpty();

        deque.addLast(1);
        int size2 = deque.size();

        int first = deque.removeFirst();
        int size3 = deque.size();

        deque.addLast(2);
        int last = deque.removeLast();

        int n = 2;
        while (n <= 10) {
            deque.addLast(n);
            n++;
        }

        int size10 = deque.size();

        StdOut.println(size0 == 0);
        StdOut.println(isEmpty0);
        StdOut.println(size1 == 1);
        StdOut.println(!isEmpty1);
        StdOut.println(size2 == 2);
        StdOut.println(first == 0);
        StdOut.println(size3 == 1);
        StdOut.println(last == 2);
        StdOut.println(size10 == 10);

        for (int i : deque) {
            StdOut.println(i);
        }
    }
}
