package cn.edu.nju.xlst;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Parser the given xml files and sort the course based on the score
 *
 * @author Qiang
 * @since 22/04/2017
 */
public class XLSTParser {

    /**
     * 使用XSLT转换XML文件
     *
     * @param srcXml 源XML文件路径
     * @param dstXml 目标XML文件路径
     * @param xslt   XSLT文件路径
     */
    public static void transformXmlByXslt(String srcXml, String dstXml, String xslt) {

        // 获取转换器工厂
        TransformerFactory tf = TransformerFactory.newInstance();

        try {
            // 获取转换器对象实例
            Transformer transformer = tf.newTransformer(new StreamSource(xslt));
            // 进行转换
            transformer.transform(new StreamSource(srcXml),
                    new StreamResult(new FileOutputStream(dstXml)));
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }


}

