package exer2;

import java.util.HashMap;
import java.util.Map;

/**
 * #146
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
 * <p>
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 * <p>
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * @author Qiang
 * @since 14/05/2017
 */
public class LRUCache {


    class DoubleLinkNode {
        int key;
        int value;
        DoubleLinkNode left, right;

        DoubleLinkNode(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<Integer, DoubleLinkNode> map = new HashMap<>();
    private int capacity;
    private int size;
    private DoubleLinkNode head;
    private DoubleLinkNode tail;


    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public int get(int key) {
        DoubleLinkNode value = map.get(key);
        if (value != null) {
            removeNode(value);
            addAtTheHead(value);

            return value.value;
        } else {
            return -1;
        }
    }

    private void addAtTheHead(DoubleLinkNode value) {
        if (head == null) {
            assert tail == null;
            head = value;
            tail = value;
        } else {
            value.right = head;
            value.left = null;
            head.left = value;
            head = value;
        }
    }

    private DoubleLinkNode removeTail() {
        if (tail == null) {
            return null;
        } else {
            DoubleLinkNode remove = tail;
            tail = tail.left;

            if (tail == null)
                head = null;
            else
                tail.right = null;
            return remove;
        }

    }

    private DoubleLinkNode removeNode(DoubleLinkNode value) {
        if (value.left != null && value.right != null) {
            value.right.left = value.left;
            value.left.right = value.right;
        } else if (value.left == null && value.right == null) {
            head = tail = null;
        } else if (value.left == null) {
            head = value.right;
            head.left = null;
        } else {
            tail = value.left;
            tail.right = null;
        }


        return value;
    }

    public void put(int key, int value) {
        DoubleLinkNode newNode = new DoubleLinkNode(key, value);
        if (map.get(key) == null) {
            // new node

            if (size == capacity) {
                DoubleLinkNode node = removeTail();
                if (node != null) {
                    map.put(node.key, null);
                }
                addAtTheHead(newNode);
            } else {
                size++;
                addAtTheHead(newNode);
            }

            map.put(key, newNode);


        } else {
            DoubleLinkNode oldNode = map.get(key);
            removeNode(oldNode);
            addAtTheHead(newNode);
            map.put(key, newNode);
        }
    }

    public static void main(String[] ar) {


        LRUCache obj = new LRUCache(10);
        obj.put(10, 13);
        obj.put(3, 17);
        obj.put(6, 11);
        obj.put(10, 5);
        obj.put(9, 10);

        System.out.println(obj.get(13));
        obj.put(2, 19);
        System.out.println(obj.get(2));
        System.out.println(obj.get(3));
        obj.put(5, 25);

        System.out.println(obj.get(8));
        obj.put(9, 22);
        obj.put(5, 5);
        obj.put(1, 30);
        System.out.println(obj.get(11));
        obj.put(9, 12);
        System.out.println(obj.get(7));
        System.out.println(obj.get(5));
        System.out.println(obj.get(8));
        System.out.println(obj.get(9));
        obj.put(4, 30);
        obj.put(9, 3);
        System.out.println(obj.get(9));
        System.out.println(obj.get(10));
        System.out.println(obj.get(10));

        obj.put(6, 14);
        obj.put(3, 1);
        System.out.println(obj.get(3));
        obj.put(10, 11);
        System.out.println(obj.get(8));
        obj.put(2, 14);

        System.out.println(obj.get(1));
        System.out.println(obj.get(5));
        System.out.println(obj.get(4));
        obj.put(11, 4);
        obj.put(12, 24);
        obj.put(5, 18);
        System.out.println(obj.get(13));

        obj.put(7, 23);
        System.out.println(obj.get(8));
        System.out.println(obj.get(12));
        obj.put(3, 27);
        obj.put(2, 12);
        System.out.println(obj.get(5));
        obj.put(2, 9);
        obj.put(13, 4);
        obj.put(8, 18);
        obj.put(1, 7);
        System.out.println(obj.get(6));
    }

}
