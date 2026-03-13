package job_portal.feature.role.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateRoleRequest(
        @NotBlank(message = "Name is required!")
        String name
) {
}
