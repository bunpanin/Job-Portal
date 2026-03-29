package job_portal.mapper;

import job_portal.domain.Seeker;
import job_portal.domain.User;
import job_portal.feature.seeker.Auth.dto.request.UpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SeekerMapper {

    // Update User basic fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UpdateRequest update, @MappingTarget User user);

    // Update Seeker fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSeekerFromDto(UpdateRequest update, @MappingTarget Seeker seeker);
}
