package pl.jewusiak.luncher_api.restaurants.models;

import jakarta.persistence.*;
import lombok.*;
import pl.jewusiak.luncher_api.companies.models.Company;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOffer;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String displayName;

    @ManyToOne(optional = false)
    private User moderator;

    private boolean isEnabled;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "restaurant")
    private List<DiningOffer> diningOffers;
    
    @ManyToOne(optional = false)
    private Company owningCompany;
}
