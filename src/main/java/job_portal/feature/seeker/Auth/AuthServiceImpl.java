package job_portal.feature.seeker.Auth;
import jakarta.mail.MessagingException;
import job_portal.domain.Role;
import job_portal.domain.Seeker;
import job_portal.domain.User;
import job_portal.feature.EmailVerifycaionRepository;
import job_portal.feature.admin.Permission.dto.respone.PermissionRespone;
import job_portal.feature.admin.Role.dto.respone.RoleRespone;
import job_portal.feature.seeker.Auth.dto.request.LoginRequest;
import job_portal.feature.seeker.Auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.Auth.dto.request.UpdateRequest;
import job_portal.feature.seeker.Auth.dto.respone.DataRespone;
import job_portal.feature.seeker.Auth.dto.respone.JwtRespone;
import job_portal.feature.seeker.Auth.dto.respone.SeekerRespone;
import job_portal.feature.seeker.SeekerRepository;
import job_portal.feature.admin.Role.RoleRepository;
import job_portal.feature.user.UserRepository;
import job_portal.mapper.SeekerMapper;
import job_portal.security.JwtService;
import job_portal.util.GlobalUtil;
import job_portal.util.UserTypeEnum;
import job_portal.util.UuidUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.*;
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
    private final JwtService jwtService;
    private final SeekerMapper seekerMapper;
    private final TokenBlacklistService tokenBlacklistService;


    @Autowired
    @org.springframework.beans.factory.annotation.Qualifier("jwtEncoderRefreshToken")
    public void setJwtEncoderRefreshToken(JwtEncoder jwtEncoderRefreshToken) {
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
    }


    @Override
    public void logout(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String uuidSeeker = jwt.getClaim("uuidSeeker");
        tokenBlacklistService.blacklistUser(uuidSeeker);
    }

    @Override
    public void updateSeekerByUuid(UpdateRequest update) {
        String uuid = jwtService.extractUuid();
        User user = userRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found")
        );

        seekerMapper.updateUserFromDto(update, user);

        if (user.getSeeker() != null) {
            seekerMapper.updateSeekerFromDto(update, user.getSeeker());
        }
        userRepository.save(user);
    }

    @Override
    public SeekerRespone login(LoginRequest login) {

        User user = userRepository.findByEmail(login.email()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found")
        );
        Authentication auth;

        try {
            auth = new UsernamePasswordAuthenticationToken(login.email(), login.password());
            auth = authProvider.authenticate(auth);

        } catch (DisabledException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Account not verified. Please verify your email."
            );
        } catch (LockedException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Account is locked."
            );
        }

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

        List<RoleRespone> roles = user.getUserRoles().stream().map(
                r -> new RoleRespone(r.getRole().getName())
        ).toList();

        List<PermissionRespone> permissions = user.getUserRoles()
                .stream()
                .flatMap(role -> role.getRole().getRolePermissions().stream())
                .distinct()
                .map(permission -> new PermissionRespone(permission.getPermission().getName()))
                .toList();
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
                .roles(roles)
                .permissions(permissions)
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
        User user = User.builder()
            .uuid(UuidUtil.generateUuid(request.fullName()))
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .createdAt(LocalDateTime.now())
                .isVerified(false)
                .isBlocked(false)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isDeleted(false)
            .build();


        // 4. Get SEEKER role
        Role seekerRole = roleRepository.findByName(UserTypeEnum.SEEKER.toString())
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Role SEEKER not found"
            ));
        Role adminRole = roleRepository.findByName(UserTypeEnum.ADMIN.toString())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role ADMIN not found"
            ));

        GlobalUtil.addRoleToUser(user, seekerRole);
        GlobalUtil.addRoleToUser(user, adminRole);

        userRepository.save(user);

        Seeker seeker = Seeker.builder()
            .fullName(request.fullName())
            .user(user)
            .build();

        seekerRepository.save(seeker);
    }
}
