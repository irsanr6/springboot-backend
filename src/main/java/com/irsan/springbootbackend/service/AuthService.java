package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.SignInRequest;
import com.irsan.springbootbackend.model.SignUpRequest;
import com.irsan.springbootbackend.model.SignUpResponse;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import com.irsan.springbootbackend.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
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
        String usernameReq = signUpRequests.getUsername();
        String username;
        if (employeeRepository.findByUsername(signUpRequests.getUsername()).isPresent()) {
            return BaseResponse.error200("Username is already taken!");
        }

        if (employeeRepository.findByEmail(signUpRequests.getEmail()).isPresent()) {
            return BaseResponse.error200("Email is already taken!");
        }
        if (StringUtils.isEmpty(signUpRequests.getUsername()) || signUpRequests.getUsername().equals("")) {
            username = StringUtils.lowerCase(String.join(".", signUpRequests.getFirstName(), signUpRequests.getLastName()));
        } else {
            username = StringUtils.lowerCase(usernameReq);
        }

        Employee employee = Employee.builder()
                .firstName(signUpRequests.getFirstName())
                .lastName(signUpRequests.getLastName())
                .email(signUpRequests.getEmail())
                .username(username)
                .password(passwordEncoder.encode(signUpRequests.getPassword()))
                .encodePassword(Helper.encodeString(signUpRequests.getPassword()))
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

    public BaseResponse<?> checkPassword(String employeeId, String searchGlobal) {
        Map<String, String> mapRes = new HashMap<>();
        List<Map<String, String>> listMapRes = new ArrayList<>();
        if (StringUtils.isEmpty(employeeId) && StringUtils.isEmpty(searchGlobal)) {
            return BaseResponse.error("Error, no data to present",listMapRes);
        }
        if (StringUtils.isEmpty(employeeId)) {
            return getBaseResponse(searchGlobal, mapRes, listMapRes);
        }
        if (StringUtils.isEmpty(searchGlobal)) {
            employeeRepository.findByEmployeeId(Long.valueOf(employeeId)).ifPresent(optEmployee -> mapRes.put(optEmployee.getUsername(), Helper.decodeString(optEmployee.getEncodePassword())));
            if (mapRes.isEmpty()) {
                return BaseResponse.error("Error, no data to present",listMapRes);
            }
            listMapRes.add(mapRes);
            return BaseResponse.ok(listMapRes);

        }
        employeeRepository.findByEmployeeId(Long.valueOf(employeeId)).ifPresent(optEmployee -> mapRes.put(optEmployee.getUsername(), Helper.decodeString(optEmployee.getEncodePassword())));
        return getBaseResponse(searchGlobal, mapRes, listMapRes);
    }

    private BaseResponse<?> getBaseResponse(String searchGlobal, Map<String, String> mapRes, List<Map<String, String>> listMapRes) {
        List<Employee> employeeList = getEmployees(searchGlobal);
        for (Employee emp :
                employeeList) {
            mapRes.put(emp.getUsername(), Helper.decodeString(emp.getEncodePassword()));
        }
        if (mapRes.isEmpty()) {
            return BaseResponse.error("Error, no data to present",listMapRes);
        }
        listMapRes.add(mapRes);
        return BaseResponse.ok(listMapRes);
    }

    private List<Employee> getEmployees(String searchGlobal) {
        return employeeRepository.findByEmailContainingOrFirstNameContainingOrLastNameContainingOrUsernameContaining(searchGlobal, searchGlobal, searchGlobal, searchGlobal);
    }
}
