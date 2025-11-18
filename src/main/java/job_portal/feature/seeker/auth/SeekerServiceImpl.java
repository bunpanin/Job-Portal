package job_portal.feature.seeker.auth;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import job_portal.domain.backend.Role;
import job_portal.domain.backend.RoleRepository;
import job_portal.domain.backend.seeker.EmailVerification;
import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.DataRespone;
import job_portal.feature.seeker.auth.dto.EmailRequest;
import job_portal.feature.seeker.auth.dto.JwtRespone;
import job_portal.feature.seeker.auth.dto.LoginRequest;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.SeekerDataRespone;
import job_portal.feature.seeker.auth.dto.SeekerUpdateRequest;
import job_portal.feature.seeker.auth.dto.VerifyRequest;
import job_portal.mapper.seeker.SeekerMapper;
import job_portal.util.MailHtmlUtil;
import job_portal.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.Jwt;

@Service
@RequiredArgsConstructor
@Slf4j 
public class SeekerServiceImpl implements SeekerService {

    private final SeekerRepository seekerRepository;
    private final SeekerMapper seekerMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JobLevelRepository jobLevelRepository;
    private final AuthenticationManager authProvider;

    // Mail Config
    private final EmailVerificationRepository emailVerificationRepository;

    private final JavaMailSender javaMailSender;
    private final JwtEncoder jwtEncoder;
    private JwtEncoder jwtEncoderRefreshToken;

    private final String TOKEN_TYPE = "Bearer";

    @Value("${spring.mail.username}")
    private String myMail;

    @Autowired
    @org.springframework.beans.factory.annotation.Qualifier("jwtEncoderRefreshToken")
    public void setJwtEncoderRefreshToken(JwtEncoder jwtEncoderRefreshToken) {
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
    }

    @Override
    public SeekerDataRespone updateByUuid(String uuid, SeekerUpdateRequest seekerUpdateRequest) {
        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(()-> 
            new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Seeker not found!"
            ));
        
