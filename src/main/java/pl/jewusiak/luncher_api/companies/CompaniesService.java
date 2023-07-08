package pl.jewusiak.luncher_api.companies;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.luncher_api.companies.models.Company;
import pl.jewusiak.luncher_api.companies.models.CompanyDetails;
import pl.jewusiak.luncher_api.companies.models.CompanyMapper;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.users.UsersService;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompaniesService {

    private final CompaniesRepository companiesRepository;
    private final CompanyMapper companyMapper;
    private final UsersService usersService;

    @Named("getCompanyById")
    public Company getCompanyById(UUID id) {
        return companiesRepository.findById(id).orElseThrow(() -> new NotFoundException("Company by ID"));
    }

    public Company getCompanyByTin(String tin){
        return companiesRepository.findByTin(tin).orElseThrow(() -> new NotFoundException("Company by TIN"));
    }

    public Page<Company> getAll(Pageable pageable) {
        return companiesRepository.findAll(pageable);
    }

    public Company createNewCompany(Company company) {
        if (companiesRepository.existsByTin(company.getTin()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company with this TIN already exists. TIN: " + company.getTin());
        return companiesRepository.save(company);
    }

    public Company modifyCompanyById(CompanyDetails changes, UUID companyUuid) {
        Company oldCompany = getCompanyById(companyUuid);
        companyMapper.updateCompanyFromCompanyDetails(changes, oldCompany);
        return companiesRepository.save(oldCompany);
    }

    public void deleteCompanyById(UUID id) {
        companiesRepository.delete(getCompanyById(id));
    }

    public Company addUserToCompany(UUID companyId, UUID userId){
        Company company=getCompanyById(companyId);
        User user = usersService.getUserById(userId);
        user.setCompany(company);
        company.getManagingUsers().add(user);
        return companiesRepository.save(company);
    }
}
