package pl.jewusiak.luncher_api.diningOffers.models.requests;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.UUID;

@Data
public class DiningOfferElementRequest {
    private String heading;
    private Price additionalPrice;
    private UUID diningOfferId;
}
