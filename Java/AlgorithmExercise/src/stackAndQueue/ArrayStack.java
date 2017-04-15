package stackAndQueue;

import java.util.EmptyStackException;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public class ArrayStack<T> implements Stack<T>{

    private T[] array;
    private int capacity;
    private int size;

    public ArrayStack() {
        this(10);
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
    }


    @Override
    public T peek() {
        if (size <= 0) {
            throw new EmptyStackException();
        }
        return array[size - 1];
    }

    @Override
    public T pop() {
        if (size <= 0) {
            throw new EmptyStackException();
        } else if (size <= capacity / 4) {
            resize(capacity >> 1);
        }
        T temp = array[--size];
        array[size] = null;
        return temp;
    }

    @Override
    public void push(T item) {
        if (size == capacity) {
            resize(capacity << 1);
        }

        array[size++] = item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int count() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, capacity);
        array = newArray;
        capacity = newCapacity;

    }
}
