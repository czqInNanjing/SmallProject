package dao.impl;

import dao.UserDAO;

import entity.ScoreEntity;
import entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * @author Qiang
 * @since 03/01/2017
 */
public class UserDAOImpl implements UserDAO {

    private Configuration config;
    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public int userExists(String username, String password) {

        List<StudentEntity> scores = null;
        try {

            config = new Configuration().configure();
            config.addAnnotatedClass(StudentEntity.class);

            serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            sessionFactory=config.buildSessionFactory(serviceRegistry);
            session=sessionFactory.openSession();
            Transaction tx=session.beginTransaction();

            String hql = "from " + StudentEntity.class.getSimpleName()+" WHERE name = '" + username +  "' AND password = '" + password + "'";
//            String sql = "select * from " + ScoreEntity.class.getSimpleName();

            scores = session.createQuery(hql).getResultList();




            tx.commit();
            session.close();
            sessionFactory.close();
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
