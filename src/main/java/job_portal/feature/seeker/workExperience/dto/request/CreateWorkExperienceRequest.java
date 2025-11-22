package job_portal.feature.seeker.workExperience.dto.request;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateWorkExperienceRequest(

    @NotBlank(message = "JobTitle is required!")
    @Size(max =  150, message = "JobTitle must be less than 100 charaters")
    String jobTittle,

    @NotNull(message = "JobLevel is required!")
    Integer jobLevel,

    Integer typeOfExperience,
    @NotBlank(message = "CityOrProvince is required!")

    @NotBlank(message = "Company is required!")
    String companyName,

    @NotNull(message = "TypeOfExperience is required!")
    String cityOrProvince,

    @NotBlank(message = "Country is required!")
    String country,

    @NotNull(message = "StartDate is required!")
    LocalDate startDate,
    @NotNull(message = "EndDate is required!")
    LocalDate endDate,
    @NotBlank(message = "DescriptionYourExperience is required!")
    @Column(columnDefinition = "TEXT")
    String descriptionYourExperience
) {
}