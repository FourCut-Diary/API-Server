package com.fourcut.diary.strategy.apple;

import com.fourcut.diary.client.apple.AppleAuthClient;
import com.fourcut.diary.client.apple.dto.AppleUserResponse;
import com.fourcut.diary.constant.StringConstant;
import com.fourcut.diary.strategy.SocialStrategy;
import com.fourcut.diary.strategy.dto.SocialLoginRequest;
import com.fourcut.diary.strategy.dto.SocialLoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;

import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.Security;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
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
    public SocialLoginResponse login(SocialLoginRequest request) {

        checkIdentityTokenIsNull(request.identityToken());

        Claims claims = appleTokenUtil.getClaimsByIdentityToken(request.identityToken());
        String appleAccessToken = getAccessToken(request.authorizationCode());
        AppleUserResponse appleUser = appleTokenUtil.decodePayload(appleAccessToken, AppleUserResponse.class);
        System.out.println(appleUser.email());

        return new SocialLoginResponse(1L);
    }

    private String getAccessToken(String authorizationCode) {

        return appleAuthClient.getOAuth2Token(
                APPLE_CLIENT_ID,
                generateClientSecret(),
                APPLE_GRANT_TYPE,
                authorizationCode
        ).accessToken();
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
        } catch (Exception e) {
            throw new RuntimeException("Error converting private key from String", e);
        }
    }

    private void checkIdentityTokenIsNull(String identityToken) {
        if (identityToken == null) {
            throw new InvalidParameterException(StringConstant.NULL_IDENTITY_TOKEN);
        }
    }
}
