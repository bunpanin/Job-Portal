package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import job_portal.domain.backend.seeker.WorkExperience;
import job_portal.feature.seeker.workExperience.dto.request.CreateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.request.UpdateWorkExperienceRequest;
import job_portal.feature.seeker.workExperience.dto.respone.WorkExperienceRespone;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {

    @Mapping(target = "jobLevel", ignore = true)
    @Mapping(target = "typeOfExperience", ignore = true)
    WorkExperienceRespone toWorkExperienceRespone(WorkExperience workExperience);

    @Mapping(target = "jobLevel", ignore = true)
    @Mapping(target = "typeOfExperience", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromWorkExperienceUpdateRequest(UpdateWorkExperienceRequest updateWorkExperienceRequest, @MappingTarget WorkExperience workExperience);

    @Mapping(target = "jobLevel", ignore = true)
    @Mapping(target = "typeOfExperience", ignore = true)
    WorkExperience fromCreateNewWorkExperience(CreateWorkExperienceRequest createWorkExperienceRequest);



    
}