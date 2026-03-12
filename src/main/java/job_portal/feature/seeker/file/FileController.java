package job_portal.feature.seeker.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/seeker/profile")
@RequiredArgsConstructor
@Tag(name = "Seeker Profile")
public class FileController {

    private final FileService fileService;

    @PatchMapping(value = "/{uuid}", consumes =  MediaType.MULTIPART_FORM_DATA_VALUE)
    void upload(@PathVariable String uuid,@Valid @RequestPart MultipartFile file) throws IOException {
        fileService.upload(uuid, file);
    }

}
