package com.fourcut.diary.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fourcut.diary.constant.ErrorMessage;
import com.fourcut.diary.dto.ErrorResponse;
import com.fourcut.diary.exception.model.UnauthorizedException;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            setErrorResponse(response, e.getErrorMessage());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorMessage errorMessage) {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorMessage.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.of(errorMessage);

        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error("Error writing response: {}", e.getMessage());
        }
    }
}
