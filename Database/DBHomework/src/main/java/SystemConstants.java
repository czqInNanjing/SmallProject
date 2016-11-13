/**
 * Define some system constants
 * @author Qiang
 * @date 08/11/2016
 */
public class SystemConstants {

    public static final int carriagesNumber = 8;
    public static final int[] carriageSpilt = {3, 5, 9};  // 1 business seats , 2-3 first class seats
    public static final String[] carriageName = {"商务座" , "一等座" , "二等座" , "无座"};
    public static final int[] seatCost = {800, 400, 100, 100};
    public static final int seatScope = 200; //票价最多上浮200
    public static final int rowNum = 4;
    public static final char[] seatNumber = {'A' , 'B' , 'C' , 'E' , 'F'};

    public static final int intervalLeast = 1;
    public static final int intervalLargest = 6;
    public static final int restLeast = 120;
    public static final int restLargest = 1200;

    public static final int planDays = 7;
    public static final int noSeatNumEachCarriage = 20;

    public enum type { NO_SEAT("无座"), BUSINESS_SEAT("商务座"), FIRST_CLASS("一等座"), SECOND_CLASS("二等座");
        String name;
        type(String name){
            this.name = name;
        }
    };

}
