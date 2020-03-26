package springbootboilerplate.application.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTTokenStore {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    static final String AUTHORITIES_CLAIM_KEY = "roles";

    AbstractAuthenticationToken buildAuthentication(String token) {
        Claims claims = getClaimsFromToken(token);
        List<String> roles = claims.get(AUTHORITIES_CLAIM_KEY, List.class);

        List<SimpleGrantedAuthority> grantedAuthorities = roles.stream().map(
                SimpleGrantedAuthority::new
        ).collect(Collectors.toList());

        User user = new User(claims.getSubject(), "", grantedAuthorities);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                token,
                grantedAuthorities
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
            throw new BadCredentialsException("invalid token");
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
        claims.put(AUTHORITIES_CLAIM_KEY,
                userDetails.getAuthorities().stream().map(
                        GrantedAuthority::getAuthority
                ).collect(Collectors.toList())
        );
        return claims;
    }

    private Date buildExpiration() {
        Calendar expirationCalendar = new GregorianCalendar();
        expirationCalendar.add(Calendar.MINUTE, expiration);
        return expirationCalendar.getTime();
    }
}
