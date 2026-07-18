package kakeibo.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import kakeibo.user.Role;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    // 本番ではapplication.ymlから読み込む
    private static final String SECRET =
            "your-secret-key-must-be-at-least-32-characters";

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // 30分
    private static final long ACCESS_TOKEN_EXPIRE = 1000 * 60 * 30;

    // 7日
    private static final long REFRESH_TOKEN_EXPIRE = 1000L * 60 * 60 * 24 * 7;


    public String generateAccessToken(String email, Role role) {

        Date now = new Date();

        return Jwts.builder()
                .subject(email)
                .claim("role", role.name())
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() + ACCESS_TOKEN_EXPIRE)
                )
                .signWith(key)
                .compact();
    }


    public String generateRefreshToken(String email){

        Date now = new Date();

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime() + REFRESH_TOKEN_EXPIRE)
                )
                .signWith(key)
                .compact();
    }


    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }


    public String getEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }


    public Role getRole(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Role.valueOf(claims.get("role", String.class));
    }
}