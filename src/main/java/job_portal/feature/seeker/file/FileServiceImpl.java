package job_portal.feature.seeker.file;

import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Value("${file-server-seeker-path}")
    private String serverPath;

    @Value("${file-server-seeker-base-uri}")
    private String baseUrl;

    private final SeekerRepository seekerRepository;

    @Override
    public void uploadMultiple(List<MultipartFile> files) throws IOException {

    }

    @Override
    public void upload(String uuid, MultipartFile file) throws IOException {
        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found")
        );

        String newName = FileUtil.generateFileName(file.getOriginalFilename());
        String extension = FileUtil.extractExtention(file.getOriginalFilename());

        // Physical path (PC)
        Path newFilePath = Path.of(serverPath + newName);
        // serverPath = "D:/Years 4/SANA/File/Seeker/images/"

        //DELETE OLD FILE
        if (seeker.getProfile() != null && !seeker.getProfile().isBlank()) {

            // Extract old filename from URL
            String oldUrl = seeker.getProfile();
            // Example: /seeker/images/f067ac8d-65fe-4652-a73f-416980733f0c.jpg

            String oldFileName = oldUrl.substring(oldUrl.lastIndexOf("/") + 1);

            Path oldFilePath = Path.of(serverPath + oldFileName);

            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
            }
        }

        // Overwrite file
        Files.copy(
                file.getInputStream(),
                newFilePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        seeker.setProfile(baseUrl+newName);
        seekerRepository.save(seeker);
    }

}
