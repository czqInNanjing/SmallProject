package stackAndQueue;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public interface Queue<T> extends Iterable<T>{


    void enqueue(T item);
    T dequeue();
    T peek();
    int count();

}
