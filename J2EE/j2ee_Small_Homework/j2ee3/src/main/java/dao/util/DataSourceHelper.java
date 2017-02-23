package dao.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author Qiang
 * @since 03/01/2017
 */
public class DataSourceHelper {

    private static DataSource dataSource;

    private DataSourceHelper(){}

    static {
        InitialContext jndiContext ;

        try {
            jndiContext = new InitialContext();
            dataSource = (DataSource) jndiContext
                    .lookup("java:comp/env/jdbc/j2ee1");
        } catch (NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static DataSource getInstance(){
        return dataSource;
    }


}
