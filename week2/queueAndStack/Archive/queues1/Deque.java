import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current.item == null) {
                throw new java.util.NoSuchElementException("the current item is null");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("not supported remove function");
        }
    }

    private Node first, last;
    private int size;

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

        if (item == null) {
            throw new IllegalArgumentException("null arguments");
        }

        if (isEmpty()) {

            first = new Node();
            first.item = item;
            last = first;

        } else {

            Node oldfirst = first;

            first = new Node();
            first.item = item;
            first.next = oldfirst;
            oldfirst.prev = first;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            throw new IllegalArgumentException("null arguments");
        }

        if (isEmpty()) {

            last = new Node();
            last.item = item;
            first = last;

        } else {

            Node oldLast = last;

            last = new Node();
            last.item = item;
            last.prev = oldLast;
            oldLast.next = last;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("empty list");
        }

        Item item = first.item;

        if (first.next != null) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }

        size--;

        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) {
            throw new java.util.NoSuchElementException("empty list");
        }

        Item item = last.item;

        if (last.prev != null) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }

        size--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item>  iterator() {
        return new ListIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> dq = new Deque<String>();
        dq.addLast("a");
        dq.addLast("b");
        dq.addLast("c");
        dq.addLast("d");
        dq.addFirst("1");

        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeLast());
        StdOut.println(dq.removeLast());
    }

}

