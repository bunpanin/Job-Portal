package job_portal.domain.backend;
import java.time.LocalDate;
import java.util.List;

import job_portal.domain.backend.company.Company;
import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class Role implements GrantedAuthority{
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

    @ManyToMany(mappedBy = "roles")
    private List<Seeker> seekers;

    @ManyToMany(mappedBy = "roles")
    private List<Company> companies;
    
    @Override
    public String getAuthority() {
        return "ROLE_" + name; // ROLE_ADMIN, ROLE_MANAGER
    }
}
