package com.fourcut.diary.jwt;

import com.fourcut.diary.auth.service.token.RefreshTokenService;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.UnauthorizedException;
import com.fourcut.diary.filter.CustomUserDetailsService;
import com.fourcut.diary.user.domain.RoleType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenManager {

    private final Key key;
    private final long accessTokenExpTime;
    private final long refreshTokenExpTime;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String TOKEN_TYPE_KEY = "type";
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    private final CustomUserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    public JwtTokenManager(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token.expiration-time}") long accessTokenExpTime,
            @Value("${jwt.refresh-token.expiration-time}") long refreshTokenExpTime,
            CustomUserDetailsService userDetailsService,
            RefreshTokenService refreshTokenService
    ) {
        this.accessTokenExpTime = accessTokenExpTime;
        this.refreshTokenExpTime = refreshTokenExpTime;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
    }

    public JwtToken getUserJwtToken(String principle) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principle, null, List.of(new SimpleGrantedAuthority(RoleType.USER.name())));
        return createToken(authenticationToken);
    }

    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);

        if (claims == null || claims.get(AUTHORITIES_KEY) == null || claims.get(TOKEN_TYPE_KEY) == null) {
            throw new RuntimeException("정보가 누락된 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private JwtToken createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = generateToken(authentication.getName(), authorities, ACCESS_TOKEN_TYPE, accessTokenExpTime);
        String refreshToken = generateToken(authentication.getName(), authorities, REFRESH_TOKEN_TYPE, refreshTokenExpTime);
        refreshTokenService.createRefreshToken(refreshToken, refreshTokenExpTime);

        return new JwtToken(accessToken, refreshToken);
    }


    public boolean validateToken(String token) {
        try {

            if (token == null || token.isBlank()) {
                throw new UnauthorizedException(ErrorMessage.INVALID_JWT_TOKEN);
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException(ErrorMessage.MALFORMED_TOKEN);
        } catch (IllegalArgumentException | JwtException e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_JWT_TOKEN);
        }
    }

    private String generateToken(String socialId, String authorities, String tokenType, Long expiration) {

        long now = (new Date()).getTime();
        Date expirationDate = new Date(now + expiration);

        return Jwts.builder()
                .setSubject(socialId)
                .claim(AUTHORITIES_KEY, authorities)
                .claim(TOKEN_TYPE_KEY, tokenType)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expirationDate)
                .compact();
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
