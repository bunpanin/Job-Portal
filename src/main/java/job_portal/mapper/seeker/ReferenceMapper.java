package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import job_portal.domain.backend.seeker.SeekerReference;
import job_portal.feature.seeker.reference.dto.request.CreateReferenceRequest;
import job_portal.feature.seeker.reference.dto.request.UpdateReferenceRequest;
import job_portal.feature.seeker.reference.dto.respone.ReferenceRespone;

@Mapper(componentModel = "spring")
public interface ReferenceMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    void fromReferenceUpdateRequest(UpdateReferenceRequest updateReferenceRequest, @MappingTarget SeekerReference reference);
    ReferenceRespone toReferenceRespone(SeekerReference reference);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    SeekerReference fromCreateNewReference(CreateReferenceRequest createReferenceRequest);
}