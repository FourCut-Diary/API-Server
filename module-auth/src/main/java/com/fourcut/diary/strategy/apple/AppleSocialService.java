package com.fourcut.diary.strategy.apple;

import com.fourcut.diary.client.apple.AppleAuthClient;
import com.fourcut.diary.client.apple.dto.AppleOAuth2TokenResponse;
import com.fourcut.diary.client.apple.dto.AppleUserResponse;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import feign.FeignException;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppleSocialService implements SocialStrategy {

    @Value("${apple.grant-type}")
    private String APPLE_GRANT_TYPE;

    @Value("${apple.team-id}")
    private String APPLE_TEAM_ID;

    @Value("${apple.client-id}")
    private String APPLE_CLIENT_ID;

    @Value("${apple.key-id}")
    private String APPLE_KEY_ID;

    @Value("${apple.audience}")
    private String APPLE_AUDIENCE;

    @Value("${apple.private-key}")
    private String APPLE_PRIVATE_KEY;

    private final AppleAuthClient appleAuthClient;

    private final AppleTokenUtil appleTokenUtil;

    @Override
    public SocialLoginResponse getSocialInfo(String authorizationCode) {

        AppleOAuth2TokenResponse appleOAuth2TokenResponse = getAccessToken(authorizationCode);
        String appleIdToken = appleOAuth2TokenResponse.idToken();
        AppleUserResponse appleUser = appleTokenUtil.decodePayload(appleIdToken, AppleUserResponse.class);

        return new SocialLoginResponse(appleUser.sub());
    }

    private AppleOAuth2TokenResponse getAccessToken(String authorizationCode) {

        try {
            return appleAuthClient.getOAuth2Token(
                    APPLE_CLIENT_ID,
                    generateClientSecret(),
                    APPLE_GRANT_TYPE,
                    authorizationCode
            );
        } catch (FeignException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String generateClientSecret() {

        Date tokenExpireDate = Date.from(LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, APPLE_KEY_ID)
                .setHeaderParam(JwsHeader.ALGORITHM, "ES256")
                .setIssuer(APPLE_TEAM_ID)
                .setAudience(APPLE_AUDIENCE)
                .setSubject(APPLE_CLIENT_ID)
                .setExpiration(tokenExpireDate)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");

        try {
            byte[] privateKeyBytes = Base64.getDecoder().decode(APPLE_PRIVATE_KEY);

            PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(privateKeyBytes);
            return converter.getPrivateKey(privateKeyInfo);
        } catch (Exception exception) {
            throw new RuntimeException("Error converting private key from String", exception);
        }
    }
}
