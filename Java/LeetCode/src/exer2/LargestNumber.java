package exer2;

import java.util.ArrayList;
import java.util.List;

/**
 * #179. Largest Number
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * <p>
 * For example, given [3, 30, 34, 5, 9], the largest formed number is 9534330.
 * <p>
 * Note: The result may be very large, so you need to return a string instead of an integer.
 *
 * @author Qiang
 * @since 17/05/2017
 */
public class LargestNumber {
    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0)
            return "";

        List<List<String>> ints = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ints.add(new ArrayList<>());
        }

        for (int num : nums) {
            String temp = Integer.toString(num);
            ints.get(temp.charAt(0) - '0').add(temp);
        }

        StringBuilder result = new StringBuilder();

        for (int i = 9; i >= 0; i--) {
            List<String> list = ints.get(i);
            list.sort((o1, o2) -> {
                String s1 = o1 + o2;
                String s2 = o2 + o1;
                return s2.compareTo(s1);
            });
            for (int j = 0; j < list.size(); j++) {
                result.append(list.get(j));
            }
        }
        if (result.charAt(0) == '0')
            return "0";
        return result.toString();


    }


    public static void main(String[] a) {
        LargestNumber largestNumber = new LargestNumber();
        System.out.println(largestNumber.largestNumber(new int[]{9682,928,2354,8060}));


    }
}
