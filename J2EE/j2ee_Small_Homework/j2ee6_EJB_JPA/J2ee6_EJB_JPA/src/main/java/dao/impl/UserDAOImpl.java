package dao.impl;

import dao.UserDAO;
import dao.util.DataSourceHelper;
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
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    protected EntityManager manager;

    @Override
    public int userExists(String username, String password) {
        Query query = manager.createNativeQuery("SELECT * FROM student WHERE name = ? AND password = ?", StudentEntity.class);
        query.setParameter(1, username);
        query.setParameter(2, password);
        Object result = query.getSingleResult();
        if (result != null) {
            StudentEntity studentEntity = (StudentEntity) result;
            return studentEntity.getId();
        }

//        try {
//            String sql = "SELECT * FROM student WHERE name = ? AND password = ?";
//            Connection conn = dataSource.getConnection();
//            PreparedStatement prep = conn.prepareStatement(sql);
//            prep.setString(1, username);
//            prep.setString(2,password);
//            ResultSet resultSet = prep.executeQuery();
//
//            if (resultSet.next()){
//                return resultSet.getInt(1);
//            }
//
//
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return -1;
    }
}
