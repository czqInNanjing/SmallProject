package cn.edu.nju;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Qiang
 * @version  15/10/2016
 */
public class Constants {

    public enum TOKEN_TYPE  { KEY_WORD, INT, DOUBLE  , OPERATOR, DELIMITER, IDENTIFIER, INVALID_IDENTIFIER , INVALID_NUM , ANNOTATION , STRING_CONSTANTS}



    public static String[] KEY_WORD = {
            "abstract", "continue", "for", "new" , "switch" ,
            "assert" , "default" , "goto" , "package" , "synchronized",
            "boolean" , "do" , "if" , "private" , "this",
            "break" , "double" , "implements" , "protected" , "throw",
            "byte" , "else" , "import" , "public" , "throws",
            "case" , "enum" , "instanceof" , "return" , "transient",
            "catch" , "extends" , "int" , "short" , "try",
            "char" ,"final" , "interface" , "static" , "void",
            "class" , "finally" , "long" , "strictfp" , "volatile",
            "const" , "float" , "native" , "super", "super" , "while"
    };

    public static char[] DELIMITER = {
             '%','(',')','{','}','!',';',',','.','[','[','?',':','~','^','[',']'
    };

    public static String[] OPERATOR = {
            "+","+=","-","-=","*","*=","/","/=","=","==","&","|","&&","||","!","!="
            ,"<","<=",">",">=","++" , "--","|","&",
    };


    public static String[] ANNOTATION = {"//","/*","*/"};


}
