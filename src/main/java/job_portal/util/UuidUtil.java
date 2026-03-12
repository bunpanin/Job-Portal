package job_portal.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UuidUtil {
    public  static  String generateUuid(String name){
        String slugName = name.toLowerCase().replace(" ", "-");
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        String shortUuid = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
       return "%s-%s-%s".formatted(slugName, dateTime, shortUuid);
    }
}
