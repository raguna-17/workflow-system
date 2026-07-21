package workflow.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import workflow.domains.user.Role;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;



@Component
public class JwtProvider {


    /**
     * 本番では application.yml から取得する
     */
    private static final String SECRET =
            "your-secret-key-must-be-at-least-32-characters";


    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(StandardCharsets.UTF_8)
            );



    /**
     * アクセストークン有効期限
     * 30分
     */
    private static final long ACCESS_TOKEN_EXPIRE =
            1000 * 60 * 30;



    /**
     * リフレッシュトークン有効期限
     * 7日
     */
    private static final long REFRESH_TOKEN_EXPIRE =
            1000L * 60 * 60 * 24 * 7;




    /**
     * Access Token発行
     */
    public String generateAccessToken(

            String email,

            Role role

    ) {


        Date now = new Date();



        return Jwts.builder()

                // UserDetails.getUsername() = email
                .subject(email)

                .claim(
                        "role",
                        role.name()
                )

                .issuedAt(now)

                .expiration(
                        new Date(
                                now.getTime()
                                + ACCESS_TOKEN_EXPIRE
                        )
                )

                .signWith(key)

                .compact();

    }




    /**
     * Refresh Token発行
     */
    public String generateRefreshToken(

            String email

    ) {


        Date now = new Date();



        return Jwts.builder()

                .subject(email)

                .issuedAt(now)

                .expiration(
                        new Date(
                                now.getTime()
                                + REFRESH_TOKEN_EXPIRE
                        )
                )

                .signWith(key)

                .compact();

    }





    /**
     * JWT有効性確認
     */
    public boolean validateToken(

            String token

    ) {


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





    /**
     * JWTからemail取得
     */
    public String getEmail(

            String token

    ) {


        Claims claims =
                Jwts.parser()

                        .verifyWith(key)

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();



        return claims.getSubject();

    }





    /**
     * JWTからRole取得
     */
    public Role getRole(

            String token

    ) {


        Claims claims =
                Jwts.parser()

                        .verifyWith(key)

                        .build()

                        .parseSignedClaims(token)

                        .getPayload();



        return Role.valueOf(
                claims.get(
                        "role",
                        String.class
                )
        );

    }

}