        JobLevel jobLevel = null;
        if(seekerUpdateRequest.jobLevel() != null){
            jobLevel = jobLevelRepository.findById(seekerUpdateRequest.jobLevel())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "JobLevel not found!"));
        }
        log.info("reweffdfbf : ",jobLevel);
        seeker.setJobLevel(jobLevel);
        seekerMapper.fromSeekerUpdateRequest(seekerUpdateRequest, seeker);
        seeker = seekerRepository.save(seeker);

        // DataRespone data = seekerMapper.toDataRespone(seeker);
        DataRespone data = DataRespone.builder()
          .jobLevel(jobLevel != null ? jobLevel.getName() : null)
            .uuid(seeker.getUuid())
            .email(seeker.getEmail())
            .phoneNumber(seeker.getPhoneNumber())
            .password(seeker.getPhoneNumber())
            .gender(seeker.getGender())
            .profile(seeker.getProfile())
            // .dob(seeker.getDob().toString())
            .address(seeker.getAddress())
            .cityOrProvince(seeker.getCityOrProvince())
            .country(seeker.getCountry())
            .githubAccount(seeker.getGithubAccount())
            .linkInAccount(seeker.getLinkAccount())
            .portfolio(seeker.getPortfolio())
            .jobLevel(jobLevel.getName())
            .uuid(seeker.getUuid())
        .build();

        
        

        seekerRepository.save(seeker);

        return SeekerDataRespone.builder()
                .DATA(data)
                .build();
        }

    @Override
    public SeekerDataRespone login(LoginRequest loginRequest) {

        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        auth = authProvider.authenticate(auth);

        log.info("Authorities: {}", auth.getAuthorities());
           String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("SCOPE: {}", scope);

        Instant now = Instant.now();

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
            .id(auth.getName())
            .issuedAt(now)
            .issuer("web")
            .audience(List.of("nextjs", "reactjs"))
            .subject("Access Token")
            .expiresAt(now.plus(30, ChronoUnit.MINUTES))
            .claim("scope", scope)
            .build();

        // Create refresh token claims set
        JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
            .id(auth.getName())
            .issuedAt(now)
            .issuer("web")
            .audience(List.of("nextjs", "reactjs"))
            .subject("Refresh Token")
            .expiresAt(now.plus(7, ChronoUnit.DAYS))
            .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt jwt = jwtEncoder.encode(jwtEncoderParameters);

        JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(jwtClaimsSetRefreshToken);
        Jwt jwtRefreshToken = jwtEncoderRefreshToken.encode(jwtEncoderParametersRefreshToken);

        String accessToken = jwt.getTokenValue();
        String refreshToken = jwtRefreshToken.getTokenValue();

        // JWT Respone
        JwtRespone jwtRespone = JwtRespone.builder()
            .tokenType(TOKEN_TYPE)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        
        Seeker seeker = seekerRepository.findByEmail(loginRequest.email())
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Seeker not found!"));

        JobLevel jobLevel = null;
        if(seeker.getJobLevel() != null && seeker.getJobLevel().getId() != null){
            jobLevel = jobLevelRepository.findById(seeker.getJobLevel().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"JobLevel not found!")
            );
        }
        // Set jobLevel only if exists
        seeker.setJobLevel(jobLevel);

        // seeker.setJobLevel(seeker.getJobLevel() != null ? seeker.getJobLevel().getName() : null);
        // seeker.setJobLevel(jobLevel != null ? jobLevel : null);

        // DataRespone data = seekerMapper.toDataRespone(seeker);
        DataRespone data = DataRespone.builder()
            .jobLevel(jobLevel != null ? jobLevel.getName() : null)
            .uuid(seeker.getUuid())
            .email(seeker.getEmail())
            .phoneNumber(seeker.getPhoneNumber())
            .password(seeker.getPhoneNumber())
            .gender(seeker.getGender())
            .profile(seeker.getProfile())
            // .dob(seeker.getDob().toString())
            .address(seeker.getAddress())
            .cityOrProvince(seeker.getCityOrProvince())
            .country(seeker.getCountry())
            .githubAccount(seeker.getGithubAccount())
            .linkInAccount(seeker.getLinkAccount())
            .portfolio(seeker.getPortfolio())
        .build();
        return SeekerDataRespone.builder()
            .KEY(jwtRespone)
            .DATA(data)
            .build();
    }

    @Override
    public void resentVerify(EmailRequest emailRequest) throws MessagingException{

        Seeker seeker = seekerRepository
            .findByEmail(emailRequest.email())
            .orElseThrow(()->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email doesn't exist!"
                )
            );

        if(Boolean.TRUE.equals(seeker.getIsVerified())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Account already verified!"
            );
        }

        EmailVerification emailVerification = emailVerificationRepository
            .findBySeeker(seeker)
            .orElseThrow(()->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Seeker doesn't exits!"
                )
            );
        
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));

        emailVerificationRepository.save(emailVerification);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Resent Email Verification From JobPortal");
        helper.setTo(seeker.getEmail());
        helper.setFrom(myMail);
        helper.setText(MailHtmlUtil.buildVerificationEmail(emailVerification.getVerificationCode()),true);

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void verify(VerifyRequest verifyRequest) {

        Seeker seeker = seekerRepository
            .findByEmail(verifyRequest.email())
            .orElseThrow(()->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email doesn't exist!"
                )
            );
        
        EmailVerification emailVerification = emailVerificationRepository
            .findBySeeker(seeker)
            .orElseThrow(()->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Seeker doesn't exits!"
                )
            );

        if(!emailVerification.getVerificationCode().equals(verifyRequest.vertificationCode())){
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

        seeker.setIsVerified(true);
        seeker.setIsDeleted(false);

        seekerRepository.save(seeker);
    }


    @Override
    public void register(RegisterRequest registerRequest) throws MessagingException {

        if(seekerRepository.existsByEmail(registerRequest.email())){
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Email already exists"
            );
        }

        if(!registerRequest.password().equals(registerRequest.comfirmedPassword())){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Passwords do not match"
            );
        }

        Seeker seeker = seekerMapper.fromRegisterRequest(registerRequest);
        seeker.setUuid(registerRequest.fullName().toLowerCase() + "-" + UUID.randomUUID().toString());
        // seeker.setUuid(registerRequest.fullName() + "-" + shortUuid);
        seeker.setCreatedAt(LocalDateTime.now());
        seeker.setIsVerified(false);
        seeker.setIsBloked(false);
        seeker.setIsAccountNonExpired(true);
        seeker.setIsAccountNonLocked(true);
        seeker.setIsCredentialsNonExpired(true);
        seeker.setIsDeleted(true);
        seeker.setPassword(passwordEncoder.encode(seeker.getPassword()));
        
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(1).orElseThrow());
        seeker.setRoles(roles);
        seekerRepository.save(seeker);

        // EmailVerification emailVerification = new EmailVerification();
        // emailVerification.setVerificationCode(RandomUtil.random6Digits());
        // emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        // emailVerification.setSeeker(seeker);

        // emailVerificationRepository.save(emailVerification);

        // String myHtml = String.format("""
        //     <h1>JobPortal - Email Verification</h1>
        //     <hr/>
        //     %s
        //     """, emailVerification.getVerificationCode()
        // );

        // MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        // helper.setSubject("Email Verification From JobPortal");
        // helper.setTo(seeker.getEmail());
        // helper.setFrom(myMail);
        // helper.setText(myHtml,true);

        // javaMailSender.send(mimeMessage);
        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        emailVerification.setSeeker(seeker);

        emailVerificationRepository.save(emailVerification);
        
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Email Verification - MBanking");
        helper.setTo(seeker.getEmail());
        // helper.setFrom(myMail);
        helper.setText(MailHtmlUtil.buildVerificationEmail(emailVerification.getVerificationCode()), true);
        javaMailSender.send(mimeMessage);
    }
}



