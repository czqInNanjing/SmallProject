package service;


/**
 * @author Qiang
 * @since 06/01/2017
 */

public interface UserService {
    /**
     *
     * @param username
     * @param password
     * @return user id if user exists, or -1 if user does not exist
     */
    int userExists(String username, String password);

}
