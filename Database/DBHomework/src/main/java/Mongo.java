import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.sun.javadoc.Doc;
import org.bson.Document;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

/**
 * Mongo Core Class
 * @author Qiang
 * @date 10/11/2016
 */
public class Mongo {

    private final static String routeTableName = "routes";
    private final static String seatTableName = "seats";

    private MongoDatabase db;

    private final static String routeIDStr = "route_id";
    private final static String station_idStr = "station_id";
    private final static String route_numberStr = "route_number";
    private final static String dateStr = "date";
    private final static String start_stationStr = "start_station";
    private final static String end_stationStr = "end_station";
    private final static String intervalStr = "interval";
    private final static String start_timeStr = "start_time";
    private final static String carriageStr = "carriage";
    private final static String rowStr = "row";
    private final static String numberStr = "number";
    private final static String availableStr = "available";
    private final static String seatTypeStr = "type";
    private final static String highStation_idStr = "high_station";
    public Mongo() {
        MongoClient mongoClient = new MongoClient("localhost" , 27017);
        db = mongoClient.getDatabase("railway");
    }

    public void checkIfAvailable(String start, String end, Date checkDate, boolean whetherOutput){
        MongoCollection<Document> routes = db.getCollection(routeTableName);
        MongoCollection<Document> seats = db.getCollection(seatTableName);

        List<Document> routesAvailable = new ArrayList<>(100);
    String te ;

        //第一步：找到该线路
        BasicDBObject route1 = new BasicDBObject(dateStr, checkDate.toString()).append(start_stationStr , start );

        BasicDBObject route2 = new BasicDBObject(end_stationStr ,  end );
        FindIterable<Document> result = routes.find(route1);

//        sql = "SELECT route1.route_id, route1.station_id,route2.station_id,route1.route_number,route1.start_time,route1.start_station, route2.end_station " +
//                "FROM route route1, route route2\n" +
//                "WHERE route1.start_station LIKE ? " +
//                "AND route2.end_station LIKE ?  " +
//                "AND route1.route_id = route2.route_id AND route1.route_number = route2.route_number " +
//                "AND route1.date = ? ;\n";

        BasicDBObject sameWithRoute1;
        BasicDBList togetherList = new BasicDBList();
        BasicDBObject checkRoute;
        for (Document document : result) {
            sameWithRoute1 = new BasicDBObject(routeIDStr , document.get(routeIDStr)).append(route_numberStr , document.get(route_numberStr ));
            togetherList.clear();
            togetherList.add(sameWithRoute1);
            togetherList.add(route2);
            checkRoute = new BasicDBObject();
            checkRoute.put("$and", togetherList);
            FindIterable<Document> findResult = routes.find(checkRoute);
            if (findResult.first() != null) {
                Document finalStation = findResult.first();
                routesAvailable.add(buildRoute(document , finalStation.get(station_idStr), finalStation.get(end_stationStr)));

            }

        }


        //第二步：对每一条线路查询其可用票数
        for ( Document oneAvailableRoute : routesAvailable) {


            //模拟执行以下mysql 语句
//            sql = "SELECT type, COUNT(*) FROM ( " +
//                    "SELECT route_id, date, carriage , row, number,type " +
//                    "FROM seat " +
//                    "WHERE route_id = ? AND route_number = ? AND station_id >= ? AND station_id <= ? AND type <> '无座' " +
//                    "GROUP BY route_id,date,carriage, row , number,type " +
//                    "HAVING MIN(available) = 1) temp " +
//                    "GROUP BY type;";
            AggregateIterable<Document> output = seats.aggregate(Arrays.asList(

                    new Document("$match", new Document(routeIDStr, oneAvailableRoute.get(routeIDStr))
                            .append(route_numberStr, oneAvailableRoute.get(route_numberStr))
                            .append(station_idStr , new Document("$gte" , oneAvailableRoute.get(station_idStr)))
                            .append(station_idStr , new Document("$lte" , oneAvailableRoute.get(highStation_idStr)))
                            ),
                    new Document("$group" , new Document("_id" , new Document(routeIDStr , "$" + routeIDStr).append(dateStr , "$" + dateStr)
                            .append(carriageStr , "$" + carriageStr).append(rowStr , "$" + rowStr).append(numberStr , "$" + numberStr).append(seatTypeStr , "$" + seatTypeStr) //结束Group by
                                                        ).append("temp" , new Document("$min" , "$" + availableStr))
                            ),
                    new Document("$match" , new Document("temp" , 1)),
                    new Document("$project" , new Document("_id" , 1) ),//丢弃temp属性

                    new Document("$group" , new Document("_id" , new Document(seatTypeStr , "$_id." + seatTypeStr))
                            .append("count" , new Document("$sum" , 1))
                                )

                    )



            );


            System.out.println("从 "  + oneAvailableRoute.getString(start_stationStr) + " 到 " + oneAvailableRoute.getString(end_stationStr) + " 在 "  + oneAvailableRoute.getString(dateStr)+ " 线路为 " + oneAvailableRoute.getString(routeIDStr) + " 有如下票次：");
            for (Document document : output) {
//                System.out.println(document);
                System.out.println("类型：" + ((Document)document.get("_id")).get(seatTypeStr) + " 座位数" + document.getInteger("count"));

            }

        }







    }


