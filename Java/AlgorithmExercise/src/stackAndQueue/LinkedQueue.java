package stackAndQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class LinkedQueue<T> implements Queue<T> {

    private int size;
    private Node first;
    private Node last;

    @Override
    public Iterator<T> iterator() {
        //TODO
        return null;
    }

    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public LinkedQueue() {}


    @Override
    public void enqueue(T item) {
        if (last == null) {
            first = last = new Node(item);
        } else {
            last.next = new Node(item);
            last = last.next;
        }
        size++;
    }

    @Override
    public T dequeue() {

        if (size == 0) {
            throw new NoSuchElementException();
        }

        T item = first.item;
        first = first.next;
        size--;


        return item;
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        return first.item;
    }

    @Override
    public int count() {
        return size;
    }
}
