package pl.jewusiak.luncher_api.diningOffers.models.requests;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.InWeekDateRange;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.UUID;

@Data
public class DiningOfferRequest {
    private String name;
    private String description;
    private InWeekDateRange range;
    private Price price;
    private UUID restaurantId;
}
