package job_portal.domain;
import jakarta.persistence.*;
//import job_portal.domain.backend.role.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "user_roles",
         uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"})
)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MANY UserRole → ONE User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // MANY UserRole → ONE Role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}