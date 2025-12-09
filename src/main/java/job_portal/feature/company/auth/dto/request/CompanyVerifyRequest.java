package job_portal.feature.company.auth.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyVerifyRequest(
        @NotBlank(message = "Email is required!")
        @Email(message = "Email must be a valid address!")
        String email,
        @NotBlank(message = "verificationCode is required!")
        String verificationCode
) {
}
