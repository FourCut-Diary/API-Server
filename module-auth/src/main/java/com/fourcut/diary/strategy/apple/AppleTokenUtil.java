package com.fourcut.diary.strategy.apple;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourcut.diary.client.apple.AppleAuthClient;
import com.fourcut.diary.client.apple.dto.ApplePublicKey;
import com.fourcut.diary.client.apple.dto.ApplePublicKeyResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleTokenUtil {

    private final AppleAuthClient appleAuthClient;

    public Claims getClaimsByIdentityToken(String identityToken) {
        try {
            ApplePublicKeyResponse response = appleAuthClient.getApplePublicKey();
            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = stringHeaderToMap(headerOfIdentityToken);
            ApplePublicKey applePublicKey = response.getMatchedKey(header.get("kid"), header.get("alg"));

            byte[] nBytes = Base64.getUrlDecoder().decode(applePublicKey.n());
            byte[] eBytes = Base64.getUrlDecoder().decode(applePublicKey.e());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(applePublicKey.kty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(identityToken)
                    .getBody();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | AuthenticationException exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    public <T> T decodePayload(String token, Class<T> targetClass) {

        String[] tokenParts = token.split("\\.");
        String payloadJWT = tokenParts[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(payloadJWT));
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return objectMapper.readValue(payload, targetClass);
        } catch (Exception e) {
            throw new RuntimeException("Error decoding token payload", e);
        }
    }

    private Map<String, String> stringHeaderToMap(String headerOfIdentityToken) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(headerOfIdentityToken);
            String decodedString = new String(decodedBytes);

            return new ObjectMapper().readValue(decodedString, new TypeReference<>() {
            });
        } catch (Exception exception) {
            throw new RuntimeException("Error decoding token header", exception);
        }
    }
}
