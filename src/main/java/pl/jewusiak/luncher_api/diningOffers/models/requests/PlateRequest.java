package pl.jewusiak.luncher_api.diningOffers.models.requests;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.UUID;

@Data
public class PlateRequest {
    private String name;
    private String description;
    private String ingredientList;
    private Price additionalPrice;
    private UUID diningOfferElementId;
}
