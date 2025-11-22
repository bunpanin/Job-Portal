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
import job_portal.domain.backend.seeker.Degree;
import job_portal.domain.backend.seeker.Education;
import job_portal.domain.backend.seeker.EmailVerification;
import job_portal.domain.backend.seeker.JobLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.domain.backend.seeker.SeekerEducation;
import job_portal.domain.backend.seeker.SeekerWorkExperience;
import job_portal.domain.backend.seeker.TypeOfExperience;
import job_portal.domain.backend.seeker.WorkExperience;
import job_portal.feature.seeker.auth.dto.request.EmailRequest;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.VerifyRequest;
import job_portal.feature.seeker.auth.dto.respone.DataRespone;
import job_portal.feature.seeker.auth.dto.respone.JwtRespone;
import job_portal.feature.seeker.auth.dto.respone.LoginRequest;
import job_portal.feature.seeker.auth.dto.respone.SeekerDataRespone;
import job_portal.feature.seeker.degree.DegreeRepository;
import job_portal.feature.seeker.education.EducationRepository;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;
import job_portal.feature.seeker.jobLevel.JobLevelRepository;
import job_portal.feature.seeker.role.RoleRepository;
import job_portal.feature.seeker.role.dto.respone.RoleRespone;
import job_portal.feature.seeker.typeOfExperience.TypeOfExperienceRepository;
import job_portal.feature.seeker.workExperience.SeekerWorkExperienceRepository;
import job_portal.feature.seeker.workExperience.WorkExperienceRepository;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;
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
    private final TypeOfExperienceRepository typeOfExperienceRepository;
    private final SeekerWorkExperienceRepository seekerWorkExperienceRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final DegreeRepository degreeRepository;
    private final EducationRepository educationRepository;

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

    // @Override
    // public SeekerDataRespone updateByUuid(String uuid, SeekerUpdateRequest seekerUpdateRequest) {
    //     Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(()-> 
    //         new ResponseStatusException(
    //             HttpStatus.NOT_FOUND, 
    //             "Seeker not found!"
    //         ));
             
    //     JobLevel jobLevel = null;
    //     if(seekerUpdateRequest.jobLevel() != null){
    //         jobLevel = jobLevelRepository.findById(seekerUpdateRequest.jobLevel())
    //             .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "JobLevel not found!"));
    //     }
    //     seeker.setJobLevel(jobLevel);
    //     seekerMapper.fromSeekerUpdateRequest(seekerUpdateRequest, seeker);
    //     seeker = seekerRepository.save(seeker);

    //     // ======================== Update Work Experience =============================
        
    //     if(seekerUpdateRequest.workExperienceRequests() != null){
    //         for(WorkExperienceRequest w : seekerUpdateRequest.workExperienceRequests()){

    //             JobLevel jobLevelExp = jobLevelRepository.findById(w.jobLevel())
    //                 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "JobLevel not found!")
    //             );

    //             TypeOfExperience typeExp = typeOfExperienceRepository.findById(w.typeOfExperience())
    //                 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "TypeOfExperience not found!"));

    //             WorkExperience exp = new WorkExperience();
    //             exp.setJobTittle(w.jobTitle());
    //             exp.setJobLevel(jobLevelExp);
    //             exp.setCompanyName(w.company());
    //             exp.setTypeOfExperience(typeExp);
    //             exp.setCityOrProvince(w.cityOrProvince());
    //             exp.setCountry(w.country());
    //             exp.setStartDate(w.startDate());
    //             exp.setEndDate(w.endDate());
    //             exp.setDescriptionsYourExperience(w.descriptionYourExperience());
    //             /// save workExperience first
    //             exp = workExperienceRepository.save(exp); 
    //             SeekerWorkExperience seekerWorkExp = new SeekerWorkExperience();
    //             seekerWorkExp.setSeeker(seeker);
    //             seekerWorkExp.setWorkExperience(exp);
    //             seekerWorkExp.setCreatedAt(LocalDate.now());

    //             seekerWorkExperienceRepository.save(seekerWorkExp);
    //         }
    //     }
        
    
    // List<WorkExperienceRespone> workExperienceRespones = seeker.getSeekerWorkExperiences()
    // .stream()
    // .map(exp -> new WorkExperienceRespone(
    //         exp.getWorkExperience().getId(),
    //         exp.getWorkExperience().getJobTittle(),
    //         exp.getWorkExperience().getJobLevel().getName(),
    //         exp.getWorkExperience().getCompanyName(),
    //         exp.getWorkExperience().getTypeOfExperience().getName(),
    //         exp.getWorkExperience().getCityOrProvince(),
    //         exp.getWorkExperience().getCountry(),
    //         exp.getWorkExperience().getStartDate(),
    //         exp.getWorkExperience().getEndDate(),
    //         exp.getWorkExperience().getDescriptionsYourExperience()
    // ))
    // .toList();

    //     DataRespone data = DataRespone.builder()
    //         .jobLevel(jobLevel != null ? jobLevel.getName() : null)
    //         .uuid(seeker.getUuid())
    //         .email(seeker.getEmail())
    //         .phoneNumber(seeker.getPhoneNumber())
    //         .password(seeker.getPhoneNumber())
    //         .gender(seeker.getGender())
    //         .profile(seeker.getProfile())
    //         .workExperienceRequests(workExperienceRespones)
    //         .address(seeker.getAddress())
    //         .cityOrProvince(seeker.getCityOrProvince())
    //         .country(seeker.getCountry())
    //         .githubAccount(seeker.getGithubAccount())
    //         .linkInAccount(seeker.getLinkAccount())
    //         .portfolio(seeker.getPortfolio())
    //         .uuid(seeker.getUuid())
    //     .build();
    //     return SeekerDataRespone.builder()
    //             .DATA(data)
    //             .build();
    //     }

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

        List<RoleRespone> roleRespones = seeker.getRoles()
            .stream()
            .map(role -> new RoleRespone(
                role.getUuid(),
                role.getAlias(),
                role.getName()
                )
            )
            .toList();

        JobLevel jobLevel = null;
        if(seeker.getJobLevel() != null && seeker.getJobLevel().getId() != null){
            jobLevel = jobLevelRepository.findById(seeker.getJobLevel().getId()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"JobLevel not found!")
            );
        }
        seeker.setJobLevel(jobLevel);

        // ====================================== Education ============================================
        List<EducationRespone> educationRespones = new ArrayList<>();
        if (seeker.getSeekerEducations() != null) {

            for (SeekerEducation seekerData : seeker.getSeekerEducations()) {

                Education education = seekerData.getEducation();
                Degree degree = education.getDegree();

                educationRespones.add(
                    EducationRespone.builder()
                        .id(education.getId())
                        .schoolOrUniversity(education.getSchoolOrUniversity())
                        .degree(degree.getName())
                        .major(education.getMajor())
                        .startDate(education.getStartDate())
                        .endDate(education.getEndDate())
                        .country(education.getCountry())
                        .cityOrProvince(education.getCityOrProvince())
                        .educationDetail(education.getEducationDetail())
                        .build()
                );
            }
        }
        // ====================================== Experience ============================================
        List<WorkExperienceRespone> workExperienceRespones = new ArrayList<>();
        if(seeker.getSeekerWorkExperiences() != null){
            for(SeekerWorkExperience expData : seeker.getSeekerWorkExperiences()){

                WorkExperience workExperience = expData.getWorkExperience();
                JobLevel jobLevelExp = workExperience.getJobLevel();
                TypeOfExperience typeOfExperience = workExperience.getTypeOfExperience();
                workExperienceRespones.add(
                    WorkExperienceRespone.builder()
                    .id(workExperience.getId())
                    .jobTitle(workExperience.getJobTittle())
                    .jobLevel(jobLevelExp.getName())
                    .company(workExperience.getCompanyName())
                    .typeOfExperience(typeOfExperience.getName())
                    .cityOrProvince(workExperience.getCityOrProvince())
                    .country(workExperience.getCountry())
                    .startDate(workExperience.getStartDate())
                    .endDate(workExperience.getEndDate())
                    .descriptionYourExperience(workExperience.getDescriptionYourExperience())
                    .build()
                );

            }
        }


        DataRespone data = DataRespone.builder()
            // Normal Data
            .jobLevel(jobLevel != null ? jobLevel.getName() : null)
            .uuid(seeker.getUuid())
            .email(seeker.getEmail())
            .phoneNumber(seeker.getPhoneNumber())
            .fullName(seeker.getFullName())
            .password(seeker.getPassword())
            .gender(seeker.getGender())
            .profile(seeker.getProfile())
            .roles(roleRespones)
            .dob(seeker.getDob())
            .address(seeker.getAddress())
            .cityOrProvince(seeker.getCityOrProvince())
            .country(seeker.getCountry())
            .githubAccount(seeker.getGithubAccount())
            .linkInAccount(seeker.getLinkAccount())
            .portfolio(seeker.getPortfolio())
            .createdAt(seeker.getCreatedAt())
            // Data for cv
            .educationRespones(educationRespones)
            .workExperienceRespones(workExperienceRespones)
            /// Security
            .isVerified(seeker.getIsVerified())
            .isBloked(seeker.getIsBloked())
            .isAccountNonExpired(seeker.getIsAccountNonExpired())
            .isAccountNonLocked(seeker.getIsAccountNonLocked())
            .isCredentialsNonExpired(seeker.getIsCredentialsNonExpired())
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
        roles.add(roleRepository.findById(2).orElseThrow());
        roles.add(roleRepository.findById(3).orElseThrow());
        roles.add(roleRepository.findById(4).orElseThrow());
        seeker.setRoles(roles);
        seekerRepository.save(seeker);

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



