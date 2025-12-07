// package job_portal.domain.backend.seeker;
// import java.time.LocalDate;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
//
// @Getter
// @Setter
// @NoArgsConstructor
// @Entity
// @Builder
// @AllArgsConstructor
// @Table(name = "seeker_work_experiences")
// public class SeekerWorkExperience {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;
//
//     @ManyToOne
//     private Seeker seeker;
//
//     @ManyToOne
//     private WorkExperience workExperience;
//
//     private LocalDate createdAt;
// }
