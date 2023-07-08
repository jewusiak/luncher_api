package pl.jewusiak.luncher_api.diningOffers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.DiningOfferDto;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.DiningOfferElementDto;
import pl.jewusiak.luncher_api.diningOffers.models.dtos.PlateDto;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferElementRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.DiningOfferRequest;
import pl.jewusiak.luncher_api.diningOffers.models.requests.PlateRequest;
import pl.jewusiak.luncher_api.restaurants.RestaurantsService;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.utils.UserRoleDescription;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diningoffers")
@Tag(name = "Dining offers", description = "Operations related to dining offers")
public class DiningOffersController {
    private final DiningOffersService diningOffersService;
    private final RestaurantsService restaurantsService;
    private final DiningOffersMapper diningOffersMapper;
    private final DiningOffersCreationMapper diningOffersCreationMapper;

    @PutMapping("/do/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Modify dining offer", description = "Modifies dining offer with given id. Only owner of the dining offer or an Admin can modify it.", responses = {
            @ApiResponse(responseCode = "200", description = "Dining offer details"),
            @ApiResponse(responseCode = "404", description = "Dining offer not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public DiningOfferDto modifyDiningOffer(@PathVariable UUID id, @RequestBody DiningOfferRequest diningOfferRequest, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferAndIsNotAdmin((User) authentication.getPrincipal(), id);
        diningOfferRequest.setRestaurantId(null);

        return diningOffersMapper.mapToDto(diningOffersService.modifyDiningOffer(id, diningOfferRequest));
    }

    @PutMapping("/doe/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Modify dining offer element", description = "Modifies dining offer element with given id. Only owner of the dining offer element or an Admin can modify it.", responses = {
            @ApiResponse(responseCode = "200", description = "Dining offer element details"),
            @ApiResponse(responseCode = "404", description = "Dining offer element not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer element and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public DiningOfferElementDto modifyDiningOfferElement(@PathVariable UUID id, @RequestBody DiningOfferElementRequest diningOfferElementRequest, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferElementAndIsNotAdmin((User) authentication.getPrincipal(), id);
        diningOfferElementRequest.setDiningOfferId(null);

        return diningOffersMapper.mapToDto(diningOffersService.modifyDiningOfferElement(id, diningOfferElementRequest));
    }

    @PutMapping("/plates/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Modify plate", description = "Modifies plate with given id. Only owner of the plate or an Admin can modify it.", responses = {
            @ApiResponse(responseCode = "200", description = "Plate details"),
            @ApiResponse(responseCode = "404", description = "Plate not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the plate and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public PlateDto modifyPlate(@PathVariable UUID id, @RequestBody PlateRequest plateRequest, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnPlateAndIsNotAdmin((User) authentication.getPrincipal(), id);
        plateRequest.setDiningOfferElementId(null);
        return diningOffersMapper.mapToDto(diningOffersService.modifyPlate(id, plateRequest));
    }


    @GetMapping("/do/{id}")
    @Operation(summary = "Get dining offer", description = "Returns dining offer with given id.", responses = {
            @ApiResponse(responseCode = "200", description = "Dining offer details"),
            @ApiResponse(responseCode = "404", description = "Dining offer not found", content = @Content)
    })
    @UserRoleDescription
    public DiningOfferDto getDiningOfferById(@PathVariable UUID id) {
        return diningOffersMapper.mapToDto(diningOffersService.getDiningOfferById(id));
    }

    @DeleteMapping("/do/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Delete dining offer", description = "Deletes dining offer with given id. Only owner of the dining offer or an Admin can delete it.", responses = {
            @ApiResponse(responseCode = "204", description = "Dining offer deleted"),
            @ApiResponse(responseCode = "404", description = "Dining offer not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<?> deleteDiningOfferById(@PathVariable UUID id, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferAndIsNotAdmin((User) authentication.getPrincipal(), id);

        diningOffersService.deleteDiningOfferById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/doe/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Delete dining offer element", description = "Deletes dining offer element with given id. Only owner of the dining offer element or an Admin can delete it.", responses = {
            @ApiResponse(responseCode = "204", description = "Dining offer element deleted"),
            @ApiResponse(responseCode = "404", description = "Dining offer element not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer element and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<?> deleteDiningOfferElementById(@PathVariable UUID id, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferElementAndIsNotAdmin((User) authentication.getPrincipal(), id);

        diningOffersService.deleteDiningOfferElementById(id);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/plates/{id}")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Delete plate", description = "Deletes plate with given id. Only owner of the plate or an Admin can delete it.", responses = {
            @ApiResponse(responseCode = "204", description = "Plate deleted"),
            @ApiResponse(responseCode = "404", description = "Plate not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the plate and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<?> deletePlateById(@PathVariable UUID id, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnPlateAndIsNotAdmin((User) authentication.getPrincipal(), id);

        diningOffersService.deletePlateById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/do")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Create dining offer", description = "Creates new dining offer. Only owner of the restaurant or an Admin can create it.", responses = {
            @ApiResponse(responseCode = "201", description = "Dining offer created"),
            @ApiResponse(responseCode = "404", description = "Restaurant not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the restaurant and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<DiningOfferDto> createDiningOffer(@RequestBody DiningOfferRequest diningOfferRequest, Authentication authentication) {
        restaurantsService.throwIfUserDoesNotOwnRestaurantAndIsNotAdmin((User) authentication.getPrincipal(), diningOfferRequest.getRestaurantId());

        return ResponseEntity.status(201)
                .body(diningOffersMapper.mapToDto(diningOffersService.createNewDiningOffer(diningOffersCreationMapper.mapNewToEntity(diningOfferRequest))));
    }

    @PostMapping("/doe")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Create dining offer element", description = "Creates new dining offer element. Only owner of the dining offer or an Admin can create it.", responses = {
            @ApiResponse(responseCode = "201", description = "Dining offer element created"),
            @ApiResponse(responseCode = "404", description = "Dining offer not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<DiningOfferElementDto> createDiningOfferElement(@RequestBody DiningOfferElementRequest diningOfferElementRequest, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferAndIsNotAdmin((User) authentication.getPrincipal(), diningOfferElementRequest.getDiningOfferId());

        return ResponseEntity.status(201)
                .body(diningOffersMapper.mapToDto(diningOffersService.createNewDiningOfferElement(diningOffersCreationMapper.mapNewToEntity(diningOfferElementRequest))));
    }

    @PostMapping("/plates")
    @Secured("ROLE_RESTAURATEUR")
    @Operation(summary = "Create plate", description = "Creates new plate. Only owner of the dining offer element or an Admin can create it.", responses = {
            @ApiResponse(responseCode = "201", description = "Plate created"),
            @ApiResponse(responseCode = "404", description = "Dining offer element not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "User is not owner of the dining offer element and is not an Admin", content = @Content)
    })
    @UserRoleDescription
    public ResponseEntity<PlateDto> createPlate(@RequestBody PlateRequest plateRequest, Authentication authentication) {
        diningOffersService.throwIfUserDoesNotOwnDiningOfferElementAndIsNotAdmin((User) authentication.getPrincipal(), plateRequest.getDiningOfferElementId());

        return ResponseEntity.status(201)
                .body(diningOffersMapper.mapToDto(diningOffersService.createNewPlate(diningOffersCreationMapper.mapNewToEntity(plateRequest))));
    }
}
