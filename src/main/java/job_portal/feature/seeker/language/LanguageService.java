package job_portal.feature.seeker.language;

import job_portal.feature.seeker.language.dto.CreateLanguageRequest;
import job_portal.feature.seeker.language.dto.UpdateLanguageRequest;

public interface LanguageService {

    void deleteById(Integer id);
    void updateById(Integer id,UpdateLanguageRequest updateLanguageRequest);
    void createNewLanguage(String uuid,CreateLanguageRequest createLanguageRequest);
}