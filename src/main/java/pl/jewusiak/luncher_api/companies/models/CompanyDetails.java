package pl.jewusiak.luncher_api.companies.models;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.Address;

@Data
public class CompanyDetails {
    private String displayName;
    private String officialName;
    private Address officialAddress;
    private String tin;
    private Address mailingAddress;
    private String emailContact;
    private String phoneNumber;
}
