import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int size;
    private int head;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        size = 0;
        head = 0;
        tail = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int iCopy = 0;
        for (int i = head; i < tail; i++) {
            if (q[i] != null) {
                copy[iCopy] = q[i];
                iCopy++;
            }
        }
        q = copy;
        head = 0;
        tail = size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("cannot add null to queue");
        q[tail] = item;
        tail++;
        size++;
        if (tail == q.length) {
            resize(2 * size);
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new java.util.NoSuchElementException("cannot remove from empty queue");
        int randomIndex = StdRandom.uniformInt(head, tail);
        while (q[randomIndex] == null) {
            randomIndex = StdRandom.uniformInt(head, tail);
        }
        Item item = q[randomIndex];
        q[randomIndex] = null;
        size--;
        if (randomIndex == head) head++;
        if (randomIndex == tail) tail--;
        if (size > 0 && size == q.length / 4) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new java.util.NoSuchElementException("cannot return sample from empty queue");
        int randomIndex = StdRandom.uniformInt(head, tail);
        while (q[randomIndex] == null) {
            randomIndex = StdRandom.uniformInt(head, tail);
        }
        return q[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int[] indices;
        private int curIndex;

        public RQIterator() {
            indices = new int[size];
            curIndex = 0;

            int cur = 0;
            for (int i = head; i < tail; i++) {
                if (q[i] != null) {
                    indices[cur] = i;
                    cur++;
                }
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return curIndex < indices.length;
        }

        public Item next() {
            if (!this.hasNext())
                throw new java.util.NoSuchElementException("no more item to return");
            Item item = q[indices[curIndex]];
            curIndex++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove method not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        System.out.println(queue.size());
        System.out.println(queue.isEmpty());

        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        System.out.println(queue.size());
        System.out.println(queue.sample());

        System.out.println(queue.dequeue());

        System.out.println(queue.size());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());

        Iterator<Integer> first = queue.iterator();
        Iterator<Integer> second = queue.iterator();
        while (first.hasNext()) {
            int n = first.next();
            StdOut.print(n);
        }
        StdOut.print("\n");
        while (second.hasNext()) {
            int n = second.next();
            StdOut.print(n);
        }
    }
}
