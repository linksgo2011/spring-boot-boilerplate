package springbootboilerplate.modules.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class JWTTokenStore {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    static final String AUTHORITIES_CLAIM_KEY = "roles";

    AbstractAuthenticationToken buildAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                token,
                claims.get(AUTHORITIES_CLAIM_KEY, Collection.class)
        );
        return authToken;
    }

    Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Claims claims = generateClaimsFromUser(userDetails);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private Claims generateClaimsFromUser(UserDetails userDetails) {
        Claims claims = new DefaultClaims();
        claims.setSubject(userDetails.getUsername());
        claims.setExpiration(buildExpiration());
        claims.put(AUTHORITIES_CLAIM_KEY, userDetails.getAuthorities());
        return claims;
    }

    private Date buildExpiration() {
        Calendar expirationCalendar = new GregorianCalendar();
        expirationCalendar.add(Calendar.MINUTE, expiration);
        return expirationCalendar.getTime();
    }
}
