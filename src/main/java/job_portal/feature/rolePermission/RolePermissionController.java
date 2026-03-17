package job_portal.feature.rolePermission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import job_portal.feature.rolePermission.dto.request.CreateRolePermissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1/rolePermissions")
@RequestMapping("/api/v1/seeker/rolePermissions")
@RequiredArgsConstructor

@Tag(name = "Role Permission")
public class RolePermissionController {

    private final RolePermissionService service;
    @PostMapping
    @Operation(summary = "Create new Role Permission")
    @PreAuthorize("hasAuthority('admin')")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @RequestBody CreateRolePermissionRequest create){
        service.creatNewRolePermission(create);
    }
}
