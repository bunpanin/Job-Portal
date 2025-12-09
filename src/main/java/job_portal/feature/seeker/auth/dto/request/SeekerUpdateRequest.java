 package job_portal.feature.seeker.auth.dto.request;
 import java.time.LocalDate;
 import lombok.Builder;

 @Builder
 public record SeekerUpdateRequest(
     String fullName,
     String email,
     String phoneNumber,
     String gender,
     LocalDate dob,
     Integer jobLevel,
     String address,
     String cityOrProvince,
     String country,
     String githubAccount,
     String linkAccount,
     String portfolio,
     String descriptionYourSelf,
     String cvFile
 ) {
 }
