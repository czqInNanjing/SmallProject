package cn.edu.nju.xlst;

import cn.edu.nju.enumType.DepartmentType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Department implements ElementService {
    private DepartmentType type;
    private String number;
    private String name;
    private String manager;
    private String description;

    public Department() {
    }

    ;

    public Department(DepartmentType type, String number, String name, String manager, String description) {
        this.type = type;
        this.number = number;
        this.name = name;
        this.manager = manager;
        this.description = description;
    }

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Element createElement(Document doc) {
        Element departmentElement = doc.createElement("部门");
        departmentElement.setAttribute("部门属性", type.toString());
        departmentElement.setAttribute("部门编号", number);
        departmentElement.setAttribute("部门名称", name);
        Element managerElement = doc.createElement("部门主管");
        managerElement.appendChild(doc.createTextNode(manager));
        Element descriptionElement = doc.createElement("部门描述");
        descriptionElement.appendChild(doc.createTextNode(description));
        departmentElement.appendChild(managerElement);
        departmentElement.appendChild(descriptionElement);
        return departmentElement;
    }


}
