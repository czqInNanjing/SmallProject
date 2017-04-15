package stackAndQueue;

import java.util.EmptyStackException;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class LinkedStack<T> implements Stack<T> {

    private int size;
    private Node first;

    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public LinkedStack() {

    }

    @Override
    public T peek() {
        if (size <= 0) {
            throw new EmptyStackException();
        }
        return first.item;
    }

    @Override
    public T pop() {
        if (size <= 0) {
            throw new EmptyStackException();
        }
        T item = first.item;
        first = first.next;
        size--;

        return item;
    }

    @Override
    public void push(T item) {
        Node newFirst = new Node(item);
        newFirst.next = first;
        first = newFirst;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int count() {
        return size;
    }
}
