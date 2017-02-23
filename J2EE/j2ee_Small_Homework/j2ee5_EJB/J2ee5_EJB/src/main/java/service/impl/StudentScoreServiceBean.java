package service.impl;

import dao.StudentScoreDAO;
import entity.Score;
import factory.EJBFactory;
import service.StudentScoreService;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Stateless
public class StudentScoreServiceBean implements StudentScoreService {

    private StudentScoreDAO dao = (StudentScoreDAO) EJBFactory
            .getEJB("java:jboss/exported/J2ee5_Web_war_exploded/StudentScoreDAOImpl!dao.StudentScoreDAO");

    @Override
    public List<Score> getStudentsScores(int studentId) {
        return dao.getStudentsScores(studentId);
    }
}
