package com.fourcut.diary.filter;

import com.fourcut.diary.constant.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorMessage.EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            setErrorResponse(response, ErrorMessage.MALFORMED_TOKEN);
        } catch (IllegalArgumentException | JwtException e) {
            setErrorResponse(response, ErrorMessage.INVALID_JWT_TOKEN);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorMessage errorMessage) {

        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorMessage.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            response.getWriter().write(errorMessage.getMessage());
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }
}
