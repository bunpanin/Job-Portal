package job_portal.feature.seeker.reference.dto.respone;
import java.time.LocalDate;

public record ReferenceRespone(
    Integer id,
    String name,
    String position,
    String company,
    String phoneNumber,
    String email,
    LocalDate createdAt
) {
    
}
