package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.model.SignInRequest;
import com.irsan.springbootbackend.model.SignUpRequest;
import com.irsan.springbootbackend.service.AuthService;
import com.irsan.springbootbackend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public BaseResponse<?> authenticateUser(@RequestBody SignInRequest signInRequest) throws BadCredentialsException {
        return authService.authenticateUser(signInRequest);
    }

    @PostMapping("/signup")
    public BaseResponse<?> registerUser(@RequestBody SignUpRequest signUpRequests) {
        return authService.registerUser(signUpRequests);
    }
}
