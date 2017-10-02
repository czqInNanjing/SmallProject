package cn.edu.nju.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Qiang
 * @since 10/07/2017
 */
public class FileProcesser {

    public static List<String> getDocument(String filePath) {
        List<String> document = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
            String temp;
            while (true) {
                if ((temp = reader.readLine()) != null) {
                    document.add(temp);
                }else{
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return document;
    }//#close the method

    public static void outPut(List<String> file) {
        String path = "output/output.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
            for (String fileLine : file) {
                writer.write(fileLine);
                writer.newLine();
            }
            writer.flush();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
