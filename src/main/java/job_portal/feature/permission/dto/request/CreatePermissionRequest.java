package job_portal.feature.permission.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Builder
public record CreatePermissionRequest(
        @NotBlank(message = "Name is required!")
        String nane,
        @NotBlank(message = "Description is required!")
        String description,

        @NotBlank(message = "CreatedByAlias is required!")
        String createdByAlias
) {
}
