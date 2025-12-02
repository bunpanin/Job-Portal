package job_portal.feature.seeker.language;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import job_portal.domain.backend.seeker.Language;
import job_portal.domain.backend.seeker.LanguageLevel;
import job_portal.domain.backend.seeker.Seeker;
import job_portal.feature.seeker.auth.SeekerRepository;
import job_portal.feature.seeker.language.dto.CreateLanguageRequest;
import job_portal.feature.seeker.language.dto.UpdateLanguageRequest;
import job_portal.feature.seeker.languageLevel.LanguageLevelRepository;
import job_portal.mapper.seeker.LanguageMapper;
import job_portal.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService{

    private final SeekerRepository seekerRepository;
    private final LanguageLevelRepository languageLevelRepository;
    private final LanguageMapper languageMapper;
    private final LanguageRepository languageRepository;
    private final JwtService jwtService;

    @Override
    public void deleteById(Integer id) {
        Long seekerId = jwtService.extractUserId();
        Language language = languageRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found!")
        );
        if(!language.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not delete this language");
        }
        languageRepository.delete(language);
    }   

    @Override
    public void updateById(Integer id, UpdateLanguageRequest updateLanguageRequest) {
        Long seekerId = jwtService.extractUserId();
        Language language = languageRepository.findById(id).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found!")
        );
        if(!language.getSeeker().getId().equals(seekerId.intValue())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You do not update this language");
        }

        languageMapper.fromLanguageUpdateRequest(updateLanguageRequest, language);
        if(updateLanguageRequest.languageLevel() != null){
            LanguageLevel languageLevel = languageLevelRepository.findById(updateLanguageRequest.languageLevel()).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language Level not found!")
            );
            language.setLanguageLevel(languageLevel);
        }
        languageRepository.save(language);
    }

    @Override
    public void createNewLanguage(String uuid, CreateLanguageRequest createLanguageRequest) {
        Seeker seeker = seekerRepository.findByUuid(uuid).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seeker not found!")
        );

        LanguageLevel languageLevel = languageLevelRepository.findById(createLanguageRequest.languageLevel()).orElseThrow(
            ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Languag Level not found!")
        );

        Language language = languageMapper.fromCreateNewLanguage(createLanguageRequest);
        language.setSeeker(seeker);
        language.setLanguageLevel(languageLevel);
        language.setCreatedAt(LocalDate.now());
        languageRepository.save(language);
    } 
}