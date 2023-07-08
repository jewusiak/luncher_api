package pl.jewusiak.luncher_api.configs.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${pl.jewusiak.luncher_api.jwtAccessSecret}")
    private String jwtAccessSecret;

    @Value("${pl.jewusiak.luncher_api.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${pl.jewusiak.luncher_api.jwtAccessLifetime}")
    private int jwtAccessLifetime;

    @Value("${pl.jewusiak.luncher_api.jwtRefreshLifetime}")
    private int jwtRefreshLifetime;

    @Value("${pl.jewusiak.luncher_api.httpOnlyCookieName}")
    private String jwtCookieName;


    public String generateAccessTokenFromUsername(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + jwtAccessLifetime))
                   .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                   .compact();
    }
    public String generateRefreshTokenFromUsername(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date())
                   .setExpiration(new Date(new Date().getTime() + jwtRefreshLifetime))
                   .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                   .compact();
    }


    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(jwtAccessSecret.getBytes());
    }
    private Key getRefreshKey() {
        return Keys.hmacShaKeyFor(jwtRefreshSecret.getBytes());
    }

    /*
     * Throws *Jwt* exceptions if expired/invalid etc. (.parseClaimsJws() method).
     * TODO: implement verfication method to catch those exceptions and log messages to stderr.
     */
    public String getUsernameFromAccessToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getAccessKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();

    }

    public String getUsernameFromRefreshToken(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getRefreshKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();

    }

    public ResponseCookie generateResponseCookie(String jwt){
        return ResponseCookie.from(jwtCookieName, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
    }


    public String getTokenFromHeaders(HttpServletRequest request) {
        //"Bearer ".length = 7
        String header = request.getHeader("Authorization");
        return header == null ? null : header.substring(7);
    }

    public String getTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        return cookie == null ? null : cookie.getValue();
    }

    public ResponseCookie generateEmptyResponseCookie() {
        return ResponseCookie.from(jwtCookieName, null).path("/").build();
    }
}
