package job_portal.domain.backend.seeker;

import java.time.LocalTime;

import jakarta.persistence.*;
import job_portal.domain.backend.company.Company;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "email_verifications")
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Seeker seeker;

    @OneToOne
    private Company company;
//    @OneToOne(optional = true)
//    @JoinColumn(name = "seeker_id")
//    private Seeker seeker;
//
//    @OneToOne(optional = true)
//    @JoinColumn(name = "company_id")
//    private Company company;

    @Column(nullable = false)
    private LocalTime expiryTime;

    @Column(nullable = false, length = 6)
    private String verificationCode;
}