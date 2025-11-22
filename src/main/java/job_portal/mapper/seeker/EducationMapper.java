package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import job_portal.domain.backend.seeker.Education;
import job_portal.feature.seeker.education.dto.request.CreateEductionRequest;
import job_portal.feature.seeker.education.dto.request.UpdateEducationRequest;
import job_portal.feature.seeker.education.dto.respone.EducationRespone;

@Mapper(componentModel = "spring")
public interface EducationMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "degree", ignore = true)
    void fromEducationUpdateRequest(UpdateEducationRequest updateEducationRequest, @MappingTarget Education education);

    @Mapping(target = "degree", ignore = true)
    Education fromCreateNewEducation(CreateEductionRequest createEductionRequest);

    @Mapping(target = "degree", ignore = true)
    EducationRespone toEducationRespone(Education education);
}