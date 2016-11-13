import mongo.MongoDBHelper;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author Qiang
 * @date 10/11/2016
 */
public class MongoTest {


    Mongo mongo;

    @Before
    public void setUp() throws Exception {
        mongo = new Mongo();

        //保证每次测试座位始终是可用的
        setAllSeatsAvailable();
    }
    @Test
    public void checkIfAvailable() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
        mongo.checkIfAvailable("广州南" , "深圳北" , new Date(calendar.getTimeInMillis()), false);
        mongo.buyTicket("G79" , "广州南" , "深圳北" , "2016-11-10"  , "一等座", 2);
        System.out.println("++++++++++++++++++++++  买票后 ++++++++++++++++++++++++++++++++++++++++");
        mongo.checkIfAvailable("广州南" , "深圳北" , new Date(calendar.getTimeInMillis()), false);
    }


    @Test
    public void buyTicketTest() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,10,10);
        mongo.buyTicket("G101" ,"北京南" , "济南西" , "2016-11-09"  , "一等座" , 38);
        mongo.buyTicket("G101" ,"北京南" , "济南西" , "2016-11-09"  , "一等座" , 4);
//        mongo.checkIfAvailable("北京南" , "济南西" , new Date(calendar.getTimeInMillis()), false);
    }


    private void setAllSeatsAvailable( ) throws  Exception {

        mongo.setAllSeatsAvailable();


    }

}