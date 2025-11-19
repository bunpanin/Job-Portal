package job_portal.feature.seeker.auth.dto;
import lombok.Builder;

@Builder
public record RoleRespone(
    String uuid,
    String alias,
    String name
) {
}