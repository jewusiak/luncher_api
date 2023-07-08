package pl.jewusiak.luncher_api.companies;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.companies.models.CompanyDetails;
import pl.jewusiak.luncher_api.companies.models.CompanyDto;
import pl.jewusiak.luncher_api.companies.models.CompanyMapper;
import pl.jewusiak.luncher_api.utils.PageResponse;
import pl.jewusiak.luncher_api.utils.UserRoleDescription;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
@Tag(name = "Companies", description = "Companies management")
public class CompaniesController {

    private final CompaniesService companiesService;
    private final CompanyMapper companyMapper;

    @GetMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Get all companies", responses = {
            @ApiResponse(responseCode = "200", description = "Companies list")
    })
    @UserRoleDescription
    public PageResponse<CompanyDto> getAllCompanies(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return PageResponse.of(companiesService.getAll(PageRequest.of(page, size)).map(companyMapper::mapToDto));
    }

    @GetMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Get company by id", responses = {
            @ApiResponse(responseCode = "200", description = "Company details"),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @UserRoleDescription
    public CompanyDto getCompanyById(@PathVariable UUID id) {
        return companyMapper.mapToDto(companiesService.getCompanyById(id));
    }

    @GetMapping("/bytin/{tin}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Get company by Tax ID Number", responses = {
            @ApiResponse(responseCode = "200", description = "Company details"),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @UserRoleDescription
    public CompanyDto getCompanyByTin(@PathVariable String tin) {
        return companyMapper.mapToDto(companiesService.getCompanyByTin(tin));
    }


    @PostMapping
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Create new company", responses = {
            @ApiResponse(responseCode = "201", description = "New company created"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Company with given Tax ID Number already exists", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<CompanyDto> createNewCompany(@RequestBody CompanyDetails companyDetails) {
        return ResponseEntity.status(201).body(companyMapper.mapToDto(companiesService.createNewCompany(companyMapper.mapCompanyDetailsToCompany(companyDetails))));
    }

    @PutMapping("/{companyId}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Modify company", responses = {
            @ApiResponse(responseCode = "200", description = "Company modified"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @UserRoleDescription
    public CompanyDto modifyCompany(@RequestBody CompanyDetails companyDetails, @PathVariable UUID companyId) {
        return companyMapper.mapToDto(companiesService.modifyCompanyById(companyDetails, companyId));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Delete company", responses = {
            @ApiResponse(responseCode = "204", description = "Company deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Company not found", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<?> deleteCompany(@PathVariable UUID id) {
        companiesService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{companyId}/adduser/{userId}")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Add user to company", responses = {
            @ApiResponse(responseCode = "200", description = "User added to company"),
            @ApiResponse(responseCode = "404", description = "Company or user not found", content = @Content)
    })
    @UserRoleDescription
    public CompanyDto addUserToCompany(@PathVariable UUID companyId, @PathVariable UUID userId) {
        return companyMapper.mapToDto(companiesService.addUserToCompany(companyId, userId));
    }
}
