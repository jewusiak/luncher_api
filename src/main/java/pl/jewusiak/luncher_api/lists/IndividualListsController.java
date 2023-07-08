package pl.jewusiak.luncher_api.lists;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.lists.models.IndividualList;
import pl.jewusiak.luncher_api.lists.models.IndividualListDto;
import pl.jewusiak.luncher_api.lists.models.IndividualListMapper;
import pl.jewusiak.luncher_api.lists.models.IndividualListRequest;
import pl.jewusiak.luncher_api.users.models.URole;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.utils.UserRoleDescription;

import java.util.UUID;

@RestController
@RequestMapping("/lists")
@RequiredArgsConstructor
@Tag(name = "Individual lists", description = "Operations related to users' individual lists")
public class IndividualListsController {
    private final IndividualListsService individualListsService;
    private final IndividualListMapper individualListMapper;


    @GetMapping("/{id}")
    @Operation(summary = "Get list by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List found"),
                    @ApiResponse(responseCode = "403", description = "User does not own this list", content = @Content),
                    @ApiResponse(responseCode = "404", description = "List not found", content = @Content)
            })
    @UserRoleDescription
    public IndividualListDto getListById(@PathVariable UUID id, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        if (!user.getAuthorities().contains(new SimpleGrantedAuthority(URole.ROLE_ADMIN.name())))
            individualListsService.throwIfUserDoesNotOwnList(id, user.getId());
        return individualListMapper.mapToDto(individualListsService.getListById(id));
    }

    @PostMapping
    @Operation(summary = "Create new list",
            responses = {
                    @ApiResponse(responseCode = "201", description = "List created"),
                    @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
            })
    @UserRoleDescription
    public ResponseEntity<IndividualListDto> createNewList(@RequestBody IndividualListRequest individualListRequest, Authentication authentication) {
        IndividualList individualList = individualListMapper.mapToEntity(individualListRequest);
        individualList.setOwner((User) authentication.getPrincipal());
        return ResponseEntity.status(201).body(individualListMapper.mapToDto(individualListsService.createList(individualList)));
    }

    @PostMapping("/{listId}/restaurants/{restaurantId}")
    @Operation(summary = "Add restaurant to list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant added"),
                    @ApiResponse(responseCode = "403", description = "User does not own this list", content = @Content),
                    @ApiResponse(responseCode = "404", description = "List or restaurant not found", content = @Content)
            })
    @UserRoleDescription
    public IndividualListDto addRestaurantToList(@PathVariable UUID listId, @PathVariable UUID restaurantId, Authentication authentication) {
        individualListsService.throwIfUserDoesNotOwnList(listId, ((User) authentication.getPrincipal()).getId());

        return individualListMapper.mapToDto(individualListsService.addRestaurantToList(listId, restaurantId));
    }

    @DeleteMapping("/{listId}/restaurants/{restaurantId}")
    @Operation(summary = "Remove restaurant from list",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Restaurant removed"),
                    @ApiResponse(responseCode = "403", description = "User does not own this list", content = @Content),
                    @ApiResponse(responseCode = "404", description = "List or restaurant not found", content = @Content)
            })
    @UserRoleDescription
    public IndividualListDto removeRestaurantFromList(@PathVariable UUID listId, @PathVariable UUID restaurantId, Authentication authentication) {
        individualListsService.throwIfUserDoesNotOwnList(listId, ((User) authentication.getPrincipal()).getId());

        return individualListMapper.mapToDto(individualListsService.removeRestaurantFromList(listId, restaurantId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete list by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "List deleted", content = @Content),
                    @ApiResponse(responseCode = "403", description = "User does not own this list", content = @Content),
                    @ApiResponse(responseCode = "404", description = "List not found", content = @Content)
            })
    @UserRoleDescription
    public ResponseEntity<?> deleteList(@PathVariable UUID id, Authentication authentication) {
        individualListsService.throwIfUserDoesNotOwnList(id, ((User) authentication.getPrincipal()).getId());

        individualListsService.deleteListById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modify list by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List modified"),
                    @ApiResponse(responseCode = "403", description = "User does not own this list", content = @Content),
                    @ApiResponse(responseCode = "404", description = "List not found", content = @Content)
            })
    @UserRoleDescription
    public IndividualListDto modifyList(@PathVariable UUID id, @RequestBody IndividualListRequest individualListRequest, Authentication authentication) {
        individualListsService.throwIfUserDoesNotOwnList(id, ((User) authentication.getPrincipal()).getId());

        return individualListMapper.mapToDto(individualListsService.modifyList(id, individualListRequest));
    }
}
