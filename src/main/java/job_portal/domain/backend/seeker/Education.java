package job_portal.domain.backend.seeker;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "educations")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 150)
    private String schoolOrUniversity;

    @ManyToOne
    private Degree degree;

    @Column(nullable = false, length = 150)
    private String major;
    @Column(nullable = false, length = 50)
    private String country;
    @Column(nullable = false, length = 50)
    private String cityOrProvince;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String educationDetail;

    @OneToMany(mappedBy = "education", cascade = CascadeType.REMOVE)
    private List<SeekerEducation> seekerEducations;
}