package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Qiang
 */
public class DatabaseHelper {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 与数据库的连接
     */
    protected Connection conn;
    /**
     *
     */
    protected PreparedStatement preState;
    /**
     * 数据库语句
     */
    protected String sql;
    /**
     * 数据库操作影响结果集
     */
    protected ResultSet result;
    /**
     * 查找返回的消息
     */
    protected ArrayList<String> findMes;
    /**
     * 在数据库操作中影响到的行数（信息条数）
     */
    protected int affectRows;
    /**
     *
     */
    protected static DatabaseHelper helper = new DatabaseHelper();

    public static final Map<String, ArrayList<String>> SQLmap = new HashMap<String, ArrayList<String>>(50);

    static {

        SQLmap.put("user", helper.bulidSQL("user" ,5 , "id" , "email" , "name" , "password" , "id_num" ));
        SQLmap.put("route" , helper.bulidSQL("route", 8, "route_id" , "station_id" , "route_number", "date" , "start_station" , "end_station" , "interval" , "start_time" ));
        SQLmap.put("seat", helper.bulidSQL("seat" , 10 , "route_id" , "station_id", "route_number", "date" , "type" , "carriage" , "row" , "number" , "cost", "available"));

    }

    public DatabaseHelper() {
    }

    public static PreparedStatement buildPrestate(PreparedStatement preState, String tableName , String... paras) {
        try {
            int paralen = Integer.parseInt(SQLmap.get(tableName).get(0));

            for (int i = 0; i < paralen; i++) {
                preState.setString(i + 1, paras[i]);
            }

        }  catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return preState;
    }


    public ArrayList<String> bulidSQL(String tableName, int num,
                                      String... paras) {
        ArrayList<String> temp = new ArrayList<>(6);
        temp.add(String.valueOf(num));
        temp.add(bulidAddSQL(tableName, num));
        temp.add(bulidDelSQL(tableName, paras[0]));
        temp.add(bulidFindSQL(tableName, paras[0]));
        temp.add(bulidUpdateSQL(tableName, num, paras));
        // 清空表内数据，用于初始化
        temp.add("TRUNCATE TABLE " + tableName);
        return temp;
    }



    private String bulidAddSQL(String name, int paraNum) {
        StringBuffer buffer = new StringBuffer("INSERT INTO `" + name
                + "` VALUES (");
        for (int i = 0; i < paraNum - 1; i++) {
            buffer.append(" ? ,");
        }

        buffer.append("? )");

        return buffer.toString();
    }

    private String bulidDelSQL(String name, String primaryKey) {
        return "DELETE FROM `" + name + "` WHERE " + primaryKey + " =";
    }

    private String bulidFindSQL(String name, String primaryKey) {

        return "SELECT * FROM `" + name + "` WHERE " + primaryKey + " = ";

    }

    private String bulidUpdateSQL(String name, int paraNum, String... paras) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("UPDATE `").append(name).append("` SET ");
        for (int i = 0; i < paraNum - 1; i++) {
            buffer.append(paras[i + 1]).append('=').append(" ? ,");
        }
        buffer.deleteCharAt(buffer.length() - 1);

        buffer.append("WHERE " + paras[0] + " = ");

        return buffer.toString();
    }






}