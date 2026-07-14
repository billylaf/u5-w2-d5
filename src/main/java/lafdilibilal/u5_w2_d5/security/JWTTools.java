package lafdilibilal.u5_w2_d5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    private final String secret;


    public JWTTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;

    }

    public String generateToken(Dipendente dipendente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .subject(String.valueOf(dipendente.getUsername()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("ci sono stati problemi con il token rieffettuare login");
        }
    }
}