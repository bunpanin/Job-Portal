package job_portal.feature.seeker.education;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import job_portal.feature.seeker.education.dto.request.CreateEductionRequest;
import job_portal.feature.seeker.education.dto.request.UpdateEducationRequest;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SEEKER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        educationService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    EducationRespone updateEducationById(@PathVariable Integer id,@Valid @RequestBody UpdateEducationRequest updateEducationRequest){
        return educationService.updateById(id, updateEducationRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @PathVariable String uuid, @RequestBody CreateEductionRequest createEductionRequest){
        educationService.createNewEducation(uuid, createEductionRequest);
    }
}
