package cn.dao.impl;

import cn.dao.StudentScoreDAO;
import cn.entity.ScoreEntity;
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
public class StudentScoreDAOImpl implements StudentScoreDAO {


    protected SessionFactory sessionFactory;

    @Autowired
    public StudentScoreDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<ScoreEntity> getStudentsScores(int studentId) {

        List<ScoreEntity> scores = null;
        try {


            Session session=sessionFactory.openSession();
            Transaction tx=session.beginTransaction();

            String hql = "from " + ScoreEntity.class.getName()+" where  studentId = '"+ studentId + "'";
            scores = session.createQuery(hql).list();



            tx.commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (scores == null || scores.isEmpty()) {
            return  null;
        } else {
            return scores;
        }





    }




}
