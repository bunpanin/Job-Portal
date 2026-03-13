package job_portal.feature.role.dto.request;

import lombok.Builder;

@Builder
public record UpdateRoleRequest(
        String name
) {
}
