package job_portal.feature.seeker.auth.dto;
import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
    @NotBlank(message = "Email is required")
    String email
) {
}