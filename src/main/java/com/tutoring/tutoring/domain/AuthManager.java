package com.tutoring.tutoring.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class AuthManager {

    /**
     * JWT 시크릿키 지정 및 알고리즘 생성
     */
    public final static Algorithm algorithm = Algorithm.HMAC256("JWTSecretKey");
    public final static String issuer = "JTH";

    // JWT 검사
    private final JWTVerifier jwtVerifier = JWT.require(algorithm)
            .withIssuer(issuer)
            .build();

    // token 과 RequestBody userId가 일치하는지 확인
    // extractUserId 메소드로 대체
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

    // JWT userId 추출
    public long extractUserId(String token) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            long tokenId = decodedJWT.getClaim("userId").asLong();
            return tokenId;
        } catch(Exception e) {
            throw e;
        }
    }
}
