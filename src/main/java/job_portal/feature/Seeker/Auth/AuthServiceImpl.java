package job_portal.feature.Seeker.Auth;
import jakarta.mail.MessagingException;
import job_portal.domain.Role;
import job_portal.domain.Seeker;
import job_portal.domain.User;
import job_portal.domain.UserRole;
import job_portal.feature.EmailVerifycaionRepository;
import job_portal.feature.Seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.Seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.Seeker.Auth.dto.respone.DataRespone;
import job_portal.feature.Seeker.Auth.dto.respone.JwtRespone;
import job_portal.feature.Seeker.Auth.dto.respone.SeekerRespone;
import job_portal.feature.Seeker.SeekerRepository;
import job_portal.feature.admin.Role.RoleRepository;
import job_portal.feature.user.UserRepository;
import job_portal.util.UserTypeEnum;
import job_portal.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final SeekerRepository seekerRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final EmailVerifycaionRepository emailVerifycaionRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authProvider;
    private final JavaMailSender javaMailSender;
    private final JwtEncoder jwtEncoder;
    private JwtEncoder jwtEncoderRefreshToken;
    private final String TOKEN_TYPE = "Bearer";


    @Autowired
    @org.springframework.beans.factory.annotation.Qualifier("jwtEncoderRefreshToken")
    public void setJwtEncoderRefreshToken(JwtEncoder jwtEncoderRefreshToken) {
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
    }


    @Override
    public SeekerRespone login(LoginRequest login) {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        auth = authProvider.authenticate(auth);

        log.info("Authorities: {}", auth.getAuthorities());
        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        log.info("SCOPE: {}", scope);

        Instant now = Instant.now();
        User user = userRepository.findByEmail(login.email()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .issuedAt(now)
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .claim("uuidSeeker", user.getUuid())
                .claim("emailSeeker", user.getEmail())
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

        DataRespone data = DataRespone.builder()
                .uuid(user.getUuid())
                .fullName(user.getSeeker().getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getSeeker().getPhoneNumber())
                .password(user.getPassword())
                .gender(user.getSeeker().getGender())
                .dob(user.getSeeker().getDob())
                .address(user.getSeeker().getAddress())
                .cityOrProvince(user.getSeeker().getCityOrProvince())
                .country(user.getSeeker().getCountry())
                .githubAccount(user.getSeeker().getGithubAccount())
                .linkInAccount(user.getSeeker().getLinkedinAccount())
                .portfolio(user.getSeeker().getPortfolio())
                .cvFile(user.getSeeker().getCvFile())
                .createdAt(user.getCreatedAt())
                .isVerified(user.getIsVerified())
                .isBlocked(user.getIsBlocked())
                .isAccountNonExpired(user.getIsAccountNonExpired())
                .isAccountNonLocked(user.getIsAccountNonLocked())
                .isCredentialsNonExpired(user.getIsCredentialsNonExpired())
                .build();

        return SeekerRespone.builder()
                .KEY(jwtRespone)
                .DATA(data)
                .build();

    }

    @Override
    public void register(RegisterRequest request) throws MessagingException {
        // 1. Validate email
        if (userRepository.existsByEmail((request.email()))) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }
        // 2. Validate password
        if (!request.password().equals(request.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Passwords do not match"
            );
        }
        // 3. Create User
        User user = new User();
        user.setUuid(UuidUtil.generateUuid(request.fullName()));
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());

        // 4. Get SEEKER role
        Role seekerRole = roleRepository.findByName(UserTypeEnum.SEEKER.toString())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Role SEEKER not found"
            ));

        // 5. Create UserRole
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(seekerRole);
        user.getUserRoles().add(userRole);
        // 6. Save User (cascade will save UserRole)
        userRepository.save(user);

        // 7. Create Seeker profile
        Seeker seeker = new Seeker();
        seeker.setFullName(request.fullName());
        seeker.setUser(user);// optional

        seekerRepository.save(seeker);
    }
}
