package dao.impl;

import dao.StudentScoreDAO;
import dao.util.DataSourceHelper;
import entity.Score;

import javax.ejb.Stateless;
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

    private DataSource dataSource = DataSourceHelper.getInstance();

    @Override
    public List<Score> getStudentsScores(int studentId) {
        List<Score> scores = new ArrayList<>();
        try {
            String sql = "SELECT * FROM score WHERE studentID = ?";
            Connection conn = dataSource.getConnection();
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, studentId);
            ResultSet resultSet = prep.executeQuery();
            Score score;
            while (resultSet.next()){
                int courseId = resultSet.getInt(2);
                int examScore = resultSet.getInt(3);

                scores.add(new Score(studentId, courseId , examScore));

            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scores;
    }
}
