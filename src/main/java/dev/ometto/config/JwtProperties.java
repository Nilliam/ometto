package dev.ometto.config;


import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.crypto.SecretKey;
import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private SecretKey secretKey;

    private JWSAlgorithm algorithm;

    private Duration expiresIn;

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public void setKey(String key) {
        var jwk =  new OctetSequenceKey.Builder(key.getBytes())
                .algorithm(algorithm)
                .build();
        this.secretKey = jwk.toSecretKey();
    }

    public JWSAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = JWSAlgorithm.parse(algorithm);
    }

    public Duration getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Duration expiresIn) {
        this.expiresIn = expiresIn;
    }
}
