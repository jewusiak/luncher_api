package pl.jewusiak.luncher_api.users;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.lists.models.IndividualListDto;
import pl.jewusiak.luncher_api.lists.models.IndividualListMapper;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.users.models.UserDto;
import pl.jewusiak.luncher_api.users.models.UserMapper;
import pl.jewusiak.luncher_api.users.models.UserRequest;
import pl.jewusiak.luncher_api.utils.UserRoleDescription;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Operations related to user's profile")
public class ProfileController {
    private final UsersService usersService;
    private final UserMapper userMapper;
    private final IndividualListMapper individualListMapper;

    @GetMapping("/lists")
    @Operation(summary = "Get all lists owned by user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lists found")
            })
    @UserRoleDescription
    public List<IndividualListDto> getUsersLists(Authentication authentication) {
        return individualListMapper.mapAllToDto(((User) authentication.getPrincipal()).getIndividualLists());
    }

    @GetMapping
    @Operation(summary = "Get my profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile found")
            })
    @UserRoleDescription
    public UserDto getMyProfile(Authentication authentication) {
        return userMapper.mapUserToDto((User) authentication.getPrincipal());
    }

    @PutMapping
    @Operation(summary = "Update my profile",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile updated")
            })
    @UserRoleDescription
    public UserDto updateMyProfile(@RequestBody UserRequest changes, Authentication authentication) {
        return userMapper.mapUserToDto(usersService.modifyUserProfile(((User) authentication.getPrincipal()).getId(), changes));
    }
}
