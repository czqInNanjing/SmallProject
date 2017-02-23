package service.impl;

import dao.UserDAO;
import factory.DAOFactory;
import service.UserService;

/**
 * @author Qiang
 * @since 06/01/2017
 */

public class UserServiceBean implements UserService {


    private UserDAO userDAO = DAOFactory.getUserDAO();


    @Override
    public int userExists(String username, String password) {
        return userDAO.userExists(username, password);
    }
}
