package job_portal.util;
import java.util.UUID;

public class FileUtil {

    public static String extractExtention(String OriginalFileName){
        int lastDotIndex = OriginalFileName.lastIndexOf(".");
        return OriginalFileName.substring(lastDotIndex + 1);
    }
    public static String generateFileName(String OriginalFileName){
        String newName = UUID.randomUUID().toString();
        String extention = extractExtention(OriginalFileName);
        return String.format("%s.%s", newName,extention);
    }
}
