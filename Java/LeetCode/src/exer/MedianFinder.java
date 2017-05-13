package exer;

/**
 * #295
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
 * <p>
 * Examples:
 * [2,3,4] , the median is 3
 * <p>
 * [2,3], the median is (2 + 3) / 2 = 2.5
 * <p>
 * Design a data structure that supports the following two operations:
 * <p>
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findK() - Return the median of all elements so far.
 *
 * @author Qiang
 * @since 13/05/2017
 */
public class MedianFinder {

    private class Node {
        private int value;
        private int size;
        private Node left;
        private Node right;


        public Node(int value, int size) {
            this.value = value;
            this.size = size;
        }
    }

    private Node root;

    /**
     * initialize your data structure here.
     */
    public MedianFinder() {
    }

    public void addNum(int num) {
        root = addNum(num, root);
    }

    private Node addNum(int num, Node x) {
        if (x == null)
            return new Node(num, 1);
        else if (num < x.value)
            x.left = addNum(num, x.left);
        else
            x.right = addNum(num, x.right);

        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public double findMedian() {
        int k = size(root) / 2 + 1;

        if (size(root) % 2 == 0) {
            return (findK(root, k).value + (long) findK(root, k - 1).value) / 2.0;
        } else {
            return findK(root, k).value;
        }


    }


    private Node findK(Node x, int rank) {
        if (size(x.left) == rank - 1)
            return x;
        else if (size(x.left) < rank - 1)
            return findK(x.right, rank - size(x.left) - 1);
        else
            return findK(x.left, rank);
    }


    public static void main(String[] a) {

        MedianFinder finder = new MedianFinder();
        finder.addNum(1);
        System.out.println(finder.findMedian());
        finder.addNum(3);
        System.out.println(finder.findMedian());
        finder.addNum(2);
        System.out.println(finder.findMedian());
        finder.addNum(7);
        System.out.println(finder.findMedian());
        finder.addNum(4);finder.addNum(9);System.out.println(finder.findMedian());


    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findK();
 */
