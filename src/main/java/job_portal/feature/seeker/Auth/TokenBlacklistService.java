package job_portal.feature.seeker.Auth;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // key = uuidSeeker, value = time we blacklisted them
    private final ConcurrentHashMap<String, Instant> blacklist = new ConcurrentHashMap<>();

    public void blacklistUser(String uuidSeeker) {
        blacklist.put(uuidSeeker, Instant.now());
    }

    public boolean isBlacklisted(String uuidSeeker, Instant tokenIssuedAt) {
        Instant blacklistedAt = blacklist.get(uuidSeeker);
        if (blacklistedAt == null) return false;
        // block if token was issued BEFORE the logout happened
        return tokenIssuedAt.isBefore(blacklistedAt);
    }
}
