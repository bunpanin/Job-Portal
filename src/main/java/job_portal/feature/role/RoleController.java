package job_portal.feature.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import job_portal.feature.role.dto.request.CreateRoleRequest;
import job_portal.feature.role.dto.request.UpdateRoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Role")

public class RoleController {

    private final RoleService roleService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete role with uuid-role")
    void deleteByUuid(@Valid @PathVariable String uuid){
        roleService.deleteByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/{uuid}")
    @Operation(summary = "Update role with uuid-role")
    void updateByUuid(@PathVariable String uuid, @Valid @RequestBody UpdateRoleRequest updateRoleRequest){
        roleService.updateByUuid(uuid,updateRoleRequest);
    }

    @Operation(summary = "Create new role")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @RequestBody CreateRoleRequest createRoleRequest){
        roleService.createNewRole(createRoleRequest);
    }
}
