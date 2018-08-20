package support;

/**
 * Created by aleksandr.kot on 8/14/18.
 */
public class main {
    public static void main(String[] args) {
        Time time = new Time("12h");
        System.out.println(time.getHours() + ":" + time.getMinutes());
    }
}
