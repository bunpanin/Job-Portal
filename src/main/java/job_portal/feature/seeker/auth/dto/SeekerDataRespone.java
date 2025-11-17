package job_portal.feature.seeker.auth.dto;
import lombok.Builder;

@Builder
public record SeekerDataRespone(
    JwtRespone KEY,
    DataRespone DATA
) {
}