package listeners;

import constants.Constants;
import constants.OnlineNumberCounter;

import javax.servlet.http.*;

import static constants.Constants.userIDStr;

/**
 * @author Qiang
 * @since 16/12/2016
 */

public class VisitorListener implements HttpSessionListener, HttpSessionAttributeListener {
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("Session Created");
        OnlineNumberCounter.getCounter().addVisitors();


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("Session Destroyed");
        if ( event.getSession().getAttribute(userIDStr) == null) {
            OnlineNumberCounter.getCounter().delVisitors();
        } else {
            OnlineNumberCounter.getCounter().delLoginNum();
        }

    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        if (event.getName().equals(Constants.userIDStr)) {
            System.out.println("student id added");
            OnlineNumberCounter.getCounter().visitorToLogin();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }
}
