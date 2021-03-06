package constants;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Qiang
 * @since 10/11/2016
 */
public class HikariSQLPool {

    private static HikariDataSource dataSource;

    static {
        dataSource = new HikariDataSource();
//        jdbc:mysql://localhost:3306/j2ee1?autoReconnect=true
//        jdbc:mysql://localhost:3306/dbname
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/j2ee1?useUnicode=true&characterEncoding=UTF-8");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setConnectionTimeout(100*1000);
        dataSource.setAllowPoolSuspension(true);
        dataSource.setMaximumPoolSize(30);

    }


    private HikariSQLPool(){}

    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.err.println("Error , SQL Connection!");
        System.exit(1);
        return null;

    }





}
