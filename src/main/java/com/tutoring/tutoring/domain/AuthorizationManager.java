package com.tutoring.tutoring.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthorizationManager {

    /**
     * JWT 시크릿키 지정 및 알고리즘 생성
     */
    public static Algorithm algorithm = Algorithm.HMAC256("JWTSecretKey");
    public static String issuer = "JTH";

    // JWT 검사
    private JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    public boolean identify(String token, long userId) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        long tokenId = decodedJWT.getClaim("userId").asLong();
        if(tokenId == userId) {
            return true;
        }
        else {
           return false;
        }
    }
}
