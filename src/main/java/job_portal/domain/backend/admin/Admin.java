package job_portal.domain.backend.admin;

import jakarta.persistence.*;
import job_portal.domain.backend.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,  nullable = false)
    private String uuid;

    private String profile;
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;
    private Boolean isDeleted;


//    @OneToMany(mappedBy = "adminRoles",cascade = CascadeType.ALL)
//    private Set<UserRole> adminRoles = new HashSet<>();
}
