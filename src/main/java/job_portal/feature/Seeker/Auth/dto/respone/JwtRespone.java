package job_portal.feature.Seeker.Auth.dto.respone;

import lombok.Builder;

@Builder
public record JwtRespone(
        String tokenType, // Bearer
        String accessToken,
        String refreshToken
) {
}