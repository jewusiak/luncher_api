package pl.jewusiak.luncher_api.diningOffers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.utils.InWeekDateRange;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiningOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;

    private boolean isEnabled;
    @Embedded
    private Price price;

    @Embedded
    private InWeekDateRange range;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "diningOffer")
    private List<DiningOfferElement> elements;

    @ManyToOne//(optional = false)
    @JsonIgnore
    private Restaurant restaurant;
}
