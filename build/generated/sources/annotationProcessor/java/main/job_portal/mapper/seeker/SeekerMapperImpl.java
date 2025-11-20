package job_portal.mapper.seeker;

import javax.annotation.processing.Generated;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;
import job_portal.feature.seeker.auth.dto.request.SeekerUpdateRequest;

import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-19T15:14:33+0700",
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
}
