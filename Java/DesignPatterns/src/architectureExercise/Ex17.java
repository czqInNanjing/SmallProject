package architectureExercise;

/**
 * @author Qiang
 * @since 30/03/2017
 */
public class Ex17 {



}


interface PersonInterface {
    double getIncome();
}

class Person implements PersonInterface {
    private double salary;


    @Override
    public double getIncome() {
        return salary;
    }
}


abstract class Decorator implements PersonInterface{
    protected PersonInterface component;

    public Decorator(PersonInterface component) {
        this.component = component;
    }
}


class AddByNum extends Decorator{
    private int number;
    private double priceEach;

    public AddByNum(PersonInterface component, int number, double priceEach) {
        super(component);
        this.number = number;
        this.priceEach = priceEach;
    }

    @Override
    public double getIncome() {
        return component.getIncome() + number*priceEach;
    }
}

class extraIncome extends Decorator {
    private String type;
    private double value;


    public extraIncome(PersonInterface component, String type, double value) {
        super(component);
        this.type = type;
        this.value = value;
    }

    @Override
    public double getIncome() {
        return component.getIncome() + value;
    }
}