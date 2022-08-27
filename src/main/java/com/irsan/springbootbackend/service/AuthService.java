package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.SignInRequest;
import com.irsan.springbootbackend.model.SignUpRequest;
import com.irsan.springbootbackend.model.SignUpResponse;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmployeeRepository employeeRepository;

    public BaseResponse<?> authenticateUser(SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsernameOrEmail(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return BaseResponse.ok("User signed-in successfully!.");
        } catch (BadCredentialsException exception) {
            return BaseResponse.error("User signed-in failed!.", exception.getMessage());
        }
    }

    public BaseResponse<?> registerUser(SignUpRequest signUpRequests) {
        if (employeeRepository.findByUsername(signUpRequests.getUsername()).isPresent()) {
            return BaseResponse.error200("Username is already taken!");
        }

        if (employeeRepository.findByEmail(signUpRequests.getEmail()).isPresent()) {
            return BaseResponse.error200("Email is already taken!");
        }

        Employee employee = Employee.builder()
                .firstName(signUpRequests.getFirstName())
                .lastName(signUpRequests.getLastName())
                .email(signUpRequests.getEmail())
                .username(signUpRequests.getUsername())
                .password(passwordEncoder.encode(signUpRequests.getPassword()))
                .build();

        Employee save = employeeRepository.save(employee);

        SignUpResponse response = SignUpResponse.builder()
                .employeeId(save.getEmployeeId())
                .firstName(save.getFirstName())
                .lastName(save.getLastName())
                .email(save.getEmail())
                .username(save.getUsername())
                .build();

        return BaseResponse.ok("User registered successfully", response);
    }
}
