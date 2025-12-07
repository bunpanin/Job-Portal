package job_portal.feature.seeker.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadMultiple(List<MultipartFile> files) throws IOException;
    void upload(String uuid,MultipartFile file) throws IOException;
}
