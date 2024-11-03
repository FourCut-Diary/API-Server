package com.fourcut.diary.config.resolver;

import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.exception.model.UnauthorizedException;
import com.fourcut.diary.jwt.JwtTokenManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class UserAuthenticationPrincipleResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenManager jwtTokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserAuthentication.class) && String.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final String token = request.getHeader("Authorization").split(" ")[1];

        // 토큰 검증
        if (!jwtTokenManager.validateToken(token)) {
            throw new UnauthorizedException(ErrorMessage.INVALID_JWT_TOKEN);
        }

        // 유저 아이디 반환
        final Authentication authentication = jwtTokenManager.getAuthentication(token);
        return authentication.getName();
    }
}
