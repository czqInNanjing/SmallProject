import mysql.SQLConnector;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author Qiang
 * @date 09/11/2016
 */
public class MySQLTest {
    Connection conn;
    @Before
    public void setUp() throws Exception {

        conn = SQLConnector.getConnection();


    }


    @Test
    public void clearDatabase() throws SQLException {
        String sql = "TRUNCATE TABLE route";
        conn.prepareStatement(sql).execute();
        sql = "TRUNCATE TABLE user";
        conn.prepareStatement(sql).execute();
        sql = "TRUNCATE TABLE r_order";
        conn.prepareStatement(sql).execute();
        sql = "TRUNCATE TABLE seat";
        conn.prepareStatement(sql).execute();

    }

    @Test
    public void setAllSeatAvailable() throws SQLException {
        String sql = "UPDATE seat\n" +
                "SET available = 1\n" +
                "WHERE available = 0;";
        conn.prepareStatement(sql).execute();
    }

    @Test
    public void addUser() throws Exception {
        MySQL.addUser(10000);
    }

    @Test
    public void insertRoute() throws Exception {
        MySQL.insertRoute();
    }

    @Test
    public void insertSeat() throws Exception {
        MySQL.insertSeat();
    }

    @Test
    public void checkIfAvailable() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
        MySQL.checkIfAvailable("北京" , "济南" , new Date(calendar.getTimeInMillis()), true);
    }

    @Test
    public void order() throws Exception {
        setAllSeatAvailable();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
//        MySQL.checkIfAvailable("北京" , "济南" , new Date(calendar.getTimeInMillis()));
        //倒数第二个参数为购买的票数,最后一个参数为用户id
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-12", SystemConstants.type.FIRST_CLASS, 1, 1));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-12", SystemConstants.type.FIRST_CLASS, 20, 3));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 4, 2));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 3, 1));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 4, 3));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 5, 2));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 4, 3));
//        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 5, 2));

//===============================================================================================

        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.NO_SEAT, 4, 3));
        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.NO_SEAT, 5, 2));
        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.BUSINESS_SEAT, 4, 3));
        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.SECOND_CLASS, 5, 2));

    }

}