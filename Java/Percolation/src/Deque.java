import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class Deque<Item> implements Iterable<Item> {

    private int size;
    private Node first;
    private Node last;


    private class Node {
        private Item item;
        private Node next;
        private Node former;

        public Node(Item item) {
            this.item = item;
        }
    }

    private class Itr implements Iterator<Item> {
        private Node cursor = first;


        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item temp = cursor.item;
            cursor = cursor.next;

            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }
    }

    public Deque() {
        // do nothing
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }


        if (first == null) {
            first = new Node(item);
            last = first;
        } else {
            Node newFirst = new Node(item);
            newFirst.next = first;
            first.former = newFirst;
            first = newFirst;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (first == null) {
            first = new Node(item);
            last = first;
        } else {
            Node newLast = new Node(item);
            newLast.former = last;
            last.next = newLast;
            last = newLast;
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item firstItem = first.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.former = null;
        }


        size--;
        return firstItem;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item lastItem = last.item;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            last = last.former;
            last.next = null;
        }

        size--;
        return lastItem;

    }

    @Override
    public Iterator<Item> iterator() {
        return new Itr();
    }

    public static void main(String[] args) {

    }
}
