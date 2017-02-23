package service.impl;

import dao.StudentScoreDAO;
import entity.ScoreEntity;
import factory.DAOFactory;
import service.StudentScoreService;

import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */

public class StudentScoreServiceBean implements StudentScoreService {

    private StudentScoreDAO dao = DAOFactory.getStudentDAO();

    @Override
    public List<ScoreEntity> getStudentsScores(int studentId) {
        return dao.getStudentsScores(studentId);
    }
}
