package job_portal.feature.role;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import job_portal.feature.role.dto.request.CreateRoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@Tag(name = "Role")

public class RoleController {

    private final RoleService roleService;
    @Operation(summary = "Create new role")
    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @PathVariable String uuid, @RequestBody CreateRoleRequest createRoleRequest){
        roleService.createNewRole(uuid, createRoleRequest);
    }
}
