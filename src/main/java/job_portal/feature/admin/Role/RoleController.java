package job_portal.feature.admin.Role;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/adnin/roles")
@RequiredArgsConstructor
@Tag(name = "Role")
public class RoleController {

}
