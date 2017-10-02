package cn.edu.nju.service;

import cn.edu.nju.service.impl.FileSearchServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class FileSearchServiceTest {

    FileSearchServiceImpl fileSearchService;

    @Before
    public void setUp() throws Exception {
        fileSearchService = new FileSearchServiceImpl();
    }

    @Test
    public void setFilePath() throws Exception {

        fileSearchService.setFilePath("short/myTest.txt");
        fileSearchService.showProcessResult();

    }

    @Test
    public void search() throws Exception {

        fileSearchService.setFilePath("long/text.txt");
//        System.out.println(fileSearchService.search("teach"));
//        System.out.println(fileSearchService.search("I && am"));
//        System.out.println(fileSearchService.search("father || mother"));
//        System.out.println(fileSearchService.search("!my"));



    }

}