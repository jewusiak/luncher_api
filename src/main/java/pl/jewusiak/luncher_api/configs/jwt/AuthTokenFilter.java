package pl.jewusiak.luncher_api.configs.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;
import pl.jewusiak.luncher_api.exceptions.UserAccountLockedException;
import pl.jewusiak.luncher_api.exceptions.UserAccountNotConfirmedException;
import pl.jewusiak.luncher_api.users.UsersService;
import pl.jewusiak.luncher_api.users.models.User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UsersService usersService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // We authenticate using token in Authorization header - first; if not successful we use token from cookies.
            String jwt = jwtUtils.getTokenFromHeaders(request);
            if (jwt == null) {
                jwt = jwtUtils.getTokenFromCookies(request);
                if (jwt == null)
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please provide auth token.");
            }
            String email;
            try {
                email = jwtUtils.getUsernameFromAccessToken(jwt);
            } catch (ExpiredJwtException e) {
                response.sendError(460);
                return;
            }
            User user = usersService.getUserByEmail(email);
            if (!user.isEnabled()) throw new UserAccountNotConfirmedException();
            if (user.isLocked()) throw new UserAccountLockedException();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            logger.error("Cannot authenticate: {}", e);
        }

        filterChain.doFilter(request, response);
    }

}
