package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.*;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import com.irsan.springbootbackend.utils.CompressionUtil;
import com.irsan.springbootbackend.utils.Helper;
import com.irsan.springbootbackend.utils.JwtTokenUtil;
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

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    public BaseResponse<?> authenticateUser(SignInRequest signInRequest) {
        getStringBaseResponse(signInRequest);
        final Employee employee = employeeRepository.findByUsernameOrEmail(signInRequest.getUsernameOrEmail(), signInRequest.getUsernameOrEmail()).get();
        EmployeeData employeeData = EmployeeData.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fullName(Helper.fullName(employee.getFirstName(), employee.getLastName()))
                .email(employee.getEmail())
                .username(employee.getUsername())
                .address(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getAddress).orElse("-"))
                .phoneNumber(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getPhoneNumber).orElse("-"))
                .nik(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getNik).orElse("-"))
                .isAktif(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getIsAktif).orElse("-"))
                .position(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getPosition).orElse("-"))
                .build();
        final String token = jwtTokenUtil.generateToken(employeeData);
        return BaseResponse.ok(UserAccessResponse.builder()
                .userAccess(UserAccessResponse.UserAccess.builder()
                        .fullName(employeeData.getFullName())
                        .username(employeeData.getUsername())
                        .email(employeeData.getEmail())
                        .build())
                .token(token)
                .build());
    }

    private void getStringBaseResponse(SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsernameOrEmail(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            BaseResponse.ok("User signed-in successfully!.");
        } catch (BadCredentialsException exception) {
            BaseResponse.error("User signed-in failed!.", exception.getMessage());
        }
    }

    public BaseResponse<?> registerUser(SignUpRequest signUpRequests) throws IOException {
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
                .encodePassword(CompressionUtil.compressB64(signUpRequests.getPassword()))
                .createdAt(Helper.currentDate())
                .updatedAt(Helper.currentDate())
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

    public BaseResponse<?> checkPassword(String employeeId, String searchGlobal) throws IOException {
        Map<String, String> mapRes = new HashMap<>();
        List<Map<String, String>> listMapRes = new ArrayList<>();
        if (StringUtils.isEmpty(employeeId) && StringUtils.isEmpty(searchGlobal)) {
            return BaseResponse.error("Error, no data to present", listMapRes);
        }
        if (StringUtils.isEmpty(employeeId)) {
            return getBaseResponse(searchGlobal, mapRes, listMapRes);
        }
        if (StringUtils.isEmpty(searchGlobal)) {
            employeeRepository.findByEmployeeId(Long.valueOf(employeeId)).ifPresent(optEmployee -> {
                try {
                    mapRes.put(optEmployee.getUsername(), CompressionUtil.decompressB64(optEmployee.getEncodePassword()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            if (Helper.isNullOrEmptyMap(mapRes)) {
                return BaseResponse.error("Error, no data to present", listMapRes);
            }
            listMapRes.add(mapRes);
            return BaseResponse.ok(listMapRes);

        }
        employeeRepository.findByEmployeeId(Long.valueOf(employeeId)).ifPresent(optEmployee -> {
            try {
                mapRes.put(optEmployee.getUsername(), CompressionUtil.decompressB64(optEmployee.getEncodePassword()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return getBaseResponse(searchGlobal, mapRes, listMapRes);
    }

    private BaseResponse<?> getBaseResponse(String searchGlobal,
                                            Map<String, String> mapRes,
                                            List<Map<String, String>> listMapRes) throws IOException {
        List<Employee> employeeList = getEmployees(searchGlobal);
        for (Employee emp :
                employeeList) {
            mapRes.put(emp.getUsername(), CompressionUtil.decompressB64(emp.getEncodePassword()));
        }
        if (Helper.isNullOrEmptyMap(mapRes)) {
            return BaseResponse.error("Error, no data to present", listMapRes);
        }
        listMapRes.add(mapRes);
        return BaseResponse.ok(listMapRes);
    }

    private List<Employee> getEmployees(String searchGlobal) {
        return employeeRepository.findByEmailContainingOrFirstNameContainingOrLastNameContainingOrUsernameContaining(searchGlobal, searchGlobal, searchGlobal, searchGlobal);
    }
}
