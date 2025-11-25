package job_portal.mapper.seeker;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import job_portal.domain.backend.seeker.Achievement;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.respone.AchievementRespone;

@Mapper(componentModel = "spring")
public interface AchievementMapper{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromAchievementUpdateRequest(UpdateAchievementRequest updateAchievementRequest, @MappingTarget Achievement achievement);

    Achievement fromCreateNewAchievement(CreateAchievementRequest createAchievementRequest);

    AchievementRespone toAcheievementRespone(Achievement achievement);
    
}
