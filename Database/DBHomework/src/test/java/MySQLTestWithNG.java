import mysql.SQLConnector;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;



/**
 * @author Qiang
 * @date 09/11/2016
 */
public class MySQLTestWithNG {
    Connection connection;

    /**
     * 该方法在任意测试前执行,保证所有座位都是可用的
     * @throws Exception
     */
    @BeforeSuite
    public void setUp() throws Exception{

        connection = SQLConnector.getConnection();
        setAllSeatAvailable();
        connection.close();


    }

    @org.testng.annotations.Test(invocationCount = 1000, threadPoolSize = 100)
    public void testCheckIfAvailable() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
        //最后一个参数表示是否输出查票结果
        MySQL.checkIfAvailable("北京" , "济南" , new Date(calendar.getTimeInMillis()), true);
    }

    @org.testng.annotations.Test(invocationCount = 1000, threadPoolSize = 100)
    public void testOrder() throws Exception {
    //倒数第二个参数为购买的票数,最后一个参数为用户id

//        System.out.println();;

        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 4, 3));
        System.out.println(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 3, 2));


    }

    @org.testng.annotations.Test(invocationCount = 1000, threadPoolSize = 80, groups = {"BuyDifferent"})
    public void testBuy() throws Exception {
//        System.out.println(2);
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
        int rand = random.nextInt(2);
        switch (rand) {
            case 0:
                Assert.assertEquals(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.SECOND_CLASS, 4, 3).charAt(0), 'S');
                break;
            case 1:
                Assert.assertEquals(MySQL.order("G101" , "北京南" , "济南西" , "2016-11-10", SystemConstants.type.FIRST_CLASS, 4, 3).charAt(0), 'S');
                break;
            case 3:
                Assert.assertEquals(MySQL.checkIfAvailable("北京" , "济南" , new Date(calendar.getTimeInMillis()), true), true);
        }


    }

//    @org.testng.annotations.Test(invocationCount = 100, threadPoolSize = 80, groups = {"BuyDifferent"})
//    public void testBu2y() throws Exception {
//        System.out.println(1);
//
//
//    }



    private void setAllSeatAvailable() throws SQLException {
        String sql = "UPDATE seat\n" +
                "SET available = 1\n" +
                "WHERE available = 0;";
        connection.prepareStatement(sql).execute();
    }

}