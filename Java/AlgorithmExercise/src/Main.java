

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String str = scanner.next();
        String pattern = scanner.next();

        List<String> patternSplit = splitPattern(pattern);



        System.out.println(compare(str, patternSplit) ? 1 : 0);

    }

    private static boolean compare(String str, List<String> patternSplit) {

        int strSize = 0;


        for (int i = 0; i < patternSplit.size(); i++) {
            String tempPattern = patternSplit.get(i);
            if (tempPattern.equals("?")) {

                strSize++;
            } else if (tempPattern.equals("*")) {
                for (int j = strSize; j < str.length(); j++) {
                    if (compare(str.substring(j) , patternSplit.subList(i+1, patternSplit.size()))){
                        return true;
                    }
                }
                return false;
            } else {
                if (tempPattern.length() > (str.length() - strSize) || !tempPattern.equals(str.substring(strSize, strSize + tempPattern.length()))) {
                    return false;
                } else {
                    strSize += tempPattern.length();
                }
            }

        }

        if (strSize != str.length()) {
            return false;
        }


        return true;




    }

    private static List<String> splitPattern(String pattern) {

        char[] array = pattern.toCharArray();
        int[] specialPosition = new int[pattern.length()];
        int specialSize = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '*' || array[i] == '?' ) {

                specialPosition[specialSize++] = i;

            }

        }
        List<String> parts = new ArrayList<>(pattern.length());
        int start = 0;
        for (int i = 0; i < specialSize; i++) {
            if (specialPosition[i] - start != 0) {

                parts.add(pattern.substring(start, specialPosition[i]));

            }
            parts.add(pattern.substring(specialPosition[i] , specialPosition[i] + 1));
            start = specialPosition[i] + 1;

        }
        if (specialPosition[specialSize - 1] != pattern.length() - 1) {
            parts.add(pattern.substring(specialPosition[specialSize - 1] + 1));
        }


        return parts;


    }


}
