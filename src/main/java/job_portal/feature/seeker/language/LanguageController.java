package job_portal.feature.seeker.language;

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
import job_portal.feature.seeker.language.dto.CreateLanguageRequest;
import job_portal.feature.seeker.language.dto.UpdateLanguageRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/seeker/language")
@RequiredArgsConstructor
public class LanguageController {
    
    private final LanguageService languageService;

    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id){
        languageService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    void updateById(@PathVariable Integer id,@Valid @RequestBody UpdateLanguageRequest updateLanguageRequest){
        languageService.updateById(id, updateLanguageRequest);
    }

    @PostMapping("/{uuid}")
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @ResponseStatus(HttpStatus.CREATED)
    void createNew(@PathVariable String uuid,@Valid @RequestBody CreateLanguageRequest createLanguageRequest){
        languageService.createNewLanguage(uuid, createLanguageRequest);
    }

}
