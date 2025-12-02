package job_portal.feature.seeker.achievement;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import job_portal.domain.backend.seeker.Achievement;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.mapper.seeker.AchievementMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final SeekerRepository seekerRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementRepository achievementRepository;
    private final JwtService jwtService;

    @Override
    public void deleteById(Integer id) {
        Long seekerId = jwtService.extractUserId();
        Achievement achievement = achievementRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found!")
        );
        if(!achievement.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not dalete this Achievement");
        }
        achievementRepository.delete(achievement);
    }

    @Override
    public void updateByAchievementId(Integer id, UpdateAchievementRequest updateAchievementRequest) {
        Long seekerId = jwtService.extractUserId();
        Achievement achievement = achievementRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement not found!")
        );

        if(!achievement.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "You do not update this Achievement");
        }
        achievementMapper.fromAchievementUpdateRequest(updateAchievementRequest, achievement);
        achievementRepository.save(achievement);
    }

    @Override
    public void createNewAchievement(String uuid, CreateAchievementRequest createAchievementRequest) {
        Seeker seeker = seekerRepository.findByUuid(uuid)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!"));
        Achievement achievement = achievementMapper.fromCreateAchievement(createAchievementRequest);
        achievement.setCreatedAt(LocalDate.now());
        achievement.setSeeker(seeker);
        achievementRepository.save(achievement);
    }

}
