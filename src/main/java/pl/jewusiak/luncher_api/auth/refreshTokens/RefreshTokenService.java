package pl.jewusiak.luncher_api.auth.refreshTokens;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.luncher_api.configs.jwt.JwtUtils;
import pl.jewusiak.luncher_api.users.models.User;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository repository;

    public String validateRefreshTokenAndGetEmail(String token) {
        String email =  jwtUtils.getUsernameFromRefreshToken(token); //checks if JWT is valid and non-expired
        if(repository.findById(token).isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return email;
    }

    public String generateRefreshToken(User user) {
        String token = jwtUtils.generateRefreshTokenFromUsername(user.getUsername());
        repository.save(new RefreshToken(token, user));
        return token;
    }
}
