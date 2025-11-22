package job_portal.feature.seeker.education.dto.respone;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record EducationRespone(
    Integer id,
    String schoolOrUniversity,
    String degree,
    String major,
    String country,
    String cityOrProvince,
    LocalDate startDate,
    LocalDate endDate,
    String educationDetail
) {
    
}
