package job_portal.feature.permission.dto.request;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UpdatePermissionRequest(
        String name,
        String description
) {
}
