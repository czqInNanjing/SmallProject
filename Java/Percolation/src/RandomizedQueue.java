import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Item[] items;
    private int capacity;


    public RandomizedQueue() {
        capacity = 10;
        items = (Item[]) new Object[capacity];
    }


    private class Itr implements Iterator<Item> {

        private int[] itemRef;
        private int cursor;

        public Itr() {
            itemRef = new int[size];

            for (int i = 0; i < size; i++) {
                itemRef[i] = i;
            }
            StdRandom.shuffle(itemRef);
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[itemRef[cursor++]];
        }


    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {

        if (item == null) {
            throw new NullPointerException();
        }


        if (size == capacity) {
            resize(capacity * 2);
        }
        items[size++] = item;


    }

    private void resize(int newCapcacity) {

        Item[] newItems = (Item[]) new Object[newCapcacity];
        int copySize = newCapcacity < capacity ? newCapcacity : capacity;
        for (int i = 0; i < copySize; i++) {
            newItems[i] = items[i];
        }
        capacity = newCapcacity;
        items = newItems;

    }

    public Item dequeue() {

        if (size <= 0) {
            throw new NoSuchElementException();
        }


        int nextToOut = StdRandom.uniform(size);
        Item removeItem = items[nextToOut];
        items[nextToOut] = items[size - 1];

        size--;
        if (size <= capacity / 4 && capacity >= 40) {
            resize(capacity / 2);
        }

        return removeItem;

    }

    public Item sample() {
        if (size <= 0) {
            throw new NoSuchElementException();
        }
        return items[StdRandom.uniform(size)];
    }

    @Override
    public Iterator<Item> iterator() {

        return new Itr();
    }

    public static void main(String[] args) {

    }


}
