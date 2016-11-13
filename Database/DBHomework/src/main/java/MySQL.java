import helper.FileIOHelper;
import helper.RandomHelper;
import mysql.DatabaseHelper;
import mysql.HikariSQLPool;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

import static helper.RandomHelper.RandomString;

/**
 * MySQL Core Class
 * @author Qiang
 * @date 07/11/2016
 */
public class MySQL {


    public static void addUser(int num) {
        Connection conn = HikariSQLPool.getConnection();

        try {
            PreparedStatement preState = conn.prepareStatement(DatabaseHelper.SQLmap.get("user").get(1));

            for (int i = 0; i < num; i++) {

                DatabaseHelper.buildPrestate(preState, "user", "" + (i + 1), RandomString(6) + i + "@qq.com", RandomHelper.getChineseName(), RandomString(8), "" + (445202199808000000L + i));

                preState.addBatch();


                if (i % 1000 == 0) {
                    preState.executeBatch(); // Execute every 1000 items.
                }
            }


            preState.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void insertRoute() {
        try {

            List<String> routes = FileIOHelper.fileReaderToString("src/main/data/route.txt");
            Connection conn = HikariSQLPool.getConnection();
            PreparedStatement preState = conn.prepareStatement(DatabaseHelper.SQLmap.get("route").get(1));
            for (String route : routes) {

                String routeId = route.split(" ")[0];
                String[] stations = route.split(" ")[1].split("-");
                long currentTime = System.currentTimeMillis();
                long timePass = 0;  // the former routes has pass what time

                for (int i = 0; i < stations.length - 1; i++) {

                    Calendar calendar = Calendar.getInstance();
                    Date date;

                    String start = stations[i];
                    String end = stations[i + 1];
                    int restTime = i == 0 ? 0 : RandomHelper.getRandomNum(SystemConstants.restLeast, SystemConstants.restLargest);
                    Timestamp startTime = new Timestamp(currentTime + timePass + restTime * 1000);

                    calendar.setTimeInMillis(startTime.getTime());


                    int interval = RandomHelper.getRandomNum(SystemConstants.intervalLeast, SystemConstants.intervalLargest);

                    timePass = timePass + interval * 3600 * 1000 + restTime * 1000;


                    for (int j = 0; j < SystemConstants.planDays; j++) {

                        date = new Date(calendar.getTimeInMillis());
                        calendar.add(Calendar.DATE, 1);
                        Timestamp tempstamp = new Timestamp(startTime.getTime() + j * 24 * 3600 * 1000);
                        DatabaseHelper.buildPrestate(preState, "route", routeId, "" + i, "" + j, date.toString(), start, end, "" + interval, tempstamp.toString());
                        preState.execute();

                    }


                }


            }


        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Assumption: Every train has 16 carriages and each carriage has 18 rows and
     * each row has 5 seat 'A' 'B' 'C' 'E' 'F'
     * Seats A, B, C are adjacent
     * <p>
     * Carriage 1-2 are business seats
     * Carriage 3-5 are firstClass seats
     * Carriage 6-16 are second Class seats
     * <p>
     * 无座 has no row or seat, each carriage from 6-16 has 20 tickets of 无座
     * 'N' means no seat
     */
    public static void insertSeat() {
        Connection conn = HikariSQLPool.getConnection();
        String sql = "SELECT route_id,station_id,route_number ,  `date`  FROM route";
        int carriagesNumber = SystemConstants.carriagesNumber;
        int[] carriageSpilt = SystemConstants.carriageSpilt;  // 1-2 business seats , 3-5 first class seats
        String[] carriageName = SystemConstants.carriageName;
        int rowNum = SystemConstants.rowNum;
        char[] seatNumber = SystemConstants.seatNumber;
        try {
            PreparedStatement preState = conn.prepareStatement(sql);

            ResultSet resultSet = preState.executeQuery();

            preState = conn.prepareStatement(DatabaseHelper.SQLmap.get("seat").get(1));
            while (resultSet.next()) {

                String route_id = resultSet.getString(1);
                int stationId = resultSet.getInt(2);
                int routeNumber = resultSet.getInt(3);
                Date date = resultSet.getDate(4);
                int carriage;
                int cost = 200;
                System.out.println(route_id + " " + stationId + " " + routeNumber + " " + date);

                String type = "无座";
                for (int i = 1; i <= carriagesNumber; i++) {
                    carriage = i;

                    for (int j = 0; j < carriageSpilt.length; j++) {
                        if (i < carriageSpilt[j]) {
                            type = carriageName[j];
                            cost = RandomHelper.getRandomNum(SystemConstants.seatCost[j], SystemConstants.seatCost[j] + SystemConstants.seatScope);
                            break;
                        }
                    }

                    for (int j = 1; j <= rowNum; j++) {

                        for (char seatNum : seatNumber) {

                            DatabaseHelper.buildPrestate(preState, "seat", route_id, stationId + "", routeNumber + "", date.toString(), type, carriage + "", j + "", seatNum + "", cost + "", "1");
                            preState.addBatch();
                        }


                    }

                    if (i >= carriageSpilt[carriageSpilt.length - 2]) {  // insert no seat tickets
                        type = "无座";
                        for (int j = 0; j < SystemConstants.noSeatNumEachCarriage; j++) {
                            DatabaseHelper.buildPrestate(preState, "seat", route_id, stationId + "", routeNumber + "", date.toString(), type, carriage + "", j + "", 'N' + "", cost + "", "1");
                            preState.addBatch();
                        }


                    }

                }

                preState.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public synchronized static boolean checkIfAvailable(String start, String end, Date checkDate, boolean whetherOutput) {
        Connection conn = HikariSQLPool.getConnection();
        String sql;
        // start and end are the same
        Boolean findTicket = false;
        Boolean hasRoutes = false;
        try {
            // get the route id
            sql = "SELECT route1.route_id, route1.station_id,route2.station_id,route1.route_number,route1.start_time,route1.start_station, route2.end_station " +
                    "FROM route route1, route route2\n" +
                    "WHERE route1.start_station LIKE ? " +
                    "AND route2.end_station LIKE ?  " +
                    "AND route1.route_id = route2.route_id AND route1.route_number = route2.route_number " +
                    "AND route1.date = ? ;\n";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, start + "%");
            preState.setString(2, end + "%");
            preState.setDate(3, checkDate);
            ResultSet resultSet = preState.executeQuery();


            // check if has same seats available in each routes
            while (resultSet.next()) {
                hasRoutes = true;

                String routeId = resultSet.getString(1);
                int lowStation = resultSet.getInt(2);
                int highStation = resultSet.getInt(3);
                int routeNumber = resultSet.getInt(4);
                Date date = resultSet.getDate(5);
                String startStation = resultSet.getString(6);
                String endStation = resultSet.getString(7);

                sql = "SELECT type, COUNT(*) FROM ( " +
                        "SELECT route_id, date, carriage , row, number,type " +
                        "FROM seat " +
                        "WHERE route_id = ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND type <> '无座' " +
                        "GROUP BY route_id,date,carriage, row , number,type " +
                        "HAVING MIN(available) = 1) temp " +
                        "GROUP BY type;";
                preState = conn.prepareStatement(sql);
                preState.setString(1, routeId);
                preState.setInt(2, routeNumber);
                preState.setInt(3, lowStation);
                preState.setInt(4, highStation);
                ResultSet tempSet = preState.executeQuery();

                System.out.println("查票结果如下");

                while (tempSet.next() && whetherOutput) {
                    System.out.println("车号为 " + routeId + " 从 " + startStation + " 到 " + endStation + " 在" + date.toString() + " 有" + tempSet.getString(1) + " " + tempSet.getInt(2) + "张票");
                    findTicket = true;
                }

                sql = "SELECT type, COUNT(*) FROM (\n" +
                        "SELECT route_id, date, carriage , row, number,type\n" +
                        "FROM seat\n" +
                        "WHERE route_id= ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND type = '无座'\n" +
                        "GROUP BY route_id,date,carriage, row , number,type\n" +
                        "HAVING MIN(available) = 1) temp\n" +
                        "GROUP BY type;";
                preState = conn.prepareStatement(sql);
                preState.setString(1, routeId);
                preState.setInt(2, routeNumber);
                preState.setInt(3, lowStation);
                preState.setInt(4, highStation);
                tempSet = preState.executeQuery();

                if (tempSet.next() && whetherOutput) {
                    System.out.println("车号为 " + routeId + " 从 " + startStation + " 到 " + endStation + " 在" + date.toString() + " 有" + tempSet.getString(1) + " " + tempSet.getInt(2) + "张票");
                    findTicket = true;
                }

                if (!findTicket && whetherOutput) {
                    System.out.println("Sorry, 无票可买");
                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (!hasRoutes && whetherOutput) {
            System.out.println("Sorry, 未查到相关线路");
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findTicket;


    }

    /**
     * 借助buytickets实现买票，若成功，生成订单并插入数据库中
     *
     * @param route_id
     * @param start
     * @param end
     * @param date
     * @param type
     * @param num
     * @param userId
     * @return
     */
    public synchronized static String order(String route_id, String start, String end, String date, SystemConstants.type type, int num, int userId) {

        String[] dates = date.split("-");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
        StringBuffer result = new StringBuffer(1000);
        int sum = buyTicket(route_id, start, end, new Date(calendar.getTimeInMillis()), type, num, result);

        if (sum != -1) {

            try {
                Connection conn = HikariSQLPool.getConnection();
                String sql = "INSERT INTO r_order (user_id, bill, start_station, end_station, ticket_num, time) VALUES (? , ? , ? , ? , ? , ?);";
                PreparedStatement preState = conn.prepareStatement(sql);
                preState.setInt(1, userId);
                preState.setInt(2, sum);
                preState.setString(3, start);
                preState.setString(4, end);
                preState.setInt(5, num);
                Timestamp temp = new Timestamp(System.currentTimeMillis());
                preState.setTimestamp(6, temp);
                int affect = preState.executeUpdate();
                conn.close();
                if (affect == 1) {
//                    conn.commit();
                    result.insert(0, "Success 您于" + temp.toString() + "成功购买车票");


                    return result.toString();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }


        return "Fail 购票失败";
    }

    /**
     * 核心方法,实现购买火车票
     *
     * @param route_id  火车id
     * @param start     起始城市
     * @param end       终止城市
     * @param checkDate 出发日期
     * @param type      车票类型
     * @param num       数量
     * @param result    购买信息
     * @return 若成功, 返回总价, 若失败, 返回-1
     */
    private synchronized static int buyTicket(String route_id, String start, String end, Date checkDate, SystemConstants.type type, int num, StringBuffer result) {
        Connection conn;
        try {
            conn = HikariSQLPool.getConnection();
            //关闭自动commit,开启事务
            conn.setAutoCommit(false);
            String sql;

            /*
                查询该线路是否存在
             */
            sql = "SELECT route1.station_id,route2.station_id,route1.route_number,route1.start_time FROM route route1, route route2\n" +
                    "WHERE route1.route_id = ? AND route1.start_station = ? AND route2.end_station = ?  AND route1.date = ? AND route1.route_id = route2.route_id AND route1.route_number = route2.route_number;";
            PreparedStatement preState = conn.prepareStatement(sql);
            preState.setString(1, route_id);
            preState.setString(2, start);
            preState.setString(3, end);
            preState.setDate(4, checkDate);
            ResultSet resultSet = preState.executeQuery();

            boolean success = true;
            int ticketBought = 0;
            int affectRows;

            int cost = 0;
            if (resultSet.next()) {    //找到了该条线路
                int startStationID = resultSet.getInt(1);
                int endStationID = resultSet.getInt(2);
                int routeNumber = resultSet.getInt(3);
                Timestamp startTime = resultSet.getTimestamp(4);
                /*
                    检索所有可用的座位
                 */
                sql = "SELECT carriage, row, number, SUM(cost)\n" +
                        "FROM seat \n" +
                        "WHERE route_id= ? AND route_number = ? AND seat.station_id >= ? AND seat.station_id <= ? AND seat.type = ?\n" +
                        "GROUP BY carriage, row , number\n" +
                        "HAVING min(available) = 1;";

                PreparedStatement specificPreState = conn.prepareStatement(sql);
                specificPreState.setString(1, route_id);
                specificPreState.setInt(2, routeNumber);
                specificPreState.setInt(3, startStationID);
                specificPreState.setInt(4, endStationID);
                specificPreState.setString(5, type.name);
                ResultSet checkResult = specificPreState.executeQuery();


                int stationNum = endStationID - startStationID + 1;

                boolean buySpecificTickets;  //是否分配了连续的座位,如果为true,需要重新更新可用座位表
                while (checkResult.next() && ticketBought < num) {
                    buySpecificTickets = false;
                    if (cost == 0) {            //如果cost还未赋值
                        cost = checkResult.getInt(4);
                    }

                    if (num - ticketBought >= 3 && type != SystemConstants.type.NO_SEAT) {      //三张票连在一起
                        sql = "SELECT carriage, row FROM (\n" +
                                "SELECT route_id, route_number, carriage , row, number,type\n" +
                                "FROM seat\n" +
                                "WHERE route_id= ? AND route_number= ? AND seat.station_id >= ? AND seat.station_id <= ? AND type = ?\n" +
                                "GROUP BY route_id,route_number,carriage, row , number,type\n" +
                                "HAVING MIN(available) = 1) temp WHERE number <= 'C'\n" +
                                "GROUP BY carriage, row\n" +
                                "HAVING COUNT(*) > 2;";

                        preState = conn.prepareStatement(sql);
                        preState.setString(1, route_id);
                        preState.setInt(2, routeNumber);
                        preState.setInt(3, startStationID);
                        preState.setInt(4, endStationID);
                        preState.setString(5, type.name);
                        ResultSet checkThreeSeat = preState.executeQuery();

                        while (checkThreeSeat.next()) {

                            int threeSeatCarr = checkThreeSeat.getInt(1);
                            int threeSeatRow = checkThreeSeat.getInt(2);

                            sql = "UPDATE seat\n" +
                                    "SET available = 0\n" +
                                    "WHERE route_id= ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND carriage = ? AND row = ? AND number <= 'C' AND available = 1;";
                            preState = conn.prepareStatement(sql);
                            preState.setString(1, route_id);
                            preState.setInt(2, routeNumber);
                            preState.setInt(3, startStationID);
                            preState.setInt(4, endStationID);
                            preState.setInt(5, threeSeatCarr);
                            preState.setInt(6, threeSeatRow);
                            affectRows = preState.executeUpdate();
                            if (affectRows != 3 * stationNum) {
                                conn.rollback(); // give up three adjacent seats and try to buy single seats
                                break;
                            } else {
                                ticketBought += 3;
                                result.append(printTicket(route_id, startTime, start, end, threeSeatCarr, threeSeatRow, 'A', type, cost));
                                result.append(printTicket(route_id, startTime, start, end, threeSeatCarr, threeSeatRow, 'B', type, cost));
                                result.append(printTicket(route_id, startTime, start, end, threeSeatCarr, threeSeatRow, 'C', type, cost));
                                buySpecificTickets = true;
                                if (num - ticketBought < 3) { // 没有更多三张票要买了,离开
                                    break;
                                }
                            }
                        }


                    }

                    if (num - ticketBought >= 2 && type != SystemConstants.type.NO_SEAT) {      //2张票连在一起
                        sql = "SELECT carriage, row FROM (\n" +
                                "SELECT route_id, route_number, carriage , row, number,type\n" +
                                "FROM seat\n" +
                                "WHERE route_id= ? AND route_number= ? AND seat.station_id >= ? AND seat.station_id <= ? AND type = ?\n" +
                                "GROUP BY route_id,route_number,carriage, row , number,type\n" +
                                "HAVING MIN(available) = 1) temp WHERE number = 'E' OR number = 'F'\n" +
                                "GROUP BY carriage, row\n" +
                                "HAVING COUNT(*) = 2;";

                        preState = conn.prepareStatement(sql);
                        preState.setString(1, route_id);
                        preState.setInt(2, routeNumber);
                        preState.setInt(3, startStationID);
                        preState.setInt(4, endStationID);
                        preState.setString(5, type.name);
                        ResultSet checkTwoSeat = preState.executeQuery();

                        while (checkTwoSeat.next()) {

                            int twoSeatCarr = checkTwoSeat.getInt(1);
                            int twoSeatRow = checkTwoSeat.getInt(2);

                            sql = "UPDATE seat\n" +
                                    "SET available = 0\n" +
                                    "WHERE route_id= ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND carriage = ? AND row = ? AND number >= 'E' AND number <= 'F' AND available = 1;";
                            preState = conn.prepareStatement(sql);
                            preState.setString(1, route_id);
                            preState.setInt(2, routeNumber);
                            preState.setInt(3, startStationID);
                            preState.setInt(4, endStationID);
                            preState.setInt(5, twoSeatCarr);
                            preState.setInt(6, twoSeatRow);
                            affectRows = preState.executeUpdate();
                            if (affectRows != 2 * stationNum) {
                                conn.rollback(); // give up three adjacent seats and try to but single seats
                                break;
                            } else {
                                ticketBought += 2;
                                result.append(printTicket(route_id, startTime, start, end, twoSeatCarr, twoSeatRow, 'E', type, cost));
                                result.append(printTicket(route_id, startTime, start, end, twoSeatCarr, twoSeatRow, 'F', type, cost));
                                buySpecificTickets = true;
                                if (num - ticketBought < 2) { // 没有更多2张票要买了,离开
                                    break;
                                }
                            }
                        }


                    }
                    if (buySpecificTickets) {                         //如果购买了特殊的票
                        checkResult = specificPreState.executeQuery(); //更新可用票
                        continue;
                    }


                    if (num - ticketBought > 0) {                   //购买单张票
                        int carriage = checkResult.getInt(1);
                        int row = checkResult.getInt(2);
                        char number = checkResult.getString(3).charAt(0);


                        sql = "UPDATE seat\n" +
                                "SET available = 0\n" +
                                "WHERE route_id= ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND carriage = ? AND row = ? AND number = ? AND available = 1;";
                        preState = conn.prepareStatement(sql);
                        preState.setString(1, route_id);
                        preState.setInt(2, routeNumber);
                        preState.setInt(3, startStationID);
                        preState.setInt(4, endStationID);
                        preState.setInt(5, carriage);
                        preState.setInt(6, row);
                        preState.setString(7, String.valueOf(number));

                        affectRows = preState.executeUpdate();
                        if (affectRows != stationNum) {
                            success = false;
                            conn.rollback();
                            break;
                        } else {
                            ticketBought++;
                            result.append(printTicket(route_id, startTime, start, end, carriage, row, number, type, cost));
                        }


                    }


                }


            }

            if (!success || ticketBought < num) {
                result.setLength(0);
                result.append("买票失败");
                conn.rollback();
                conn.close();
                return -1;
            } else {
//                conn.commit();
                result.insert(0, "您好,您买到的票总价为: " + num * cost + "元,分别为:\n");
                conn.commit();
                conn.close();
                return num * cost;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private static String printTicket(String route_id, Timestamp startTime, String start, String end, int carriage, int row, char number, SystemConstants.type type, int cost) {
        if (type == SystemConstants.type.NO_SEAT) {
            return "您好: 您的票为 " + route_id + " 列 " + startTime + " 时刻, 起始站:" + start + " 终点站:" + end + " 座位类型:" + type.name + " 车厢:" + carriage + " 票价: " + cost + "\n";
        } else {
            return "您好: 您的票为 " + route_id + " 列 " + startTime + " 时刻, 起始站:" + start + " 终点站:" + end + " 座位类型:" + type.name + " 车厢:" + carriage + " 座位行: " + row + " 座位号:" + number + " 票价: " + cost + "\n";
        }


    }





}
