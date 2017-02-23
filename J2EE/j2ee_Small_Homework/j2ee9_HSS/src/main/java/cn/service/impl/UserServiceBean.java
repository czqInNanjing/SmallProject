package cn.service.impl;

import cn.dao.UserDAO;
import cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Service
public class UserServiceBean implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceBean(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @Override
    public int userExists(String username, String password) {
        return userDAO.userExists(username, password);
    }
}
