package job_portal.domain.backend;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import job_portal.domain.backend.company.Company;
import job_portal.domain.backend.seeker.Seeker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "roles")
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String uuid;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String alias;

    private Boolean isDeleted;
    private LocalDate createdAt;

//    @ManyToMany(mappedBy = "roles")
//    private List<Seeker> seekers;
//
//    @ManyToMany(mappedBy = "roles")
//    private List<Company> companies;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    // private List<Permission> permissions;
    private Set<Permission> permissions;

//    @Override
//    public String getAuthority() {
//        return "ROLE_" + name; // ROLE_ADMIN, ROLE_MANAGER
//    }
}
