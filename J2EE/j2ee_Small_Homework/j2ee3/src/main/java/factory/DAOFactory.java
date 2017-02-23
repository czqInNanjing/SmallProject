package factory;

import dao.StudentScoreDAO;
import dao.UserDAO;
import dao.impl.StudentScoreDAOImpl;
import dao.impl.UserDAOImpl;

/**
 * @author Qiang
 * @since 21/12/2016
 */
public class DAOFactory {

    public static UserDAO getUserDAO() { return new UserDAOImpl();}

    public static StudentScoreDAO getStudentDAO() {return new StudentScoreDAOImpl();}

}
