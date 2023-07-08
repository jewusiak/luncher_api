package pl.jewusiak.luncher_api.diningOffers.models.dtos;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.List;
import java.util.UUID;

@Data
public class DiningOfferElementDto {
    private UUID id;
    private String heading;
    private Price additionalPrice;
    private List<PlateDto> plates;

}
