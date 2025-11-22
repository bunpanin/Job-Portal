package job_portal.feature.seeker.workExperience.dto.request;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UpdateWorkExperienceRequest(
    String jobTittle,
    Integer jobLevel,
    Integer typeOfExperience,
    String companyName,
    String cityOrProvince,
    String country,
    LocalDate startDate,
    LocalDate endDate,
    String descriptionYourExperience
) {
    
}
