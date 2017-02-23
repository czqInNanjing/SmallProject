package service.impl;

import dao.UserDAO;
import factory.EJBFactory;
import service.UserService;

import javax.ejb.Stateless;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Stateless
public class UserServiceBean implements UserService {


    private UserDAO userDAO = (UserDAO) EJBFactory
            .getEJB("java:jboss/exported/J2ee5_Web_war_exploded/UserDAOImpl!dao.UserDAO");


    @Override
    public int userExists(String username, String password) {
        return userDAO.userExists(username, password);
    }
}
