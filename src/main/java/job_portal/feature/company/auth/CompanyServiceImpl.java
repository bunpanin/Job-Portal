package job_portal.feature.company.auth;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.company.Company;
import job_portal.domain.backend.seeker.EmailVerification;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.company.auth.dto.request.CompanyLoginRequest;
import job_portal.feature.company.auth.dto.request.CompanyRegisterRequest;
import job_portal.feature.company.auth.dto.request.CompanyVerifyRequest;
import job_portal.feature.seeker.auth.EmailVerificationRepository;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.role.RoleRepository;
import job_portal.mapper.company.AuthCompanyMapper;
import job_portal.util.MailHtmlUtil;
import job_portal.util.RandomUtil;
import job_portal.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final AuthCompanyMapper authCompanyMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;
    private final AuthenticationManager authProvider;

    @Override
    public void login(CompanyLoginRequest companyLoginRequest) {

        Authentication auth = new UsernamePasswordAuthenticationToken(companyLoginRequest.email(), companyLoginRequest.password());

        auth = authProvider.authenticate(auth);

        log.info("Authorities: {}", auth.getAuthorities());
        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("SCOPE: {}", scope);

        Instant now = Instant.now();

        Company company = companyRepository.findByEmail(companyLoginRequest.email()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!")
        );
        return;
    }

    @Override
    public void verify(CompanyVerifyRequest companyVerifyRequest){
        Company company = companyRepository
            .findByEmail(companyVerifyRequest.email())
            .orElseThrow(()->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email doesn't exist!"
            )
        );
        EmailVerification emailVerification = emailVerificationRepository
                .findByCompany(company)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Seeker doesn't exits!"
                        )
                );

        if(!emailVerification.getVerificationCode().equals(companyVerifyRequest.verificationCode())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Verification failed!"
            );
        }

        if(LocalTime.now().isAfter(emailVerification.getExpiryTime())){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Verification code is expired!"
            );
        }

        company.setIsVerified(true);
        company.setIsDeleted(false);

        companyRepository.save(company);
    }

    @Override
    public void register(CompanyRegisterRequest companyRegisterRequest)throws MessagingException {
        if(companyRepository.existsByEmail(companyRegisterRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists!");
        }
        if(!companyRegisterRequest.password().equals(companyRegisterRequest.confirmedPassword())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Passwords don't match!");
        }
        Company company = authCompanyMapper.fromRegisterRequest(companyRegisterRequest);
        company.setUuid(SlugUtil.toSlug(companyRegisterRequest.companyName()) + "-" + UUID.randomUUID().toString());
        company.setSlugCompanyName(SlugUtil.toSlug(companyRegisterRequest.companyName()));
        company.setCreatedAt(LocalDateTime.now());
        company.setIsVerified(false);
        company.setIsBlocked(false);
        company.setIsAccountNonExpired(true);
        company.setIsAccountNonLocked(true);

        company.setIsCredentialsNonExpired(true);
        company.setIsDeleted(true);
        company.setPassword(passwordEncoder.encode(company.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(3).orElseThrow());
        company.setRoles(roles);
        companyRepository.save(company);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        emailVerification.setCompany(company);

        emailVerificationRepository.save(emailVerification);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Email Verification - Job-Portal");
        helper.setTo(company.getEmail());
        helper.setText(MailHtmlUtil.buildVerificationEmail(emailVerification.getVerificationCode()), true);
        javaMailSender.send(mimeMessage);

    }
}
