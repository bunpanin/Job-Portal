package job_portal.security;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Service
public class JwtService {

        public Long extractUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return null;

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getClaim("userId");
    }

    public String extractEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) return null;

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof Jwt jwt)) {
            return null;
        }

        return jwt.getClaim("email"); // or your claim name
    }
    
}
