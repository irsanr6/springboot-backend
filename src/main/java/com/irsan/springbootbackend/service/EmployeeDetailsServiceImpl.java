package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (employee.isPresent()) {
            return new User(employee.get().getUsername(), employee.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Employee not found with username: " + usernameOrEmail);
        }
    }
}
