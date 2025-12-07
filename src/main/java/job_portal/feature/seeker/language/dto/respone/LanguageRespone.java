package job_portal.feature.seeker.language.dto.respone;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record LanguageRespone(
        Integer id,
        String languageName,
        String languageLevel,
        LocalDate createdAt
) {
}
