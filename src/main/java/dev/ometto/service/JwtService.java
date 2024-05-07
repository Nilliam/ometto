package dev.ometto.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.ometto.config.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateJwt(Authentication authentication) {
        var header = new JWSHeader(jwtProperties.getAlgorithm());
        var claimsSet = buildClaims(authentication);

        var jwt = new SignedJWT(header, claimsSet);

        try {
            jwt.sign(new MACSigner(jwtProperties.getSecretKey()));
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }

        return jwt.serialize();
    }

    private JWTClaimsSet buildClaims(Authentication authentication) {
        var issuedAt = Instant.now();
        var expiration = issuedAt.plus(jwtProperties.getExpiresIn());

        var builder = new JWTClaimsSet.Builder()
                .issuer("self")
                .issueTime(Date.from(issuedAt))
                .subject(authentication.getName())
                .expirationTime(Date.from(expiration));

        return builder.build();
    }

}
