package cn.edu.nju;

import com.sun.tools.classfile.ConstantPool;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexical Analyzer Core
 * @author Qiang
 * @date 15/10/2016
 */

public class Lex {

     private char[] inputSeq;

     private List<Token> tokens = new ArrayList<Token>(3000);

     private int offset = 0;

     private int fileSize;

     public Lex (char[] inputSeq) {
          this.inputSeq = inputSeq;
          fileSize = inputSeq.length;

     }






     public List<Token> output() {
          process();
          return tokens;
     }

     private void process() {
          char tempC;
          StringBuffer buffer = new StringBuffer(10000);
          boolean isLineAnnotation = false;
          boolean isMultilineAnnotation = false;
          while (offset < fileSize) {
               buffer.setLength(0);
               tempC = inputSeq[offset];
               buffer.append(tempC);

               if (isLineAnnotation){
                    offset++;
                    if (tempC == '\n'){
                         isLineAnnotation = false;
                    }
                    continue;
               } else if (isMultilineAnnotation) {
                    if (tempC == '*' && inputSeq[offset + 1] =='/'){
                         addToken("*/" , Constants.TOKEN_TYPE.ANNOTATION);
                         isMultilineAnnotation = false;
                         offset += 2;
                    } else {
                         offset++;
                    }
                    continue;
               }

               if (tempC == '\\'){
                    offset += 2;
                    addToken("\\" , Constants.TOKEN_TYPE.DELIMITER);
                    continue;
               } else if (Character.isLetter(tempC)) { // start with a letter, check if keyword or identifier

                    while ( ++offset < fileSize) { // avoid surplus the file length limit
                         char c = inputSeq[offset];
                         if(Character.isLetterOrDigit(c) || c == '_'){
                              buffer.append(c);
                         } else if (isEmptyCode(c)) {
                              if(checkIfKeyWord(buffer.toString())){
                                   addToken(buffer.toString(), Constants.TOKEN_TYPE.KEY_WORD);
                                   break;
                              } else {
                                   addToken(buffer.toString(), Constants.TOKEN_TYPE.IDENTIFIER);
                                   break;
                              }


                         } else if (checkIfDelimiter(c) || checkIfOperator(c + "")) {
                              addToken(buffer.toString(), Constants.TOKEN_TYPE.IDENTIFIER);
                              offset--;                                                       // give back the char
                              break;
                         } else { // appear unexpected char
                              buffer.append(c);
                              getAllInvalidSeqs(buffer);
                              addToken(buffer.toString() , Constants.TOKEN_TYPE.INVALID_IDENTIFIER);
                              break;
                         }





                    }



               } else if (Character.isDigit(tempC)){ // if is a digit, check if a valid value or a invalid type
                    boolean isDouble = false;
                    while (++offset < fileSize) {
                         char c = inputSeq[offset];

                         if (Character.isDigit(c)) {
                              buffer.append(c);
                         } else if (c == '.') {
                              if(isDouble){
                                   buffer.append(c);
                                   getAllInvalidSeqs(buffer);
                                   addToken(buffer.toString() , Constants.TOKEN_TYPE.INVALID_NUM);
                                   break;
                              }else {
                                   isDouble = true;
                                   buffer.append(c);
                              }


                         } else if (checkIfDelimiter(c)) {
                              if (isDouble) {
                                   addToken(buffer.toString(), Constants.TOKEN_TYPE.DOUBLE);
                              } else {
                                   addToken(buffer.toString(), Constants.TOKEN_TYPE.INT);
                              }

                              offset--;                                                       // give back the char
                              break;
                         } else {
                              buffer.append(c);
                              getAllInvalidSeqs(buffer);
                              addToken(buffer.toString() , Constants.TOKEN_TYPE.INVALID_NUM);
                              break;
                         }





                    }



               } else if (tempC == '/'){
                    if (inputSeq[offset + 1] == '*') {
                         isMultilineAnnotation = true;
                         addToken("/*" , Constants.TOKEN_TYPE.ANNOTATION);
                         offset += 2;
                         continue;
                    } else if (inputSeq[offset + 1] == '/') {
                         isLineAnnotation = true;
                         addToken("//" , Constants.TOKEN_TYPE.ANNOTATION);
                         offset += 2;
                         continue;
                    } else if (++offset < fileSize) {
                         char c = inputSeq[offset];
                         if (isEmptyCode(c) || Character.isLetterOrDigit(c)){
                              addToken("/" , Constants.TOKEN_TYPE.OPERATOR);
                              if (Character.isLetterOrDigit(c)) {
                                   offset--;
                              }
                         }else {
                              buffer.append('/');
                              buffer.append(c);
                              getAllInvalidSeqs(buffer);
                              addToken(buffer.toString() , Constants.TOKEN_TYPE.INVALID_IDENTIFIER);


                         }


                    } else {
                         addToken("/" , Constants.TOKEN_TYPE.OPERATOR);
                    }



               } else if (tempC == '-' || tempC == '+' || tempC == '*' || tempC == '=' || tempC =='|' || tempC == '&' || tempC =='<' || tempC == '>'){

                    buffer.append(inputSeq[offset + 1]);
                    if (checkIfOperator(buffer.toString())){
                         addToken(buffer.toString() , Constants.TOKEN_TYPE.OPERATOR);
                         offset++;
                    } else if (++offset < fileSize) {
                         buffer.setLength(0);

                         char c = inputSeq[offset];
                         if (isEmptyCode(c) || Character.isLetterOrDigit(c)){
                              addToken(tempC + "" , Constants.TOKEN_TYPE.OPERATOR);
                              if (Character.isLetterOrDigit(c)) {
                                   offset--;
                              }
                         }else {
                              buffer.append('/');
                              buffer.append(c);
                              getAllInvalidSeqs(buffer);
                              addToken(buffer.toString() , Constants.TOKEN_TYPE.INVALID_IDENTIFIER);


                         }


                    } else {
                         addToken(tempC + "", Constants.TOKEN_TYPE.OPERATOR);
                    }


               } else if (checkIfDelimiter(tempC)) {
                    addToken(buffer.toString() , Constants.TOKEN_TYPE.DELIMITER);
               } else if (tempC == '\'' || tempC == '"') {
                    addToken(tempC + "", Constants.TOKEN_TYPE.DELIMITER);
                    buffer.setLength(0);
                    while ( ++offset < fileSize) { // avoid surplus the file length limit

                         char c = inputSeq[offset];

                         if ( c == '\\') {
                              buffer.append(c);
                              buffer.append(inputSeq[offset + 1]);
                              offset++;
                         } else if ( c != tempC) {
                              buffer.append(c);
                         } else {
                              addToken(buffer.toString() , Constants.TOKEN_TYPE.STRING_CONSTANTS);
                              addToken(c + "", Constants.TOKEN_TYPE.DELIMITER);
                              break;
                         }





                    }

               } else if (isEmptyCode(tempC)){

               } else {
                    System.err.println("Not deal with " +tempC);
               }




               offset ++;
          }


     }

     private boolean checkIfID(char[] unit) {
          return false;
     }


     private char[] getNextUnit() {
          return null;
     }

     private boolean checkIfKeyWord(String seqs) {


          for (String temp : Constants.KEY_WORD) {
               if (seqs.equals(temp)){
                    return true;
               }
          }
          return false;



     }

     private void addToken(String seqs, Constants.TOKEN_TYPE type) {
          tokens.add(new Token(type, seqs));
//          System.out.println(tokens.get(tokens.size() - 1).toString());
     }

     private boolean isEmptyCode(char code){
          return code == ' ' || code == '\n' || code == '\t';
     }

     private void getAllInvalidSeqs(StringBuffer buffer){
          while (++offset < fileSize && !isEmptyCode(inputSeq[offset])){ // deal with the invalid token until reach an empty code
               buffer.append(inputSeq[offset]);
          }
     }

     private boolean checkIfDelimiter(char c) {
          for (char temp : Constants.DELIMITER) {
               if (temp == c){
                    return true;
               }
          }
          return false;
     }

     private boolean checkIfOperator (String str){
          for (String temp : Constants.OPERATOR){
               if (str.equals(temp)){
                    return true;
               }
          }
          return false;
     }
}
