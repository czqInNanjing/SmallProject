package stackAndQueue;

/**
 * @author Qiang
 * @since 15/04/2017
 */
public interface Stack<T> {


    T peek();
    T pop();
    void push(T item);
    boolean isEmpty();
    int count();

}
