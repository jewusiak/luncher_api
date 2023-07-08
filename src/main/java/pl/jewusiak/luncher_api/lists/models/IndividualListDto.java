package pl.jewusiak.luncher_api.lists.models;

import lombok.Data;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantDto;

import java.util.List;
import java.util.UUID;

@Data
public class IndividualListDto {
    private UUID id;
    private String displayName;
    private UUID owner;
    private List<RestaurantDto> restaurants;
}
