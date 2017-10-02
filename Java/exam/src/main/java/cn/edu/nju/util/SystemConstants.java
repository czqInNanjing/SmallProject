package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class SystemConstants {

    public final static String[] spliter = {",", " ", "\'", "."};

    public final static String AND_OPERATION = "&&";
    public final static String OR_OPERATION = "||";
    public final static String NOT_OPERATION = "!";

    public final static String[] SUFFIX = {"es" , "s", "ed", "ing"};


    public static boolean  stringEquals(String str1, String str2) {
        str1 = removeSuffix(str1).toLowerCase();
        str2 = removeSuffix(str2).toLowerCase();
        return str1.equals(str2);
    }

    public static String removeSuffix (String str) {

        if (str.length() == 1) {
            return str;
        }

        for (String aSUFFIX : SUFFIX) {
            int suffixPosition = str.lastIndexOf(aSUFFIX);
            if (suffixPosition == str.length() - aSUFFIX.length() && suffixPosition != -1) {
                return str.substring(0, suffixPosition);
            }


        }

        return str;

    }

}
