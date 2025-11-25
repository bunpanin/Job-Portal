package job_portal.feature.seeker.achievement;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import job_portal.feature.seeker.achievement.dto.request.CreateAchievementRequest;
import job_portal.feature.seeker.achievement.dto.request.UpdateAchievementRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ROLE_SEEKER', 'ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        achievementService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    void updateAchievementById(@PathVariable Integer id,@Valid @RequestBody UpdateAchievementRequest updateAchievementRequest){
        achievementService.updateByAchievementId(id,updateAchievementRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PostMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@Valid @PathVariable String uuid, @RequestBody CreateAchievementRequest createAchievementRequest){
        achievementService.createNewAchievement(uuid, createAchievementRequest);
    }
    

}
