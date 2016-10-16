package cn.edu.nju;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {



        Lex lex2 = new Lex(FileIOHelper.fileReader("complicatedInput.txt"));

        FileIOHelper.outputToken(lex2.output() , "complicatedOutput.txt");


        Lex lex = new Lex(FileIOHelper.fileReader("simpleInput.txt"));

        FileIOHelper.outputToken(lex.output() , "simpleOutput.txt");

    }
}
