package cn.edu.nju;

import java.io.IOException;
import java.util.List;

/**
 * @author Qiang
 * @version  01/11/2016
 */
public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("================================================  Test Case 1  ========================================================");
            Analyzer analyzer = new Analyzer(IOHelper.fileReaderToString("src/cn/edu/nju/ParseTable.txt"), IOHelper.fileReaderToString("src/cn/edu/nju/ContextFreeGrammar.txt"));
            List<Reduction> reductions = analyzer.parseInput(IOHelper.fileReaderToCharArray("src/cn/edu/nju/input.txt"));
            for (Reduction reduction : reductions) {
                System.out.println(reduction);
            }

            System.out.println("================================================  Test Case 2  ========================================================");
            analyzer = new Analyzer(IOHelper.fileReaderToString("src/cn/edu/nju/ppt2.txt"), IOHelper.fileReaderToString("src/cn/edu/nju/CFG2.txt"));
            reductions = analyzer.parseInput(IOHelper.fileReaderToCharArray("src/cn/edu/nju/input2.txt"));
            for (Reduction reduction : reductions) {
                System.out.println(reduction);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
