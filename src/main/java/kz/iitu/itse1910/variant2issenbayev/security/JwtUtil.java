package kz.iitu.itse1910.variant2issenbayev.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import kz.iitu.itse1910.variant2issenbayev.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@AllArgsConstructor
public class JwtUtil {
    private final JwtConfigProps jwtProps;

    public String generateToken(User user) {
        // Map.of() is immutable, which causes an exception
        Map<String, Object> claims = new HashMap<>(Map.of("id", user.getId()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(dateNowPlusMilliseconds(jwtProps.getExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, jwtProps.getSecret())
                .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProps.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProps.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    private Date dateNowPlusMilliseconds(long ms) {
        long dateNowMs = new Date().getTime();
        return new Date(dateNowMs + ms);
    }
}
