package job_portal.mapper.seeker;

import javax.annotation.processing.Generated;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-11T22:50:56+0700",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SeekerMapperImpl implements SeekerMapper {

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
