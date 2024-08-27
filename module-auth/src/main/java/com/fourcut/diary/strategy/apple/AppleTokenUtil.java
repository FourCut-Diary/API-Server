package com.fourcut.diary.strategy.apple;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AppleTokenUtil {

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
}
