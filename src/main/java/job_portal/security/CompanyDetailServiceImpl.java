package job_portal.security;

import job_portal.domain.backend.company.Company;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.company.auth.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyDetailServiceImpl implements UserDetailsService {
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Company : {}", username);

        Company company = companyRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Company Not Found!"));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setCompany(company);
        return customUserDetails;
    }
}
