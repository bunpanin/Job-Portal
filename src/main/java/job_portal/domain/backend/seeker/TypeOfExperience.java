package job_portal.domain.backend.seeker;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "type_of_experoences")
public class TypeOfExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(unique = true)
    private String uuid;

    @Column(nullable = false, unique = true, length = 50)
    private String alias;

    @Column(nullable = false, unique = true, length = 25)
    private String name; 

    // @Column(columnDefinition = "TEXT")
    // private String description;
    
    private Boolean isDeleted;
    private LocalDate createdAt;

    // --------- Reletionship Table -------------
    @OneToMany(mappedBy = "typeOfExperience")
    private List<WorkExperience> workExperiences;


}
