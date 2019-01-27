package com.networkapps.project.matchmaker.Auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.networkapps.project.matchmaker.Player.Player;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

public class AuthUtil {
    private static final AuthUtil authUtil = new AuthUtil();

    private AuthUtil() {}

    public static AuthUtil getInstance() {
        return authUtil;
    }

    public Map<String, Claim> verifyAndGetClaims(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);

            return jwt.getClaims();
        } catch (JWTVerificationException exception){
            return null;
        }
    }

    public String generateJWT(Player player) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withClaim("player_id", player.getId())
                .withClaim("email", player.getEmail())
                .withIssuer("auth0")
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
        return token;
    }

    private Date generateExpirationDate() {
        Calendar calendar=new GregorianCalendar();
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
}
