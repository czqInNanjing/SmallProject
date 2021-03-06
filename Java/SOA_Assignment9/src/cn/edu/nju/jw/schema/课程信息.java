//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.30 at 02:55:55 PM CST 
//


package cn.edu.nju.jw.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for 课程信息 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="课程信息">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="课程编号" type="{http://jw.nju.edu.cn/schema}课程编号类型"/>
 *         &lt;element name="课程名称" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="任课教师" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="学分" type="{http://jw.nju.edu.cn/schema}学分类型"/>
 *         &lt;element name="选课人数" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="课程描述" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="参考教材" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="开始时间" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="结束时间" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "\u8bfe\u7a0b\u4fe1\u606f", propOrder = {

})
public class 课程信息 {

    @XmlElement(required = true)
    protected String 课程编号;
    @XmlElement(required = true)
    protected String 课程名称;
    @XmlElement(required = true)
    protected String 任课教师;
    @XmlSchemaType(name = "integer")
    protected int 学分;
    @XmlElement(required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger 选课人数;
    @XmlElement(required = true)
    protected String 课程描述;
    @XmlElement(required = true)
    protected String 参考教材;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar 开始时间;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar 结束时间;

    /**
     * Gets the value of the 课程编号 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String get课程编号() {
        return 课程编号;
    }

    /**
     * Sets the value of the 课程编号 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void set课程编号(String value) {
        this.课程编号 = value;
    }

    /**
     * Gets the value of the 课程名称 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String get课程名称() {
        return 课程名称;
    }

    /**
     * Sets the value of the 课程名称 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void set课程名称(String value) {
        this.课程名称 = value;
    }

    /**
     * Gets the value of the 任课教师 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String get任课教师() {
        return 任课教师;
    }

    /**
     * Sets the value of the 任课教师 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void set任课教师(String value) {
        this.任课教师 = value;
    }

    /**
     * Gets the value of the 学分 property.
     * 
     */
    public int get学分() {
        return 学分;
    }

    /**
     * Sets the value of the 学分 property.
     * 
     */
    public void set学分(int value) {
        this.学分 = value;
    }

    /**
     * Gets the value of the 选课人数 property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger get选课人数() {
        return 选课人数;
    }

    /**
     * Sets the value of the 选课人数 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void set选课人数(BigInteger value) {
        this.选课人数 = value;
    }

    /**
     * Gets the value of the 课程描述 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String get课程描述() {
        return 课程描述;
    }

    /**
     * Sets the value of the 课程描述 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void set课程描述(String value) {
        this.课程描述 = value;
    }

    /**
     * Gets the value of the 参考教材 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String get参考教材() {
        return 参考教材;
    }

    /**
     * Sets the value of the 参考教材 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void set参考教材(String value) {
        this.参考教材 = value;
    }

    /**
     * Gets the value of the 开始时间 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar get开始时间() {
        return 开始时间;
    }

    /**
     * Sets the value of the 开始时间 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void set开始时间(XMLGregorianCalendar value) {
        this.开始时间 = value;
    }

    /**
     * Gets the value of the 结束时间 property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar get结束时间() {
        return 结束时间;
    }

    /**
     * Sets the value of the 结束时间 property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void set结束时间(XMLGregorianCalendar value) {
        this.结束时间 = value;
    }

}
