package cn.edu.nju.transform;

import cn.edu.nju.jw.schema.课程成绩列表类型;
import cn.edu.nju.jw.schema.课程成绩类型;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Qiang
 * @since 30/05/2017
 */
public class 生成文档4 {

    public static void main(String[] args) throws Exception {

        课程成绩列表类型 scoreList = 生成文档3.readFromXML(课程成绩列表类型.class, "files/文档3.xml");

        List<课程成绩类型> temp = scoreList.get课程成绩().stream().filter(type -> type.get成绩().get(0).get得分() < 60).collect(Collectors.toList());

        scoreList.get课程成绩().clear();
        scoreList.get课程成绩().addAll(temp);


        生成文档2.outputToXML(课程成绩列表类型.class, scoreList, "files/文档4.xml");


    }


}
