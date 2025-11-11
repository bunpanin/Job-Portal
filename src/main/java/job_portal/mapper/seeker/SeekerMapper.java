package job_portal.mapper.seeker;
import org.mapstruct.Mapper;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.RegisterRequest;

@Mapper(componentModel = "spring")
public interface SeekerMapper{
    
    Seeker fromRegisterRequest(RegisterRequest registerRequest);
}