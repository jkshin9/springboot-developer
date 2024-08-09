package me.shinsunyoung.springbootdeveloper.config.jwt;


import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;


@Service
@Getter
public class TokenProvider {

    private final String issuer = "jkshin9@plateer.com";
    private final String secretKey = "springboot000jwt000secretKey000springboot000jwt000secretKey000springboot000jwt000secretKey";

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }


    private String makeToken(Date expiredAt, User user) {

        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiredAt)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
                ;
    }

    public boolean validToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {

        Claims claims = getClames(token);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.
                                                            security.core.userdetails.User(claims.getSubject(),
                                                            "", authorities), token, authorities);
    }

    public Long getUserId(String token) {

        Claims claims = getClames(token);
        return claims.get("id", Long.class);
    }

    private Claims getClames(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
