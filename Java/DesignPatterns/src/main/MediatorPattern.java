package main;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @since 30/03/2017
 */

//TODO Can use name as id  to send to other person rather than call other directly
public class MediatorPattern {


    public static void main(String[] args) {

        Mediator mediator = new ConcreteMediator();
        Collaborator collaboratorA = new ConcreteCollaborator(mediator, "CollaboratorA");
        Collaborator collaboratorB = new ConcreteCollaborator(mediator, "CollaboratorB");
        collaboratorA.sendMessageTo(collaboratorB, "Hello");


    }







}

interface Mediator {


    void register(Collaborator collaborator);
    void sendMessage(Collaborator source, Collaborator destination, String message);

}

class ConcreteMediator implements Mediator{

    private List<Collaborator> customers;

    public ConcreteMediator() {
        this.customers = new ArrayList<>();
    }

    @Override
    public void register(Collaborator collaborator) {
          customers.add(collaborator);

    }


    @Override
    public void sendMessage(Collaborator source, Collaborator destination, String message) {
        destination.receiveMessageFrom(source, message);
    }
}



abstract class Collaborator {
    protected String name;
    protected Mediator mediator;
    public Collaborator(Mediator mediator, String name) {
        this.mediator = mediator;
        mediator.register(this);
        this.name = name;
    }

    protected abstract void sendMessageTo(Collaborator destination, String message);
    protected abstract void receiveMessageFrom(Collaborator source, String message);

    @Override
    public String toString() {
        return name;
    }
}

class ConcreteCollaborator extends Collaborator {


    public ConcreteCollaborator(Mediator mediator, String name) {
        super(mediator, name);
    }

    @Override
    protected void sendMessageTo(Collaborator destination, String message) {
        mediator.sendMessage(this, destination , message);
    }

    @Override
    protected void receiveMessageFrom(Collaborator source, String message) {
        System.out.println("Hello, I am "+ toString() + ", I receive message from " + source + ", saying that " + message);
    }
}



