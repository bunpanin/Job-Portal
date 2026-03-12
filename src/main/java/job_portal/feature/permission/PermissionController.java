package job_portal.feature.permission;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import job_portal.feature.permission.dto.request.CreatePermissionRequest;
import job_portal.feature.permission.dto.request.UpdatePermissionRequest;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permission")
@RequiredArgsConstructor
@Tag(name = "Permission")
public class PermissionController {

    private final PermissionService permissionService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAnyAuthority('ROLE_SEEKER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        permissionService.deleteById(id);
    }

//    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    void updatePermissionById(@PathVariable Integer id, @Valid @RequestBody UpdatePermissionRequest updatePermissionRequest){
//        achievementService.updateByAchievementId(id,updateAchievementRequest);
        permissionService.updatePermissionById(id, updatePermissionRequest);
    }


    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @PathVariable String uuid, @RequestBody CreatePermissionRequest createPermissionRequest){
        permissionService.createNewPermission(uuid, createPermissionRequest);
    }
}

