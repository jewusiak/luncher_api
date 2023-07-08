package pl.jewusiak.luncher_api.diningOffers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOffer;
import pl.jewusiak.luncher_api.diningOffers.models.DiningOfferElement;
import pl.jewusiak.luncher_api.diningOffers.models.Plate;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferElementRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.PlateRequest;
import pl.jewusiak.luncher_api.restaurants.RestaurantsService;

@Mapper(componentModel = "spring", uses = {RestaurantsService.class, DiningOffersService.class})
public interface DiningOffersCreationMapper {
    @Mapping(target = "restaurant", source = "restaurantId", qualifiedByName = "getRestaurantById")
    DiningOffer mapNewToEntity(DiningOfferRequest diningOfferRequest);

    @Mapping(target = "diningOffer", source = "diningOfferId", qualifiedByName = "getDiningOfferById")
    DiningOfferElement mapNewToEntity(DiningOfferElementRequest diningOfferElementRequest);

    @Mapping(target = "diningOfferElement", source = "diningOfferElementId", qualifiedByName = "getDiningOfferElementById")
    Plate mapNewToEntity(PlateRequest plateRequest);
}
