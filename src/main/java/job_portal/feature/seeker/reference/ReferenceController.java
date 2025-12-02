package job_portal.feature.seeker.reference;
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
import job_portal.feature.seeker.reference.dto.request.CreateReferenceRequest;
import job_portal.feature.seeker.reference.dto.request.UpdateReferenceRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/reference")
@RequiredArgsConstructor
public class ReferenceController {

    private final ReferenceService referenceService;

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @ResponseStatus(HttpStatus.OK)
    void deleteById(@PathVariable Integer id){
        referenceService.deleteById(id);
    }


    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    void updateById(@PathVariable Integer id,@Valid @RequestBody UpdateReferenceRequest updateReferenceRequest){
        referenceService.updateById(id, updateReferenceRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{uuid}")
    void createNew(@PathVariable String uuid,@Valid @RequestBody CreateReferenceRequest createReferenceRequest){
        referenceService.createNewReference(uuid, createReferenceRequest);
    }
}
