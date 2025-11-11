package job_portal.feature.seeker.auth;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import job_portal.domain.backend.seeker.EmailVerification;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.EmailRequest;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.VerifyRequest;
import job_portal.mapper.seeker.SeekerMapper;
import job_portal.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j 
public class SeekerServiceImpl implements SeekerService{

    private final SeekerRepository seekerRepository;
    private final SeekerMapper seekerMapper;

    // Mail Config
    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String myMail;


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

        // Step 2. Prepare to send mail
        String myHtml = String.format("""
            <h1>JobPortal - Email Verification</h1>
            <hr/>
            %s
            """, emailVerification.getVerificationCode()
        );

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Email Verification From JobPortal");
        helper.setTo(seeker.getEmail());
        helper.setFrom(myMail);
        helper.setText(myHtml,true);

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
        seeker.setUuid(UUID.randomUUID().toString());
        seeker.setCreatedAt(LocalDateTime.now());
        seeker.setIsVerified(false);
        seeker.setIsBloked(false);
        seeker.setIsAccountNonExpired(true);
        seeker.setIsAccountNonLocked(true);
        seeker.setIsCredentialsNonExpired(true);
        seeker.setIsDeleted(true);
        seeker.setRole("seeker");
        
        seekerRepository.save(seeker);

        EmailVerification emailVerification = new EmailVerification();
        emailVerification.setVerificationCode(RandomUtil.random6Digits());
        emailVerification.setExpiryTime(LocalTime.now().plusMinutes(1));
        emailVerification.setSeeker(seeker);

        emailVerificationRepository.save(emailVerification);

        // Step 2. Prepare to send mail
        String myHtml = String.format("""
            <h1>JobPortal - Email Verification</h1>
            <hr/>
            %s
            """, emailVerification.getVerificationCode()
        );

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Email Verification From JobPortal");
        helper.setTo(seeker.getEmail());
        helper.setFrom(myMail);
        helper.setText(myHtml,true);

        javaMailSender.send(mimeMessage);
    }

}



