package helper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * Deal with the input and output of the files
 * @author Qiang
 * @version  15/10/2016
 */
public class FileIOHelper {
    public static char[] fileReader(String filePath) throws IOException {
        char[] fileBuffer = new char[100000];
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));

        int num = reader.read(fileBuffer);
        if(num == fileBuffer.length) {
            System.err.println("File too long");
        }
        reader.close();

        char[] fileContent = new char[num];
        System.arraycopy(fileBuffer, 0, fileContent, 0, num);


        return fileContent;
    }

    public static List<String> fileReaderToString(String filePath) throws IOException {
        List<String> contents = new ArrayList<>(1000);
        BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));

        String temp;
        while ( (temp = reader.readLine()) != null){
            contents.add(temp);
        }

        reader.close();
        return contents;

    }

    public static void outPutToFile(List<String> content, String filePath) throws IOException {

        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
            System.err.println("Warning : file " + filePath + "lost");
        }

        FileWriter writer = new FileWriter(file , false);
        writer.write(filePath + " 's output\n");
        for (String line : content) {
            System.out.println(line);
            writer.write(line);
            writer.append('\n');
        }
        writer.flush();
        writer.close();

    }


}
