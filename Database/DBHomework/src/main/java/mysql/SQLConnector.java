package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <b>已废弃,改用HikariSQLPool 代替</>
 * 数据库连接器
 * @author Qiang
 *
 */
public class SQLConnector {
    /**
     * 所有数据读取使用的数据流
     */
    private static Connection conn;


    private SQLConnector(){

    }

    /**
     * 获得连接
     * @return
     */
    public static Connection getConnection()
    {
        if(conn == null){
            connection();
        }
        return conn;
    }



    private static void connection() {
        try {
            //使用JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //连接URL为'jdbc:mysql//服务器地址/数据库名 ' ，后面的2个参数分别是登陆用户名和密码
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/railway?useUnicode=true&characterEncoding=UTF-8","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 程序结束时调用
     */
    public static void disconnect(){
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}