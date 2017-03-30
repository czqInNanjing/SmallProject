package main;

/**
 * @author Qiang
 * @since 30/03/2017
 */
public class BridgePattern {

    public static void main(String[] a){
        Abstraction aa = new ConcreteSubClassA(new ConcreteImplementationA());
        Abstraction ab = new ConcreteSubClassA(new ConcreteImplementationB());
        Abstraction ba = new ConcreteSubClassB(new ConcreteImplementationA());
        Abstraction bb = new ConcreteSubClassB(new ConcreteImplementationB());

        aa.doOperation();
        ab.doOperation();
        ba.doOperation();
        bb.doOperation();
    }


}



abstract class Abstraction {
    protected Implementation implementation;

    Abstraction(Implementation implementation) {
        this.implementation = implementation;
    }

    public abstract void doOperation();
}

class ConcreteSubClassA extends Abstraction {


    public ConcreteSubClassA(Implementation implementation) {
        super(implementation);
    }

    @Override
    public void doOperation() {
        System.out.println("I will do the first aspect of thing in my way A");
        System.out.println("But how I do the implementation thing is depend on my field Implementation");
        implementation.operationThatICan();
    }


}

class ConcreteSubClassB extends Abstraction {


    public ConcreteSubClassB(Implementation implementation) {
        super(implementation);
    }

    @Override
    public void doOperation() {
        System.out.println("I will do the first aspect of thing in my way B");
        System.out.println("But how I do the implementation thing is depend on my field Implementation");
        implementation.operationThatICan();
    }


}


interface Implementation {

    void operationThatICan();


}

class ConcreteImplementationA implements Implementation {

    @Override
    public void operationThatICan() {
        System.out.println("I will do the implementation kind of thing in the way specfic of A");
    }
}
class ConcreteImplementationB implements Implementation {

    @Override
    public void operationThatICan() {
        System.out.println("I will do the implementation kind of thing in the way specfic of B");
    }
}