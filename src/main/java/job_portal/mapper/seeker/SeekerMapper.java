package job_portal.mapper.seeker;
import job_portal.feature.seeker.auth.dto.request.SeekerUpdateRequest;
import org.mapstruct.*;

import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;

@Mapper(componentModel = "spring")
public interface SeekerMapper{

     @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
     @Mapping(target = "jobLevel", ignore = true)
    void fromSeekerUpdateRequest(SeekerUpdateRequest seekerUpdateRequest, @MappingTarget Seeker seeker);

    Seeker fromRegisterRequest(RegisterRequest registerRequest);

    // @Mapping(target = "jobLevel", ignore = true)
    // @Mapping(target = "jobLevel", ignore = true)
    // DataRespone toDataRespone(Seeker seeker);
}