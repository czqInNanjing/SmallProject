package exer2;

/**
 * #41. First Missing Positive
 * Given an unsorted integer array, find the first missing positive integer.
 * <p>
 * For example,
 * Given [1,2,0] return 3,
 * and [3,4,-1,1] return 2.
 * <p>
 * Your algorithm should run in O(n) time and uses constant space.
 *
 * @author Qiang
 * @since 15/05/2017
 */
public class FirstMissingPositive {

    public int firstMissingPositive(int[] nums) {

        if (nums == null)
            return 1;

        for (int i = 0; i < nums.length; ) {
            if (nums[i] > 0 && nums[i] <= nums.length && nums[i] != i + 1 && nums[nums[i] - 1] != nums[i]) { // the last condition is to avoid same num problem
                swap(nums, i, nums[i] - 1);
            } else {
                i++;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1)
                return i + 1;
        }

        return nums.length + 1;


    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] a = {3, 4, 3, 1};
        int[] b = {1, 2, 0};
        int[] c = {};
        int[] d = {1};
        int[] e = {0};
        int[] f = {1, 2, 3};

        FirstMissingPositive te = new FirstMissingPositive();
        System.out.println(te.firstMissingPositive(a));
        System.out.println(te.firstMissingPositive(b));
        System.out.println(te.firstMissingPositive(c));
        System.out.println(te.firstMissingPositive(d));
        System.out.println(te.firstMissingPositive(e));
        System.out.println(te.firstMissingPositive(f));


    }
}
