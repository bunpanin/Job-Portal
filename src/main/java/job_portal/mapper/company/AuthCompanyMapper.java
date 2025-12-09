package job_portal.mapper.company;
import job_portal.domain.backend.company.Company;
import job_portal.feature.company.auth.dto.request.CompanyRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthCompanyMapper {

    Company fromRegisterRequest(CompanyRegisterRequest companyRegisterRequest);
}
