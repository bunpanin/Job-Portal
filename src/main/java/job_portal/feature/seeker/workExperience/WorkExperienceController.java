package job_portal.feature.seeker.workExperience;
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
import job_portal.feature.seeker.workExperience.dto.request.CreateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.request.UpdateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/workExperience")
@RequiredArgsConstructor
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SEEKER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        workExperienceService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    WorkExperienceRespone updateWorkExperienceById(@PathVariable Integer id,@Valid @RequestBody UpdateWorkExperienceRequest updateWorkExperienceRequest){
        return workExperienceService.updateById(id, updateWorkExperienceRequest);
    }


    @PostMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@PathVariable String uuid, @Valid @RequestBody CreateWorkExperienceRequest createWorkExperienceRequest){
        workExperienceService.createNewExperience(uuid, createWorkExperienceRequest);
    }
}
