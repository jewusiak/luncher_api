package pl.jewusiak.luncher_api.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.jewusiak.luncher_api.auth.models.RegistrationRequest;
import pl.jewusiak.luncher_api.auth.models.SignInRequest;
import pl.jewusiak.luncher_api.auth.models.SignInResponse;
import pl.jewusiak.luncher_api.auth.refreshTokens.RefreshTokenService;
import pl.jewusiak.luncher_api.configs.jwt.JwtResponse;
import pl.jewusiak.luncher_api.configs.jwt.JwtUtils;
import pl.jewusiak.luncher_api.exceptions.NotFoundException;
import pl.jewusiak.luncher_api.users.UsersService;
import pl.jewusiak.luncher_api.users.models.URole;
import pl.jewusiak.luncher_api.users.models.User;
import pl.jewusiak.luncher_api.users.models.UserMapper;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsersService usersService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    public User register(RegistrationRequest registrationRequest) {
        return usersService.createNewUser(userMapper.mapRegistrationRequestToUser(registrationRequest));
    }

    public SignInResponse signIn(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        return signIn(authentication);
    }

    private SignInResponse signIn(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User thisUser = (User) authentication.getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromUsername(thisUser.getUsername());
        String refreshToken = refreshTokenService.generateRefreshToken(thisUser);
        return new SignInResponse(new JwtResponse(accessToken, refreshToken), userMapper.mapUserToDto(thisUser));
    }

    public JwtResponse refreshAccessToken(String refreshToken) {
        String email = refreshTokenService.validateRefreshTokenAndGetEmail(refreshToken); //also validates JWT
        return new JwtResponse(jwtUtils.generateAccessTokenFromUsername(email), refreshToken);
    }

    public SignInResponse signInOrRegisterOAuth(String email, String fullname) {
        User user;
        try {
            user = usersService.getUserByEmail(email);
        } catch (NotFoundException e) {
            user = usersService.createNewUser(User.builder().email(email).fullName(fullname).role(URole.ROLE_CLIENT).build());
        }
        return signIn(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }
}
