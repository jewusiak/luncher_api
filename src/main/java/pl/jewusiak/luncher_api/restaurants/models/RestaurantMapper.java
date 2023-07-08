package pl.jewusiak.luncher_api.restaurants.models;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.jewusiak.luncher_api.companies.CompaniesService;
import pl.jewusiak.luncher_api.users.UsersService;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {UsersService.class, CompaniesService.class})
public interface RestaurantMapper {
    void updateEntity(RestaurantRequest request, @MappingTarget Restaurant entity);

    @Mapping(target = "isEnabled", expression = "java(false)")
    Restaurant mapRequestToNewRestaurant(RestaurantRequest restaurantRequest);

    RestaurantDto mapToDto(Restaurant restaurants);

    default UUID map(Restaurant restaurant) {
        return restaurant.getId();
    }


}
