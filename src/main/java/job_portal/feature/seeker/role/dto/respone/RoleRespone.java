package job_portal.feature.seeker.role.dto.respone;
import lombok.Builder;

@Builder
public record RoleRespone(
    String uuid,
    String alias,
    String name
) {
}