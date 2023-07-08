package pl.jewusiak.luncher_api.diningOffers.models.dtos;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.UUID;

@Data
public class PlateDto {
    private UUID id;
    private String name;
    private String description;
    private String ingredientList;
    private Price additionalPrice;
}
