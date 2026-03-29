package job_portal.feature.seeker.Auth.dto.respone;
import lombok.Builder;

@Builder
public record SeekerRespone(
    JwtRespone KEY,
    DataRespone DATA
) {
}
