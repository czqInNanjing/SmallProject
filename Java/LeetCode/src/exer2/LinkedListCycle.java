package exer2;

/**
 * 142. Linked List Cycle II
 * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
 * <p>
 * Note: Do not modify the linked list.
 *
 * @author Qiang
 * @since 17/05/2017
 */
public class LinkedListCycle {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;

        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        if (fast == null || fast.next == null) // no cycle
            return null;

        fast = head;
        slow = slow.next;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;

    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}