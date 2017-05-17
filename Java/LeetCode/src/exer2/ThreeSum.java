package exer2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * #15. 3Sum
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.
 * <p>
 * Note: The solution set must not contain duplicate triplets.
 * <p>
 * For example, given array S = [-1, 0, 1, 2, -1, -4],
 * <p>
 * A solution set is:
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * @author Qiang
 * @since 17/05/2017
 */
public class ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {

        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

            int j = i + 1;
            int k = nums.length - 1;

            while (j < k) {
                int tempSum = nums[i] + nums[j] + nums[k];
                if (tempSum == 0) {
                    result.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                    while (nums[j] == nums[j - 1] && j < k) j++;
                    while (nums[k] == nums[k + 1] && j < k) k--;
                } else if (tempSum > 0) {
                    k--;
                } else {
                    j++;
                }
            }


        }


        return result;


    }

}
