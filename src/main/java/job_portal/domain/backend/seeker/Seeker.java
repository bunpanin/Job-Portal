package job_portal.domain.backend.seeker;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "seekers")
public class Seeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(nullable = false, length = 50)
    private String fullName;
    @Column(nullable = false, unique = true)
    private String email;
    // @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    
    private String gender;

    private String profile;
    private LocalDate dob;

    @ManyToOne
    private JobLevel jobLevel;

    private String address;
    private String cityOrProvince;
    private String country;
    private String githubAccount;
    private String linkAccount;
    private String portfolio;

    @OneToMany(mappedBy = "seeker")
    private List<SeekerWorkExperience> seekerWorkExperiences;

    private String education;
    private String achievement;
    private String skill;
    private String language;
    private String reference;

    @Column(columnDefinition = "TEXT")
    private String descriptionYourSelf;
    private LocalDateTime createdAt;

    private Boolean isVerified;
    private Boolean isBloked;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isDeleted;
    // can i use  role "seeker"
    private String role;
}