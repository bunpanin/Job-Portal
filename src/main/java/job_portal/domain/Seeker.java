package job_portal.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seekers")
public class Seeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private String phoneNumber;
    private String gender;
    private LocalDate dob;

    private String address;
    private String cityOrProvince;
    private String country;

    private String githubAccount;
    private String linkedinAccount;
    private String portfolio;

    private String cvFile;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}