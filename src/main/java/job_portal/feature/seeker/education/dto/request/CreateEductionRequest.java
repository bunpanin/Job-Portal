package job_portal.feature.seeker.education.dto.request;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateEductionRequest(
    @NotBlank(message = "SchoolOrUniversity is required!")
    String schoolOrUniversity,
    @NotNull(message = "Degree is required!")
    Integer degree,
    @NotBlank(message = "Major is required!")
    String major,
    @NotBlank(message = "Country is required!")
    String country,
    @NotBlank(message = "CityOrProvince is required!")
    String cityOrProvince,
    @NotNull(message = "StarDate is required!")
    LocalDate startDate,
    @NotNull(message = "EndDate is required!")
    LocalDate endDate,
    @NotBlank(message = "EducationDetail is required!")
    String educationDetail
) {
    
}
