package job_portal.mapper.seeker;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.DataRespone;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.SeekerUpdateRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-17T02:21:49+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SeekerMapperImpl implements SeekerMapper {

    @Override
    public void fromSeekerUpdateRequest(SeekerUpdateRequest seekerUpdateRequest, Seeker seeker) {
        if ( seekerUpdateRequest == null ) {
            return;
        }

        if ( seekerUpdateRequest.fullName() != null ) {
            seeker.setFullName( seekerUpdateRequest.fullName() );
        }
        if ( seekerUpdateRequest.phoneNumber() != null ) {
            seeker.setPhoneNumber( seekerUpdateRequest.phoneNumber() );
        }
        if ( seekerUpdateRequest.gender() != null ) {
            seeker.setGender( seekerUpdateRequest.gender() );
        }
        if ( seekerUpdateRequest.profile() != null ) {
            seeker.setProfile( seekerUpdateRequest.profile() );
        }
        if ( seekerUpdateRequest.dob() != null ) {
            seeker.setDob( seekerUpdateRequest.dob() );
        }
        if ( seekerUpdateRequest.address() != null ) {
            seeker.setAddress( seekerUpdateRequest.address() );
        }
        if ( seekerUpdateRequest.cityOrProvince() != null ) {
            seeker.setCityOrProvince( seekerUpdateRequest.cityOrProvince() );
        }
        if ( seekerUpdateRequest.country() != null ) {
            seeker.setCountry( seekerUpdateRequest.country() );
        }
        if ( seekerUpdateRequest.githubAccount() != null ) {
            seeker.setGithubAccount( seekerUpdateRequest.githubAccount() );
        }
        if ( seekerUpdateRequest.linkAccount() != null ) {
            seeker.setLinkAccount( seekerUpdateRequest.linkAccount() );
        }
        if ( seekerUpdateRequest.portfolio() != null ) {
            seeker.setPortfolio( seekerUpdateRequest.portfolio() );
        }
        if ( seekerUpdateRequest.education() != null ) {
            seeker.setEducation( seekerUpdateRequest.education() );
        }
        if ( seekerUpdateRequest.achievement() != null ) {
            seeker.setAchievement( seekerUpdateRequest.achievement() );
        }
        if ( seekerUpdateRequest.skill() != null ) {
            seeker.setSkill( seekerUpdateRequest.skill() );
        }
        if ( seekerUpdateRequest.language() != null ) {
            seeker.setLanguage( seekerUpdateRequest.language() );
        }
        if ( seekerUpdateRequest.reference() != null ) {
            seeker.setReference( seekerUpdateRequest.reference() );
        }
        if ( seekerUpdateRequest.descriptionYourSelf() != null ) {
            seeker.setDescriptionYourSelf( seekerUpdateRequest.descriptionYourSelf() );
        }
    }

    @Override
    public Seeker fromRegisterRequest(RegisterRequest registerRequest) {
        if ( registerRequest == null ) {
            return null;
        }

        Seeker seeker = new Seeker();

        seeker.setFullName( registerRequest.fullName() );
        seeker.setEmail( registerRequest.email() );
        seeker.setPassword( registerRequest.password() );

        return seeker;
    }

    @Override
    public DataRespone toDataRespone(Seeker seeker) {
        if ( seeker == null ) {
            return null;
        }

        DataRespone.DataResponeBuilder dataRespone = DataRespone.builder();

        dataRespone.uuid( seeker.getUuid() );
        dataRespone.fullName( seeker.getFullName() );
        dataRespone.email( seeker.getEmail() );
        dataRespone.phoneNumber( seeker.getPhoneNumber() );
        dataRespone.password( seeker.getPassword() );
        dataRespone.gender( seeker.getGender() );
        dataRespone.profile( seeker.getProfile() );
        if ( seeker.getDob() != null ) {
            dataRespone.dob( DateTimeFormatter.ISO_LOCAL_DATE.format( seeker.getDob() ) );
        }
        dataRespone.address( seeker.getAddress() );
        dataRespone.cityOrProvince( seeker.getCityOrProvince() );
        dataRespone.country( seeker.getCountry() );
        dataRespone.githubAccount( seeker.getGithubAccount() );
        dataRespone.linkAccount( seeker.getLinkAccount() );
        dataRespone.portfolio( seeker.getPortfolio() );
        dataRespone.education( seeker.getEducation() );
        dataRespone.achievement( seeker.getAchievement() );
        dataRespone.descriptionYourSelf( seeker.getDescriptionYourSelf() );
        if ( seeker.getCreatedAt() != null ) {
            dataRespone.createdAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( seeker.getCreatedAt() ) );
        }
        if ( seeker.getIsVerified() != null ) {
            dataRespone.isVerified( String.valueOf( seeker.getIsVerified() ) );
        }
        if ( seeker.getIsBloked() != null ) {
            dataRespone.isBloked( String.valueOf( seeker.getIsBloked() ) );
        }
        if ( seeker.getIsAccountNonExpired() != null ) {
            dataRespone.isAccountNonExpired( String.valueOf( seeker.getIsAccountNonExpired() ) );
        }
        if ( seeker.getIsAccountNonLocked() != null ) {
            dataRespone.isAccountNonLocked( String.valueOf( seeker.getIsAccountNonLocked() ) );
        }
        if ( seeker.getIsCredentialsNonExpired() != null ) {
            dataRespone.isCredentialsNonExpired( String.valueOf( seeker.getIsCredentialsNonExpired() ) );
        }

        return dataRespone.build();
    }
}
