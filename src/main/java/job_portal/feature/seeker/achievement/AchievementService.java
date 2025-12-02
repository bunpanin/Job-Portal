package job_portal.feature.seeker.achievement;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;

public interface AchievementService {
    void deleteById(Integer id);
    void updateByAchievementId(Integer id,UpdateAchievementRequest updateAchievementRequest);
    void createNewAchievement(String uuid,CreateAchievementRequest  createAchievementRequest);
    
}