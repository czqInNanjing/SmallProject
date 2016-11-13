import mongo.MongoDBHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Qiang
 * @date 10/11/2016
 */
public class MongoDBHelperTest {



    MongoDBHelper db;

    @Before
    public void setUp() throws Exception {
        db = new MongoDBHelper();
    }


    @Test
    public void insertRoute() throws Exception {
        db.insertRoute();
    }

    @Test
    public void insertUser() throws Exception {
        db.insertUser();
    }

    @Test
    public void insertSeat() throws Exception {
        db.insertSeat();
    }
}