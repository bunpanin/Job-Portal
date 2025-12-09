package job_portal.domain.backend.company;

import jakarta.persistence.*;
import job_portal.domain.backend.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "companys")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,  nullable = false)
    private String uuid;

    private String profile;
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String slugCompanyName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phoneNumber;
    private String cityOrProvince;
    private String khanOrDistrict;
    private String sangKatOrCommune;
    private String village;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private LocalDateTime createdAt;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private Boolean isVerified;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
}
