package dao.impl;

import dao.UserDAO;
import dao.util.DataSourceHelper;
import entity.Score;

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
public class UserDAOImpl implements UserDAO {

    private DataSource dataSource = DataSourceHelper.getInstance();

    @Override
    public int userExists(String username, String password) {
        List<Score> scores = new ArrayList<>();
        try {
            String sql = "SELECT * FROM student WHERE name = ? AND password = ?";
            Connection conn = dataSource.getConnection();
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, username);
            prep.setString(2,password);
            ResultSet resultSet = prep.executeQuery();

            if (resultSet.next()){
                return resultSet.getInt(1);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
