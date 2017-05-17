package exer2;

/**
 * #287. Find the Duplicate Number
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 * <p>
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 *
 * @author Qiang
 * @since 17/05/2017
 */
public class FindDuplicateNum {
    //TODO we can get better result, this is O(n^2)
    public int findDuplicate(int[] nums) {

        for (int i = 1 ; i < nums.length ; i++ ) {
            boolean found = false;
            for(int j = 0 ; j < nums.length ; j++ ) {
                if(nums[j] == i) {
                    if(found)
                        return i;
                    else
                        found = true;
                }
            }
        }
        return 0;
    }

}

