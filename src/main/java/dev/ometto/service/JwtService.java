package dev.ometto.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.ometto.config.JwtProperties;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateJwt() {
        var header = new JWSHeader(jwtProperties.getAlgorithm());
        var claimsSet = buildClaims();

        var jwt = new SignedJWT(header, claimsSet);

        try {
            jwt.sign(new MACSigner(jwtProperties.getSecretKey()));
        } catch (JOSEException e) {
            throw new RuntimeException("Failed to generate JWT", e);
        }

        return jwt.serialize();
    }

    private JWTClaimsSet buildClaims() {
        var issuedAt = Instant.now();
        var expiration = issuedAt.plus(jwtProperties.getExpiresIn());

        var builder = new JWTClaimsSet.Builder()
                .issuer(jwtProperties.getIssuer())
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expiration));

        return builder.build();
    }

}
