package dao.impl;

import dao.StudentScoreDAO;
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
public class StudentScoreDAOImpl implements StudentScoreDAO {

    private Configuration config;
    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;
    private Session session;





    @Override
    public List<ScoreEntity> getStudentsScores(int studentId) {

        List<ScoreEntity> scores = null;
        try {

            config = new Configuration().configure();
            config.addAnnotatedClass(ScoreEntity.class);
            serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            sessionFactory=config.buildSessionFactory(serviceRegistry);
            session=sessionFactory.openSession();
            Transaction tx=session.beginTransaction();

            String hql = "from " + ScoreEntity.class.getName()+" where  studentId = '"+ studentId + "'";
            scores = session.createQuery(hql).list();



            tx.commit();
            session.close();
            sessionFactory.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        if (scores == null || scores.isEmpty()) {
            return  null;
        } else {
            return scores;
        }





    }



//    public void updateByUserid(User user)
//    {
//        try {
//
//            config = new Configuration().configure();
//            serviceRegistry =new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
//            sessionFactory=config.buildSessionFactory(serviceRegistry);
//            session=sessionFactory.openSession();
//            Transaction tx=session.beginTransaction();
//            session.update(user); //ÈÝÆ÷¾ö¶¨flushÊ±£¬Êý¾Ý½«Í¬²½µ½Êý¾Ý¿âÖÐ
//            tx.commit();
//            session.close();
//            sessionFactory.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//		/*	Connection con=daoHelper.getConnection();
//		PreparedStatement stmt=null;
//		try
//		{
//			stmt=con.prepareStatement("update users set name=?,birthday=?," +
//						"phone=?,email=?,bankid=?,account=?,password=? where userid=?");
//			stmt.setString(1,user.getName());
//			stmt.setDate(2,user.getBirthday());
//			stmt.setString(3,user.getPhone());
//			stmt.setString(4,user.getEmail());
//			stmt.setString(5,user.getBankid());
//			stmt.setDouble(6,user.getAccount());
//			stmt.setString(7,user.getPasswordOne());
//			stmt.setString(8,user.getUserid());
//
//			stmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		finally
//		{
//			daoHelper.closeConnection(con);
//			daoHelper.closePreparedStatement(stmt);
//		}		*/
//
//    }
}
