package job_portal.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.SeekerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    // private final Seeker seeker;
    private final SeekerRepository seekerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Username : {}", username);

        Seeker seeker = seekerRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException(username));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setSeeker(seeker);
        return customUserDetails;
    }



    
}
