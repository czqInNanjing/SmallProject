package cn.edu.nju.xlst;

import cn.edu.nju.enumType.Education;
import cn.edu.nju.enumType.Gender;
import cn.edu.nju.enumType.Marriage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class PersonInfo implements ElementService {
    private String name;
    private Gender gender;
    private Education education;
    private Marriage marriage;
    private String phone;
    private Department department;
    private Address address;
    private String telephone;

    public PersonInfo() {
    }

    ;

    public PersonInfo(String name, Gender gender, Education education, Marriage marriage, String phone, Department department, Address address, String telephone) {
        this.name = name;
        this.gender = gender;
        this.education = education;
        this.marriage = marriage;
        this.phone = phone;
        this.department = department;
        this.address = address;
        this.telephone = telephone;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Marriage getMarriage() {
        return marriage;
    }

    public void setMarriage(Marriage marriage) {
        this.marriage = marriage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public Element createElement(Document doc) {
        Element baseElement = doc.createElement("基本信息");
        baseElement.setAttribute("xmlns", "http://www.nju.edu.cn/schema");

        Element nameElement = doc.createElement("姓名");
        nameElement.appendChild(doc.createTextNode(name));
        baseElement.appendChild(nameElement);

        Element genderElement = doc.createElement("性别");
        genderElement.appendChild(doc.createTextNode(gender.toString()));
        baseElement.appendChild(genderElement);

        Element educationElement = doc.createElement("教育背景");
        educationElement.appendChild(doc.createTextNode(education.toString()));
        baseElement.appendChild(educationElement);

        Element marriageElement = doc.createElement("婚姻状况");
        marriageElement.appendChild(doc.createTextNode(marriage.toString()));
        baseElement.appendChild(marriageElement);

        Element phoneElement = doc.createElement("手机号码");
        phoneElement.appendChild(doc.createTextNode(phone));
        baseElement.appendChild(phoneElement);

        Element departmentElement = department.createElement(doc);
        baseElement.appendChild(departmentElement);

        Element addressElement = address.createElement(doc);
        baseElement.appendChild(addressElement);

        Element telephoneElement = doc.createElement("座机号码");
        telephoneElement.appendChild(doc.createTextNode(telephone));
        baseElement.appendChild(telephoneElement);

        return baseElement;
    }


}
