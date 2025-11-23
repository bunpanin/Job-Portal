package job_portal.mapper.seeker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.dto.request.RegisterRequest;

@Mapper(componentModel = "spring")
public interface SeekerMapper{

    // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // @Mapping(target = "jobLevel", ignore = true)
    // void fromSeekerUpdateRequest(SeekerUpdateRequest seekerUpdateRequest, @MappingTarget Seeker seeker);

    // @Mapping(target = "jobLevel", ignore = true)
    @Mapping(target = "jobLevel", ignore = true)
    Seeker fromRegisterRequest(RegisterRequest registerRequest);

    // @Mapping(target = "jobLevel", ignore = true)
    // @Mapping(target = "jobLevel", ignore = true)
    // DataRespone toDataRespone(Seeker seeker);
}