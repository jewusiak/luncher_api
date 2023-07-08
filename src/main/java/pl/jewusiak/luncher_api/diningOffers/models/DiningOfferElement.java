package pl.jewusiak.luncher_api.diningOffers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiningOfferElement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String heading;
    private boolean isEnabled;

    @Embedded
    private Price additionalPrice;

    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "diningOfferElement")
    private List<Plate> plates;

    @ManyToOne
    @JsonIgnore
    private DiningOffer diningOffer;


}
