package job_portal.feature.seeker.auth.dto.respone;
import lombok.Builder;

@Builder
public record JwtRespone(
    String tokenType, // Bearer
    String accessToken,
    String refreshToken
) {
}