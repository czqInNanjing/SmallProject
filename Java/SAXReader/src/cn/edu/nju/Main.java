package cn.edu.nju;

import cn.edu.nju.xlst.MySAXParser;

import static cn.edu.nju.xlst.XLSTParser.transformXmlByXslt;

/**
 * @author Qiang
 * @since 22/04/2017
 */
public class Main {

    public static void main(String[] args) {

        XLSTransform();

        SAXReader();

    }

    private static void XLSTransform() {
        String srcXml = "src/cn.edu.nju.xlst/文档2.xml";
        String dstXml = "src/cn.edu.nju.xlst/文档3.xml";
        String xslt = "src/cn.edu.nju.xlst/OrderXSLT.xsl";

        transformXmlByXslt(srcXml, dstXml, xslt);


    }

    private static void SAXReader() {
        MySAXParser parser = new MySAXParser("src/cn.edu.nju.xlst/文档3.xml");
        parser.createByDom(parser.getAllPassGrades());
    }

}
