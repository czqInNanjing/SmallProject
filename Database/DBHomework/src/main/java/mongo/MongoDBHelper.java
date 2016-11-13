package mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import mysql.HikariSQLPool;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiang
 * @date 07/11/2016
 */
public class MongoDBHelper {


    MongoDatabase db;


    public MongoDBHelper() {
        MongoClient mongoClient = new MongoClient("localhost" , 27017);
        db = mongoClient.getDatabase("railway");
    }



    public void insertRoute() throws SQLException {
        Connection connection = HikariSQLPool.getConnection();
        String sql = "SELECT * FROM route";
        PreparedStatement preparedStatement =  connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Document> documents = new ArrayList<>(3000);
        Document route;

        MongoCollection<Document> docs = db.getCollection("routes");
        docs.deleteMany(new BasicDBObject());
        while (resultSet.next()) {
            route = new Document("route_id" , resultSet.getString(1)).append("station_id", resultSet.getInt(2))
                                .append("route_number" , resultSet.getInt(3)).append("date" , resultSet.getDate(4).toString()).append("start_station" , resultSet.getString(5))
                                .append("end_station" , resultSet.getString(6)).append("interval", resultSet.getInt(7)).append("start_time",resultSet.getTimestamp(8));


            documents.add(route);

            if (documents.size() % 1000 == 0) {
                docs.insertMany(documents);
                documents.clear();
            }



        }
        if (documents.size() != 0 ){
            docs.insertMany(documents);
        }
        connection.close();
        System.out.println("Insert row number: " + docs.count());


    }


    public void insertUser() throws  SQLException {
        Connection connection = HikariSQLPool.getConnection();
        String sql = "SELECT * FROM USER ";
        PreparedStatement preparedStatement =  connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Document> documents = new ArrayList<>(3000);
        Document route;

        MongoCollection<Document> docs = db.getCollection("users");
        docs.deleteMany(new BasicDBObject()); // clear All users
        while (resultSet.next()) {
            route = new Document("email" , resultSet.getString(2)).append("name", resultSet.getString(3))
                    .append("password" , resultSet.getString(4)).append("id_num" , resultSet.getString(5));


            documents.add(route);

            if (documents.size() % 1000 == 0) {
                docs.insertMany(documents);
                documents.clear();
            }



        }
        if (documents.size() != 0 ){
            docs.insertMany(documents);
        }
        System.out.println("Insert row number: " + docs.count());
        connection.close();

    }


    public void insertSeat() throws SQLException {
        Connection connection = HikariSQLPool.getConnection();
        String sql = "SELECT * FROM SEAT ";
        PreparedStatement preparedStatement =  connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Document> documents = new ArrayList<>(3000);
        Document route;

        MongoCollection<Document> docs = db.getCollection("seats");
        docs.deleteMany(new BasicDBObject());
        while (resultSet.next()) {
            route = new Document("route_id" , resultSet.getString(1)).append("station_id", resultSet.getInt(2))
                    .append("route_number" , resultSet.getInt(3)).append("date" , resultSet.getDate(4).toString()).append("carriage" , resultSet.getInt(6))
                    .append("row" , resultSet.getInt(7)).append("number", resultSet.getString(8)).append("type",resultSet.getString(5)).append("cost" , resultSet.getInt(9)).append("available" , resultSet.getInt(10));



            documents.add(route);

            if (documents.size() % 1000 == 0) {
                docs.insertMany(documents);
                documents.clear();
            }



        }
        if (documents.size() != 0 ){
            docs.insertMany(documents);
        }
        connection.close();
        System.out.println("Insert row number: " + docs.count());
    }




}
