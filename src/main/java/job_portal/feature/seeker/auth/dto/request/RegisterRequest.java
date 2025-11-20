package job_portal.feature.seeker.auth.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Name is required!")
    @Size(max = 100, message = "Name must be less than 100 charaters")
    String fullName,
    @NotBlank(message = "Email is required!")
    @Email(message = "Email must be a valid address!")
    String email,
    @NotBlank(message = "Password is required!")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must be at least 8 characters and contain uppercase, lowercase, number, and special character"
    )
    String password,
    @NotBlank(message = "ComfirmedPassword is required!")
    String comfirmedPassword
) {
}