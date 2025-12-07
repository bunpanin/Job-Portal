package job_portal.mapper.seeker;
import job_portal.feature.seeker.language.dto.respone.LanguageRespone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import job_portal.domain.backend.seeker.Language;
import job_portal.feature.seeker.language.dto.request.CreateLanguageRequest;
import job_portal.feature.seeker.language.dto.request.UpdateLanguageRequest;

@Mapper(componentModel = "spring")
public interface LanguageMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "languageLevel", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    void fromLanguageUpdateRequest(UpdateLanguageRequest updateLanguageRequest, @MappingTarget Language language);
    @Mapping(target = "languageLevel", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "seeker", ignore = true)
    Language fromCreateNewLanguage(CreateLanguageRequest createLanguageRequest);
    @Mapping(target = "languageLevel", ignore = true)
    LanguageRespone toLanguageResponse(Language language);
}
