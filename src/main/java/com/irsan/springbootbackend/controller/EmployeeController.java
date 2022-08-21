package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeGetRequest;
import com.irsan.springbootbackend.model.EmployeeMultipleGetRequest;
import com.irsan.springbootbackend.model.EmployeeSaveRequest;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.service.EmployeeService;
import com.irsan.springbootbackend.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/getAll")
    public BaseResponse<?> getAllEmployee(@RequestBody EmployeeGetRequest request) {
        return employeeService.getAllEmployee(request);
    }

    @PostMapping("/save")
    public BaseResponse<?> saveEmployee(@RequestBody EmployeeSaveRequest request) {
        return employeeService.saveEmployee(request);
    }

    @GetMapping("/getAll")
    public BaseResponse<?> getAllByMultipleFilter(EmployeeMultipleGetRequest multipleRequest) {
        return employeeService.getAllByMultipleFilter(multipleRequest);
    }

}
