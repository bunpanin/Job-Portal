package job_portal.feature.seeker.language.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateLanguageRequest(
    @NotBlank(message = "languageName is required!")
    String languageName,
    @NotNull(message = "languageLevel is required!")
    Integer languageLevel
) {
    
}
