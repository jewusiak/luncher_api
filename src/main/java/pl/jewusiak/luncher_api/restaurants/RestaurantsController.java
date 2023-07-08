package pl.jewusiak.luncher_api.restaurants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.restaurants.models.Restaurant;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantDto;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantMapper;
import pl.jewusiak.luncher_api.restaurants.models.RestaurantRequest;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.utils.PageResponse;
import pl.jewusiak.luncher_api.utils.UserRoleDescription;

import java.util.UUID;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Restaurants management")
public class RestaurantsController {
    private final RestaurantsService restaurantsService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    @Operation(summary = "Get all restaurants",
    responses = {
            @ApiResponse(responseCode = "200", description = "Restaurants list")
    })
    @UserRoleDescription
    public PageResponse<RestaurantDto> getAllRestaurants(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return PageResponse.of(restaurantsService.getAllRestaurants(PageRequest.of(page, size))
                                                 .map(restaurantMapper::mapToDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by id",
    responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant details"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @UserRoleDescription
    public Restaurant getRestaurantById(@PathVariable UUID id) {
        return restaurantsService.getRestaurantById(id);
    }

    @PostMapping
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Create new restaurant",
    responses = {
            @ApiResponse(responseCode = "201", description = "Restaurant created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @UserRoleDescription
    public ResponseEntity<Restaurant> createNewRestaurant(@RequestBody RestaurantRequest restaurantRequest, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Restaurant restaurant = restaurantMapper.mapRequestToNewRestaurant(restaurantRequest);
        restaurant.setModerator(user);
        restaurant.setOwningCompany(user.getCompany());
        return ResponseEntity.status(201).body(restaurantsService.createNewRestaurant(restaurant));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Delete restaurant by id",
    responses = {
            @ApiResponse(responseCode = "204", description = "Restaurant deleted"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @UserRoleDescription
    public ResponseEntity<?> deleteRestaurantById(@PathVariable UUID id, Authentication authentication) {
        restaurantsService.throwIfUserDoesNotOwnRestaurantAndIsNotAdmin((User) authentication.getPrincipal(), id);

        restaurantsService.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Modify restaurant by id",
    responses = {
            @ApiResponse(responseCode = "200", description = "Restaurant modified"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @UserRoleDescription
    public Restaurant modifyRestaurant(@PathVariable UUID id, @RequestBody RestaurantRequest restaurantRequest, Authentication authentication) {
        restaurantsService.throwIfUserDoesNotOwnRestaurantAndIsNotAdmin((User) authentication.getPrincipal(), id);

        return restaurantsService.modifyRestaurant(id, restaurantRequest);
    }
}
