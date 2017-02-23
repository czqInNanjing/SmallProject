package dao.impl;

import dao.StudentScoreDAO;
import dao.util.DataSourceHelper;
import entity.ScoreEntity;
import entity.StudentEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @since 03/01/2017
 */
@Stateless
public class StudentScoreDAOImpl implements StudentScoreDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Override
    public List<ScoreEntity> getStudentsScores(int studentId) {
        List<ScoreEntity> scores = new ArrayList<>();
        Query query = manager.createNativeQuery("SELECT * FROM score WHERE studentID = ?", ScoreEntity.class);
        query.setParameter(1, studentId);
        return query.getResultList();


//        List<Score> scores = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM score WHERE studentID = ?";
//            Connection conn = dataSource.getConnection();
//            PreparedStatement prep = conn.prepareStatement(sql);
//            prep.setInt(1, studentId);
//            ResultSet resultSet = prep.executeQuery();
//            Score score;
//            while (resultSet.next()){
//                int courseId = resultSet.getInt(2);
//                int examScore = resultSet.getInt(3);
//
//                scores.add(new Score(studentId, courseId , examScore));
//
//            }
//
//
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return scores;
    }
}
