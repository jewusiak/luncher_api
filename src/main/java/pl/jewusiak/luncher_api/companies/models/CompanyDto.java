package pl.jewusiak.luncher_api.companies.models;

import jakarta.validation.constraints.Email;
import lombok.Data;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantDto;
import pl.jewusiak.luncher_api.users.models.UserDto;
import pl.jewusiak.luncher_api.utils.Address;

import java.util.List;
import java.util.UUID;

@Data
public class CompanyDto {
    private UUID id;
    private String tin;
    private String displayName;
    private String officialName;
    private Address officialAddress;
    private Address mailingAddress;
    private String emailContact;
    private String phoneNumber;
    private List<RestaurantDto> restaurants;
    private List<UserDto> managingUsers;
}
