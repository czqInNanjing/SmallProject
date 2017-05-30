package cn.edu.nju.transform;

import cn.edu.nju.jw.schema.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.*;

/**
 * @author Qiang
 * @since 30/05/2017
 */
public class 生成文档3 {

    public static void main(String[] args) throws Exception {

        学生列表类型 studentList = readFromXML(学生列表类型.class , "files/文档2.xml");

        if (studentList == null)
            throw new Exception("Error while reading 学生列表类型 from 文档2.xml");


        课程成绩列表类型 scoreList = new 课程成绩列表类型();

        for (学生信息 student : studentList.get学生()) {
            scoreList.get课程成绩().addAll(student.get课程成绩列表().get课程成绩());
        }

        List<课程成绩类型> scores =  scoreList.get课程成绩();

        Map<String, List<课程成绩类型>> 课程到课程成绩map = new TreeMap<>();

        for(课程成绩类型 score : scores) {
            课程到课程成绩map.computeIfAbsent(score.get课程编号(), k -> new ArrayList<>());
            课程到课程成绩map.get(score.get课程编号()).add(score);
        }


        List<课程成绩类型> finalResult = new ArrayList<>();

        for (Map.Entry<String, List<课程成绩类型>> entry: 课程到课程成绩map.entrySet()) {

            entry.getValue().sort( (c1, c2) -> {
                if(c1.get成绩性质() == c2.get成绩性质()) {
                    return c1.get成绩().get(0).get得分() > c2.get成绩().get(0).get得分() ? 1 : -1;
                } else {
                    return get成绩性质对应大小(c1.get成绩性质()) < get成绩性质对应大小(c2.get成绩性质()) ? 1 : -1;

                }




            });
            finalResult.addAll(entry.getValue());
        }



        scoreList.get课程成绩().clear();

        scoreList.get课程成绩().addAll(finalResult);


        生成文档2.outputToXML(课程成绩列表类型.class, scoreList, "/Users/czq/Desktop/SOA_Assignment9/files/文档3.xml");


    }

    private static int get成绩性质对应大小(成绩性质类型 type) {
        switch (type) {
            case 总评成绩: return -5;
            case 期末成绩: return -4;
            case 期中成绩: return -3;
            case 作业成绩: return -2;
            case 平时成绩: return -1;
        }


        return 0;

    }


    public static <T> T readFromXML(Class<T> entityClass,String filePath) {

        StringBuilder xmlStr = new StringBuilder();
         T stu = null;
        //由xml转成java对象
        try {
            FileInputStream is = new FileInputStream(new File(filePath));
            byte[] bytes = new byte[10000];

            int temp;
            while ((temp = is.read(bytes)) != -1) {
                xmlStr.append(new String(bytes, 0, temp));
            }
            stu = (T) createObject(entityClass, xmlStr.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return stu;


    }


    public static <T> Object createObject(Class<T> entityClass, String entity) {
        Object object = null;
        try {
            JAXBContext act = JAXBContext.newInstance(entityClass);
            Unmarshaller unMarshaller = act.createUnmarshaller();

            object = unMarshaller.unmarshal(new StringReader(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
