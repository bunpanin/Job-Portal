package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import job_portal.domain.backend.seeker.Seeker;
// import job_portal.feature.seeker.auth.dto.DataRespone;
import job_portal.feature.seeker.auth.dto.RegisterRequest;
import job_portal.feature.seeker.auth.dto.SeekerUpdateRequest;

@Mapper(componentModel = "spring")
public interface SeekerMapper{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(target = "jobLevel", ignore = true)
    void fromSeekerUpdateRequest(SeekerUpdateRequest seekerUpdateRequest, @MappingTarget Seeker seeker);

    // @Mapping(target = "jobLevel", ignore = true)
    @Mapping(target = "jobLevel", ignore = true)
    Seeker fromRegisterRequest(RegisterRequest registerRequest);

    // @Mapping(target = "jobLevel", ignore = true)
    // @Mapping(target = "jobLevel", ignore = true)
    // DataRespone toDataRespone(Seeker seeker);
}