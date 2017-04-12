package main;

/**
 * @author Qiang
 * @since 30/03/2017
 */
public class DecoratorPattern {

    public static void main(String[] a){
        Coffee latteMilkSugarCoffee = new Sugar(new Milk(new LatteCoffee()));
        System.out.println(latteMilkSugarCoffee.getPrice());
    }


}

interface Coffee {
    int getPrice();
}

class MokaCoffee implements Coffee {

    @Override
    public int getPrice() {
        return 3;
    }
}

class LatteCoffee implements Coffee {

    @Override
    public int getPrice() {
        return 4;
    }
}

abstract class Decorator implements Coffee {

    Coffee coffee;

    public Decorator(Coffee coffee) {
        this.coffee = coffee;
    }

//    @Overr ide
//    publicabstract int getPrice();
}

class Sugar extends Decorator{

    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int getPrice() {
        return coffee.getPrice()+ 5;
    }
}

class Milk extends Decorator{

    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int getPrice() {
        return coffee.getPrice()+ 5;
    }
}