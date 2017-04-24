package cn.edu.nju.xlst;

import cn.edu.nju.enumType.ScoreType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiang
 * @since 22/04/2017
 */
public class MySAXParser extends DefaultHandler {

    String tempCourseId;
    ScoreType tempType;
    boolean insideStudentId;
    String tempStudentId;
    boolean insideStudentScore;
    int tempScore;
    private Map<String, List<CourseGrade>> courseScore = new HashMap<>(100);

    public MySAXParser(String filePath) {
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(filePath), this);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MySAXParser parser = new MySAXParser("src/cn.edu.nju.xlst/文档3.xml");
        parser.createByDom(parser.getAllPassGrades());
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equals("课程成绩")) {
            tempCourseId = attributes.getValue("课程编号");
            tempType = ScoreType.valueOf(attributes.getValue("成绩性质"));
        } else if (qName.equals("学号")) {
            insideStudentId = true;
        } else if (qName.equals("得分")) {
            insideStudentScore = true;
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "学号":
                insideStudentId = false;
                break;
            case "得分":
                insideStudentScore = false;
                break;
            case "课程成绩":
                CourseGrade courseGrade = new CourseGrade(tempCourseId, tempType, tempStudentId, tempScore);
                if (!courseScore.containsKey(tempCourseId)) {
                    courseScore.put(tempCourseId, new ArrayList<>(5));
                }
                courseScore.get(tempCourseId).add(courseGrade);
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        char[] temp = new char[length];

        if (insideStudentId) {
            System.arraycopy(ch, start, temp, 0, length);
            tempStudentId = new String(temp);
        } else if (insideStudentScore) {
            System.arraycopy(ch, start, temp, 0, length);
            tempScore = Integer.parseInt(new String(temp));
        }
    }

    public CourseGrade[] getAllPassGrades() {
        List<CourseGrade> courseGrades = new ArrayList<>(courseScore.size() * 3);

        for (Map.Entry<String, List<CourseGrade>> courseGrade : courseScore.entrySet()) {
            boolean hasNotPassed = false;

            for (CourseGrade tempCourseGrade : courseGrade.getValue()) {
                if (tempCourseGrade.getScore() < 60) {
                    hasNotPassed = true;
                    break;
                }
            }
            if (hasNotPassed) {
                courseGrades.addAll(courseGrade.getValue());
            }


        }
        CourseGrade[] result = new CourseGrade[courseGrades.size()];
        return courseGrades.toArray(result);
    }

    /**
     * 根据传入的学生列表生成xml文件
     *
     * @param courseGrades
     */
    public void createByDom(CourseGrade[] courseGrades) {
        Document doc;
        Element courseListElement;
        try {
            //得到DOM解析器的工厂实例
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //从DOM工厂中获得DOM解析器
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            //创建文档树模型对象
            doc = dbBuilder.newDocument();

            if (doc != null) {
                courseListElement = doc.createElement("课程成绩列表");
                for (int i = 0; i < courseGrades.length; i++) {
                    Element studentElement = courseGrades[i].createElement(doc);
                    courseListElement.appendChild(studentElement);
                }

                doc.appendChild(courseListElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("src/cn/edu/nju/xlst/文档4.xml"));
                transformer.transform(source, result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
