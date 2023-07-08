package pl.jewusiak.luncher_api.restaurants.models;

import lombok.Data;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.DiningOfferDto;

import java.util.List;
import java.util.UUID;

@Data
public class RestaurantDto {
    private UUID id;
    private String displayName;
    private List<DiningOfferDto> diningOffers;
}
