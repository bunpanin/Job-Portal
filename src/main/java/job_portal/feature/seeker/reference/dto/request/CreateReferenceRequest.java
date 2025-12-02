package job_portal.feature.seeker.reference.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateReferenceRequest(
    @NotBlank(message = "name is required!")
    String name,
    @NotBlank(message = "position is required!")
    String position,
    @NotBlank(message = "company is required!")
    String company,
    @NotBlank(message = "phoneNumber is required!")
    String phoneNumber,
    @NotBlank(message = "email is required!")
    String email
) {
}
