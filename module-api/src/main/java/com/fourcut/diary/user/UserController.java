package com.fourcut.diary.user;

import com.fourcut.diary.config.resolver.UserAuthentication;
import com.fourcut.diary.user.dto.request.LoginRequest;
import com.fourcut.diary.user.dto.request.SignupRequest;
import com.fourcut.diary.user.dto.request.TokenRefreshRequest;
import com.fourcut.diary.user.dto.response.LoginResponse;
import com.fourcut.diary.user.dto.response.SignupResponse;
import com.fourcut.diary.user.dto.response.TokenRefreshResponse;
import com.fourcut.diary.user.dto.response.UserProfileResponse;
import com.fourcut.diary.user.facade.AuthFacade;
import com.fourcut.diary.user.mapper.UserResponseMapper;
import com.fourcut.diary.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerSwagger {

    private final AuthFacade authFacade;
    private final UserService userService;

    private final UserResponseMapper userResponseMapper;

    @PostMapping("/social-signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid final SignupRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signup(request));
    }

    @PostMapping("/social-login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authFacade.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(
            @UserAuthentication String socialId,
            @RequestBody @Valid final TokenRefreshRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.refreshToken(socialId, request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@UserAuthentication String socialId) {

        UserProfileResponse response = userResponseMapper.toUserProfileResponse(userService.getUserProfileInfoBySocialId(socialId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
