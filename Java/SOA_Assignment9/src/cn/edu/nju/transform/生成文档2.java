package cn.edu.nju.transform;

import cn.edu.nju.jw.schema.*;
import cn.edu.nju.schema.*;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.net.URL;
import java.util.Random;

/**
 * @author Qiang
 * @since 30/05/2017
 */
public class 生成文档2 {

    public static void main(String[] args) {

        学生列表类型 studentList = new 学生列表类型();

        学生信息 student1 ;

        课程成绩列表类型 courseType;

        个人信息类型 personInfo;

        String[] 姓名 = new String[] {
              "周聪", "崔浩" , "白国风" , "阮威威", "孙婧" , "袁阳阳", "赖斌" , "陈自强"
        };

        String[] 学号 = new String[] {
                "141250001","141250002","141250003","141250004","141250005","141250006","141250007","141250008"
        };

        性别类型[] 性别 = { 性别类型.女 , 性别类型.男, 性别类型.女 , 性别类型.男, 性别类型.女 , 性别类型.男, 性别类型.女 , 性别类型.男,} ;

        教育类型[] 教育背景 = {教育类型.本科 , 教育类型.本科 ,教育类型.本科 ,教育类型.本科 ,教育类型.本科 ,教育类型.本科 ,教育类型.本科 ,教育类型.本科 ,};

        婚姻类型[] 婚姻 = {婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, 婚姻类型.未婚, };

        String[] 手机号码 = {"18362910888" , "12345678901","18362910888" , "12345678901","18362910888" , "12345678901","18362910888" , "12345678901",};

        部门类型 部门 = new 部门类型();
        部门.set部门主管("陈琳");
        部门.set部门名称("软件学院");
        部门.set部门属性(部门属性类型.院);
        部门.set部门描述("专注于培养软件人才");
        部门.set部门编号("10125");

        地址类型 地址 = new 地址类型();
        地址.set区("鼓楼区");
        地址.set号("22号");
        地址.set城市("南京市");
        地址.set省份("江苏省");
        地址.set街道("汉口路");

        String[] 座机号码 = {"18362910888" , "12345678901","18362910888" , "12345678901","18362910888" , "12345678901","18362910888" , "12345678901",};

        String[] 课程编号 = {"450014" , "630017" , "210096" , "456123" , "123456" , "789456" , "794613" , "134679" , "134689"};

        成绩性质类型[] 成绩性质 = {成绩性质类型.平时成绩, 成绩性质类型.期末成绩, 成绩性质类型.总评成绩};
        Random random = new Random();
        for (int i = 0; i < 8; i++) {

            student1 = new 学生信息();
            personInfo = new 个人信息类型();
            personInfo.set座机号码(座机号码[i]);
            personInfo.set地址(地址);
            personInfo.set姓名(姓名[i]);
            personInfo.set婚姻状况(婚姻[i]);
            personInfo.set部门(部门);
            personInfo.set性别(性别[i]);
            personInfo.set教育背景(教育背景[i]);
            personInfo.set手机号码(手机号码[i]);


            student1.set基本信息(personInfo);
            student1.set学号(学号[i]);
            student1.set年级(年级类型.大二);


            courseType = new 课程成绩列表类型();
            for (int j = 0; j < 5; j++) {

                for (int k = 0; k < 3; k++) {
                    课程成绩类型 course = new 课程成绩类型();
                    course.set成绩性质(成绩性质[k]);
                    course.set课程编号(课程编号[j]);
                    成绩类型 score = new 成绩类型();
                    score.set学号(学号[i]);
                    score.set得分(random.nextInt(100) + 1);

                    course.get成绩().add(score);
                    courseType.get课程成绩().add(course);
                }


            }



            student1.set课程成绩列表(courseType);
            studentList.get学生().add(student1);



            outputToXML(学生列表类型.class , studentList, "files/文档2.xml");
        }




    }

    public static <T> void outputToXML(Class<T> entityClass, Object entity, String filePath) {
        String xmlStr = createXML(entityClass, entity);

        //System.out.println(xmlStr);
        //由java对象转成xml
        byte[] bytexml = xmlStr.getBytes();

        try {
            OutputStream os = new FileOutputStream(new File(filePath));
            os.write(bytexml);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public static <T> String createXML(Class<T> entityClass, Object entity) {
        StringWriter writer = new StringWriter();
        try {
            JAXBContext act = JAXBContext.newInstance(entityClass);
            Marshaller marshaller = act.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(entity, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
