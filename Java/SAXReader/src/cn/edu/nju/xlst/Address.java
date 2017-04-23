package cn.edu.nju.xlst;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class Address implements ElementService {
    private String province;
    private String city;
    private String area;
    private String road;
    private String no;

    public Address() {
    }

    ;

    public Address(String province, String city, String area, String road, String no) {
        this.province = province;
        this.city = city;
        this.area = area;
        this.road = road;
        this.no = no;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    @Override
    public Element createElement(Document doc) {
        Element addressElement = doc.createElement("地址");
        Element proviceElement = doc.createElement("省份");
        proviceElement.appendChild(doc.createTextNode(province));
        Element cityElement = doc.createElement("城市");
        cityElement.appendChild(doc.createTextNode(city));
        Element areaElement = doc.createElement("区");
        areaElement.appendChild(doc.createTextNode(area));
        Element roadElement = doc.createElement("街道");
        roadElement.appendChild(doc.createTextNode(road));
        Element noElement = doc.createElement("号");
        noElement.appendChild(doc.createTextNode(no));

        addressElement.appendChild(proviceElement);
        addressElement.appendChild(cityElement);
        addressElement.appendChild(areaElement);
        addressElement.appendChild(roadElement);
        addressElement.appendChild(noElement);

        return addressElement;
    }


}
