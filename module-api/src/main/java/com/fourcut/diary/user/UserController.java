package com.fourcut.diary.user;

import com.fourcut.diary.user.dto.request.LoginRequest;
import com.fourcut.diary.user.dto.request.SignupRequest;
import com.fourcut.diary.user.dto.response.LoginResponse;
import com.fourcut.diary.user.dto.response.SignupResponse;
import com.fourcut.diary.user.facade.AuthFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerSwagger {

    private final AuthFacade authFacade;

    @PostMapping("/social-signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Valid final SignupRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authFacade.signup(request));
    }

    @PostMapping("/social-login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid final LoginRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(authFacade.login(request));
    }
}
