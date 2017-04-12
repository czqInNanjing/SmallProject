package architectureExercise;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @since 31/03/2017
 */
public class Ex21 {


}



abstract class Student {
    protected String name;
    protected String id;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }
}

class Undergraduate extends Student{

    public Undergraduate(String name, String id) {
        super(name, id);
    }
}


class Graduate extends Student {

    public Graduate(String name, String id) {
        super(name, id);
    }
}


interface EnrollStrategy {

    void enroll(Student student);

}


interface TestStrategy {
    void testStudent(Student student);
}

class Course {
    private EnrollStrategy enrollStrategy;
    private TestStrategy testStrategy;

    private int limitedSize;
    private int enrollSize;
    private List<Student> students;


    public Course(EnrollStrategy enrollStrategy, TestStrategy testStrategy, int limitedSize) {
        this.enrollStrategy = enrollStrategy;
        this.testStrategy = testStrategy;

        enrollSize = 0;
        this.limitedSize = limitedSize;
        students = new ArrayList<>(this.limitedSize);


    }

    public void enroll(Student student) {
        enrollStrategy.enroll(student);
    }

    public void test(Student student) {
        testStrategy.testStudent(student);
    }

    public void setLimitedSize(int size) {
        this.limitedSize = size;
    }


}
