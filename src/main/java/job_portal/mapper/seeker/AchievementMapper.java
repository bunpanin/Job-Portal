package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import job_portal.domain.backend.seeker.Achievement;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.respone.AchievementRespone;

@Mapper(componentModel = "spring")
public interface AchievementMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    void fromAchievementUpdateRequest(UpdateAchievementRequest updateAchievementRequest, @MappingTarget Achievement achievement);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    Achievement fromCreateAchievement(CreateAchievementRequest createAchievementRequest);

    AchievementRespone toAchievementRespone(Achievement achievement);

    
}