package pl.jewusiak.luncher_api.companies.models;


import org.mapstruct.*;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.utils.Address;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateCompanyFromCompanyDetails(CompanyDetails companyDto, @MappingTarget Company existingCompany);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateAddress(Address oldAddress, @MappingTarget Address newAddress);

    Company mapCompanyDetailsToCompany(CompanyDetails companyDetails);

    CompanyDto mapToDto(Company company);

    default UUID map(Restaurant restaurant) {
        return restaurant.getId();
    }
}
