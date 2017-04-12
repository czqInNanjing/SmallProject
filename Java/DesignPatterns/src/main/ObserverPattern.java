package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Qiang
 * @since 30/03/2017
 */
public class ObserverPattern {





}

abstract class Observer {
    protected Observable observable;

    public abstract void update(Object message);

    public void setObservable(Observable observable) {
        this.observable = observable;
    }
}




abstract class Observable {

    private List<Observer> observerList;

    public Observable() {
        observerList = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observerList.add(observer);
        observer.setObservable(this);
    }

    public void removeObserver(Observer observer) {
        observerList.removeIf(observer1 -> observer1==observer);
        observer.setObservable(null);
    }



    public void notifyObservers() {
        for (Observer observer : observerList){
            observer.update("Some message");
        }
    }

}


