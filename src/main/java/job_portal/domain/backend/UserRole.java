package job_portal.domain.backend;

import jakarta.persistence.*;
import job_portal.domain.backend.admin.Admin;
import job_portal.domain.backend.seeker.Seeker;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "seeker_id")
    private Seeker seeker;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private LocalDateTime createdDate;
}
