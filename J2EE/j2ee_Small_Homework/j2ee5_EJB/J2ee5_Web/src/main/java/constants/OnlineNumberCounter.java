package constants;

/**
 * @author Qiang
 * @since 16/12/2016
 */
public class OnlineNumberCounter {

    private static int loginNumber;
    private static int visitors;
    private static OnlineNumberCounter counter;

    static {
        loginNumber = 0;
        visitors = 0;
        counter = new OnlineNumberCounter();
    }

    private OnlineNumberCounter() {}

    public static OnlineNumberCounter getCounter() {
        return counter;
    }


    public synchronized void addLoginNum(){
        loginNumber++;
    }

    public synchronized void addVisitors() {
        visitors++;
    }

    public synchronized void delLoginNum(){
        loginNumber--;
    }

    public synchronized void delVisitors() {
        visitors--;
    }

    public synchronized void visitorToLogin(){
        visitors--;
        loginNumber++;
    }

    public synchronized void logOut(){
        visitors++;
        loginNumber--;
    }

    public int[] getOnlineStatus() {
        int[] result = new int[3];
        result[0] = loginNumber;
        result[1] = visitors;
        result[2] = loginNumber + visitors;
        return result;
    }

}
