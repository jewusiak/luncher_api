package pl.jewusiak.luncher_api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import pl.jewusiak.luncher_api.auth.models.RefreshTokenRequest;
import pl.jewusiak.luncher_api.auth.models.RegistrationRequest;
import pl.jewusiak.luncher_api.auth.models.SignInRequest;
import pl.jewusiak.luncher_api.auth.models.SignInResponse;
import pl.jewusiak.luncher_api.configs.jwt.JwtResponse;
import pl.jewusiak.luncher_api.configs.jwt.JwtUtils;
import pl.jewusiak.luncher_api.users.models.UserDto;
import pl.jewusiak.luncher_api.users.models.UserMapper;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Authentication and authorization")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    @Operation(summary = "Register new user",
            description = "Returns user data with id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered"),
                    @ApiResponse(responseCode = "409", description = "Username or email already taken", content = @Content)
            })
    public UserDto register(@RequestBody RegistrationRequest request) {
        return userMapper.mapUserToDto(authService.register(request));
    }

    @PostMapping("/signin")
    @Operation(summary = "Sign in",
            description = "Returns user data with id. If request.useCookies is true, returns access token in a httpOnly cookie",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User signed in"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
            })
    public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {
        SignInResponse response = authService.signIn(request);
        if (request.getUseCookies() == null || !request.getUseCookies()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtUtils.generateResponseCookie(response.getTokens()
                            .getAccessToken()).toString())
                    .body(response.getUser());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token",
            description = "Returns new access token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access token refreshed"),
                    @ApiResponse(responseCode = "401", description = "Invalid refresh token", content = @Content)
            })
    public JwtResponse refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        return authService.refreshAccessToken(request.getRefreshToken());
    }

    @DeleteMapping("/logout")
    @Operation(summary = "Logout",
            description = "Returns empty response with httpOnly cookie with empty access token. Use only with useCookies=true in login request.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "User logged out", content = @Content)
            })
    public ResponseEntity<?> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, jwtUtils.generateEmptyResponseCookie().toString())
                .build();
    }

    @GetMapping("/oauth2/loginsuccess")
    @Operation(summary = "OAuth2 login success",
            description = "Returns user data with id. Creates new user if they do not exist.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User signed in"),
                    @ApiResponse(responseCode = "500", description = "Internal server error - eg. invalid OAuth response from auth server", content = @Content)
            })
    public ResponseEntity<?> oAuthLoginRedirect(OAuth2AuthenticationToken authenticationToken) {
        Map<String, Object> attributes = authenticationToken.getPrincipal().getAttributes();
        if (attributes.get("email") instanceof String email && attributes.get("name") instanceof String fullname) {
            return ResponseEntity.ok(authService.signInOrRegisterOAuth(email, fullname));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
