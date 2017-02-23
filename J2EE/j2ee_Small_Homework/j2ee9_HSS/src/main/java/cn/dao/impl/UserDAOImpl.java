package cn.dao.impl;

import cn.dao.UserDAO;
import cn.entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Qiang
 * @since 03/01/2017
 */
@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public int userExists(String username, String password) {

        List<StudentEntity> scores = null;
        try {



            Session session=sessionFactory.openSession();
            Transaction tx=session.beginTransaction();

            String hql = "from " + StudentEntity.class.getSimpleName()+" WHERE name = '" + username +  "' AND password = '" + password + "'";
//            String sql = "select * from " + ScoreEntity.class.getSimpleName();

            scores = session.createQuery(hql).getResultList();




            tx.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (scores == null || scores.isEmpty()) {
            return  -1;
        } else {
            return scores.get(0).getId();
        }





    }
}
