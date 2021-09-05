import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null arguments");
        }

        autoIncreaseSize();

        s[N++] = item;
    }

    private void autoIncreaseSize() {
        if (N == s.length) {
            resize(2 * s.length);
        }
    }

    private void resize(int capability) {
        if (capability < N) {
            throw new java.lang.IllegalArgumentException("the capability is too small");
        }
        Item[] newArray = (Item[]) new Object[capability];

        for (int i = 0; i < N; i++) {
            newArray[i] = s[i];
        }

        s = newArray;
    }


    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("the queue is empty");
        }

        int num = ramdomNum();
        Item item = s[num];
        s[num] = s[N - 1];
        s[N - 1] = null;
        N--;

        autoDecreseSize();
        return item;
    }

    private void autoDecreseSize() {
        if (N == s.length / 4) {
            resize(s.length / 2);
        }
    }


    private int ramdomNum() {
        return StdRandom.uniform(0, N); // from 0 to N-1
    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("the queue is empty");
        }

        int num = ramdomNum();
        return s[num];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueInterator();
    }

    public static void main(String[] args) {
        RandomizedQueue<String> que = new RandomizedQueue<String>();
        que.enqueue("a");
        que.enqueue("b");
        que.enqueue("c");
        que.enqueue("d");
        que.enqueue("e");

        for (int i = 0; i < 5; i++) {
            StdOut.println(que.dequeue());
        }
    }


    private class RandomizedQueueInterator implements Iterator<Item> {
        private Item[] it;
        private int i;

        public RandomizedQueueInterator() {

            it = (Item[]) new Object[N];

            for (int i = 0; i < N; i++) {
                it[i] = s[i];
            }

            //shuffle the queue
            StdRandom.shuffle(it);
        }


        public boolean hasNext() {
            return i < N;
        }

        public Item next() {

            if (!hasNext()) {
                throw new java.util.NoSuchElementException("the queue is empty");
            }

            return it[i++];

        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("not supported remove function");
        }


    }

}
