package pl.jewusiak.luncher_api.companies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.utils.Address;

import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 13)
    @NotBlank
    @NaturalId(mutable = true)
    private String tin;

    private String displayName;
    @NotBlank
    private String officialName;
    @Embedded
    private Address officialAddress;
    @Embedded
    private Address mailingAddress;
    private String emailContact;
    @Column(length = 20)
    private String phoneNumber;

    @OneToMany(mappedBy = "owningCompany")
    @JsonIgnore
    private List<Restaurant> restaurants;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<User> managingUsers;

}

