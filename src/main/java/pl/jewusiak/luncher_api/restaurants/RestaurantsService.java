package pl.jewusiak.luncher_api.restaurants;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.exceptions.ResourceAccessException;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantMapper;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantRequest;
import pl.jewusiak.luncher_api.users.models.URole;
import pl.jewusiak.luncher_api.users.models.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantsService {
    private final RestaurantsRepository restaurantsRepository;
    private final RestaurantMapper restaurantMapper;

    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantsRepository.findAll(pageable);
    }

    @Named("getRestaurantById")
    public Restaurant getRestaurantById(UUID id) {
        return restaurantsRepository.findById(id).orElseThrow(() -> new NotFoundException("Restaurant by ID"));
    }

    public Restaurant createNewRestaurant(Restaurant restaurant) {
        return restaurantsRepository.save(restaurant);
    }

    public Restaurant modifyRestaurant(UUID id, RestaurantRequest changes) {
        Restaurant oldRestaurant = getRestaurantById(id);
        restaurantMapper.updateEntity(changes, oldRestaurant);
        return restaurantsRepository.save(oldRestaurant);
    }

    public void deleteRestaurantById(UUID id) {
        restaurantsRepository.delete(getRestaurantById(id));
    }

    public void throwIfUserDoesNotOwnRestaurantAndIsNotAdmin(User user, UUID restaurantId) {
        if (!(restaurantsRepository.existsByIdAndModerator_Id(restaurantId, user.getId()) ||
                user.getAuthorities().contains(new SimpleGrantedAuthority(URole.ROLE_ADMIN.name()))))
            throw new ResourceAccessException();

    }
}
