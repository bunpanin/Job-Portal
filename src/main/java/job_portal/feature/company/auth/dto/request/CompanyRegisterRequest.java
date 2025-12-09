package job_portal.feature.company.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CompanyRegisterRequest(


        @NotBlank(message = "fullName is required!")
        @Size(max = 100, message = "fullName must be less than 100 characters")
        String fullName,
        @NotBlank(message = "Name is required!")
        @Size(max = 100, message = "companyName must be less than 100 characters")
        String companyName,
        @NotBlank(message = "Email is required!")
        @Email(message = "Email must be a valid address!")
        String email,
        @NotBlank(message = "Password is required!")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must be at least 8 characters and contain uppercase, lowercase, number, and special character"
        )
        String password,
        @NotBlank(message = "ConfirmedPassword is required!")
        String confirmedPassword
) {
}
