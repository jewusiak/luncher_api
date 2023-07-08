package pl.jewusiak.luncher_api.diningOffers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.UUID;

@Entity
@Data
public class Plate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank
    private String name;
    private String description;
    private String ingredientList;
    private boolean isEnabled;

    @Embedded
    private Price additionalPrice;

    @ManyToOne
    @JsonIgnore
    private DiningOfferElement diningOfferElement;
}
