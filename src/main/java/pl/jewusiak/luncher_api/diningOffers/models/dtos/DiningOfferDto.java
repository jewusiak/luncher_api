package pl.jewusiak.luncher_api.diningOffers.models.dtos;

import lombok.Data;
import pl.jewusiak.luncher_api.utils.InWeekDateRange;
import pl.jewusiak.luncher_api.utils.Price;

import java.util.List;
import java.util.UUID;

@Data
public class DiningOfferDto {
    private UUID id;
    private String name;
    private String description;
    private InWeekDateRange range;
    private Price price;
    private UUID restaurant;
    private List<DiningOfferElementDto> elements;
}
