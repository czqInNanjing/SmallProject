package exer;

/**
 * Given a positive integer n and you can do operations as follow:
 * <p>
 * If n is even, replace n with n/2.
 * If n is odd, you can replace n with either n + 1 or n - 1.
 * What is the minimum number of replacements needed for n to become 1?
 *
 * @author Qiang
 * @since 13/05/2017
 */
public class IntegerReplacement {
    public static int integerReplacement(int n) {
        if (n == 1)
            return 0;

        if (n % 2 == 0) {
            int timesOf2 = 1;
            n = n / 2;
            while (n % 2 == 0) {
                timesOf2++;
                n = n / 2;
            }
            return timesOf2 + integerReplacement(n);
        } else {
            if (n == Integer.MAX_VALUE)
                return 32;
            else
                return 1 + Math.min(integerReplacement(n - 1), integerReplacement(n + 1));
        }

    }
    public static void main(String[] a) {

        System.out.println(integerReplacement(1));

        System.out.println(integerReplacement(2));
        System.out.println(integerReplacement(7));
        System.out.println(integerReplacement(2147483647));
    }
}
