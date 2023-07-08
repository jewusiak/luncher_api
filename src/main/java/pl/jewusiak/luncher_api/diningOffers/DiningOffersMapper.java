package pl.jewusiak.luncher_api.diningOffers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOffer;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOfferElement;
import pl.jewusiak.luncher_api.diningOffers.models.Plate;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.DiningOfferDto;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.DiningOfferElementDto;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.PlateDto;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferElementRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.PlateRequest;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DiningOffersMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PlateRequest request, @MappingTarget Plate oldEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(DiningOfferRequest request, @MappingTarget DiningOffer oldEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(DiningOfferElementRequest request, @MappingTarget DiningOfferElement oldEntity);


    PlateDto mapToDto(Plate plate);

    DiningOfferElementDto mapToDto(DiningOfferElement diningOfferElement);

    DiningOfferDto mapToDto(DiningOffer diningOffer);

    default UUID map(Restaurant restaurant) {
        return restaurant.getId();
    }

}