    public boolean buyTicket(String checkRouteIDStr , String start, String end, String date,String type , int num) {
        boolean success = false;
        MongoCollection<Document> routes = db.getCollection(routeTableName);
        MongoCollection<Document> seats = db.getCollection(seatTableName);
        StringBuffer buyResult = new StringBuffer(1000);

        //一、查询线路是否存在
//       sql = "SELECT route1.station_id,route2.station_id,route1.route_number,route1.start_time FROM route route1, route route2\n" +
//                "WHERE route1.route_id = ? AND route1.start_station = ? AND route2.end_station = ?  AND route1.date = ? AND route1.route_id = route2.route_id AND route1.route_number = route2.route_number;";

        AggregateIterable<Document> output = routes.aggregate(Arrays.asList(
                new Document("$match", new Document(routeIDStr , checkRouteIDStr).append("$or" , Arrays.asList(new Document(start_stationStr , start) , new Document(end_stationStr , end))).append(dateStr , date)
                )

        ));
        int ticketBought = 0;
        Integer routeNumber = -1;
        Integer startStationId = 0;
        Integer endStationId = 0;

        boolean isSecond = false;
        for (Document document : output) {
            if (isSecond) {
                endStationId = document.getInteger(station_idStr);
                break;
            }
            isSecond = true;
            routeNumber = document.getInteger(route_numberStr);
            startStationId = document.getInteger(station_idStr);
            if (document.getString(end_stationStr).equals(end)) {  //如果首发于终点站在同一区间内，则不需要执行第二次
                endStationId = document.getInteger(station_idStr);
                break;
            }

        }


        if (routeNumber >= 0) {  //找到该线路

            AggregateIterable<Document> seatOutput = seats.aggregate(Arrays.asList(

                    new Document("$match", new Document(routeIDStr, checkRouteIDStr)
                            .append(route_numberStr, routeNumber)
                            .append(station_idStr , new Document("$gte" , startStationId))
                            .append(station_idStr , new Document("$lte" , endStationId))
                            .append(seatTypeStr , type)
                    ),
                    new Document("$group" , new Document("_id" , new Document(routeIDStr , "$" + routeIDStr).append(dateStr , "$" + dateStr)
                            .append(carriageStr , "$" + carriageStr).append(rowStr , "$" + rowStr).append(numberStr , "$" + numberStr).append(seatTypeStr , "$" + seatTypeStr) //结束Group by
                    ).append("temp" , new Document("$min" , "$" + availableStr))
                    ),
                    new Document("$match" , new Document("temp" , 1)),
                    new Document("$project" , new Document("_id" , 1) )//丢弃temp属性




                    )



            );
            int stationNumber = endStationId - startStationId + 1;

            java.util.Date startTime = null;
            for (Document seat : seatOutput) {
//                System.out.println(seat);


                Document seatMes = (Document) seat.get("_id");


                startTime = (java.util.Date) seatMes.get(dateStr);
                int carriage = seatMes.getInteger(carriageStr);
                int row = seatMes.getInteger(rowStr);
                String number = seatMes.getString(numberStr);


                UpdateResult result = seats.updateMany(new Document(routeIDStr, checkRouteIDStr)
                        .append(route_numberStr, routeNumber)
                        .append(carriageStr ,carriage)
                        .append(rowStr , row)
                        .append(numberStr , number)
                        .append(station_idStr , new Document("$gte" , startStationId))
                        .append(station_idStr , new Document("$lte" , endStationId))
                        .append(seatTypeStr , type)
                        .append(availableStr , 1),

                        new Document("$set" , new Document(availableStr , 0))




                );
                if (stationNumber == result.getMatchedCount() && stationNumber == result.getModifiedCount()) {
                    ticketBought++;
                    buyResult.append(printTicket(checkRouteIDStr , startTime , start ,end ,  carriage , row , number.charAt(0) , type  ));
                    if (ticketBought >= num) {

                        success = true;
                        break;
                    }
                } else {
                    success = false;
                    break;
                }

//                seats.updateOne(new Document("_id" , seat.getString("_id")) , new Document("$set" , new Document(availableStr , 0) ));
            }







        } else {
            System.out.println("Sorry 该线路不存在或起始终点站有误");
        }

        if (ticketBought == num && success) {
            buyResult.insert(0 , "您好，你成功购买火车票 " + num + " 张，分别为:\n");

        } else {
            buyResult.setLength(0);
            buyResult.append("买票失败");
        }

        System.out.println(buyResult.toString());

        return success;
    }


    private static String printTicket(String route_id, java.util.Date startTime, String start, String end, int carriage, int row, char number, String type) {
        if (type.equals("无座")) {
            return "您好: 您的票为 " + route_id + " 列 " + startTime + " 时刻, 起始站:" + start + " 终点站:" + end + " 座位类型:" + type + " 车厢:" + carriage + "\n";
        } else {
            return "您好: 您的票为 " + route_id + " 列 " + startTime + " 时刻, 起始站:" + start + " 终点站:" + end + " 座位类型:" + type + " 车厢:" + carriage + " 座位行: " + row + " 座位号:" + number + "\n";
        }


    }

    private Document buildRoute(Document document, Object station_id, Object end_station_name) {

        return document.append(highStation_idStr , station_id).append(end_stationStr , end_station_name);

    }

    public void setAllSeatsAvailable () {
        db.getCollection(seatTableName).updateMany(new BasicDBObject(availableStr , 0) , new Document("$set" , new Document(availableStr , 1)));
    }

}
