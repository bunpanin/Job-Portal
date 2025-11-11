package job_portal.util;
import java.util.Random;

public class RandomUtil {

    public static String random6Digits(){
        Random rmd = new Random();
        int number = rmd.nextInt(999999);
        return String.format("%06d", number);
    }
}
