package cn.edu.nju.service.impl;

import cn.edu.nju.helper.FileProcesser;
import cn.edu.nju.service.FileSearchService;
import cn.edu.nju.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class FileSearchServiceImpl implements FileSearchService {

    private String filePath;
    private List<String> fileContents;
    private List<Line> fileLines;
    private Map<String, Integer> wordsTimes = new HashMap<>();
    public FileSearchServiceImpl() {

    }

    public void setFilePath(String filePath) {

        this.filePath = filePath;
        processFile();
        outPutMap();
    }

    private void outPutMap() {
        List<String> result = new ArrayList<>(wordsTimes.size());
        for (String key : wordsTimes.keySet()) {
            result.add(key  + " : "+ wordsTimes.get(key));
        }
        FileProcesser.outPut(result);

    }

    private void processFile() {
        fileContents = FileProcesser.getDocument(filePath);
//        fileContents.set(0, fileContents.get(0).substring(1));
        fileLines = new ArrayList<>(fileContents.size());
        for (int i = 0; i < fileContents.size(); i++) {
            String temp = fileContents.get(i).replaceAll("[,.\"\']", "").toLowerCase();
            fileLines.add(buildLine(temp.split(" "), i));
        }
    }

    private Line buildLine(String[] words, int lineNum) {
        for (String word: words) {
            word = SystemConstants.removeSuffix(word);
            if (!wordsTimes.containsKey(word)) {
                wordsTimes.put(word, 0);
            }
            wordsTimes.put(word, wordsTimes.get(word) + 1);
        }



        return new Line(lineNum , words );
    }

    public List<String> search(String keys) {
        // analyse command, to lower case

        boolean foundAndCommand = false;
        boolean foundOrCommand = false;
        boolean foundNotCommand = false;

        if (keys.contains(SystemConstants.AND_OPERATION))
            foundAndCommand = true;

        if (keys.contains(SystemConstants.OR_OPERATION))
            foundOrCommand = true;

        if (keys.contains(SystemConstants.NOT_OPERATION))
            foundNotCommand = true;



        keys = keys.replaceAll("[!&|()]", " ");
        String[] words = keys.split(" ");
        List<String> wordsWithoutSpace = new ArrayList<>();
        for (String word : words) {
            if (!word.equals(""))
                wordsWithoutSpace.add(word);
        }
        words = new String[wordsWithoutSpace.size()];
        for (int i = 0; i < words.length; i++) {
            words[i] = wordsWithoutSpace.get(i);
        }

        if (!foundAndCommand && !foundOrCommand && !foundNotCommand) {
            Command command = new NormalCommand(words[0]);
            return dealWithCommand(command);
        }


        if (foundAndCommand && !foundOrCommand && !foundNotCommand) {
            Command command = new AndCommand(words);
            return dealWithCommand(command);
        }

        if (foundNotCommand && !foundAndCommand && !foundOrCommand) {
            Command command = new NotCommand(words[0]);
            return dealWithCommand(command);
        }

        if (foundOrCommand && !foundAndCommand && !foundNotCommand) {
            Command command = new OrCommand(words);
            return dealWithCommand(command);
        }





        return null;
    }

    private List<String> dealWithCommand(Command command) {

        List<Line> lineResult = fileLines.stream().filter(command::isResult).collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        result.add(command.getWords() + " : " + lineResult.size() + " occurences.");

        for (int i = 0; i < lineResult.size(); i++) {
            result.add(i + ". (" + lineResult.get(i).getLineNum() + ") " + fileContents.get(lineResult.get(i).getLineNum()));
        }




        return result;
    }


    // For test
    public void showProcessResult() {

        for (Line line: fileLines) {
            System.out.println(line);
        }


    }


}
