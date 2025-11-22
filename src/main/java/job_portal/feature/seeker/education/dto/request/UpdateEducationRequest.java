package job_portal.feature.seeker.education.dto.request;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record UpdateEducationRequest(
    String schoolOrUniversity,
    Integer degree,
    String major,
    String country,
    String cityOrProvince,
    LocalDate startDate,
    LocalDate endDate,
    String educationDetail
) {

}
