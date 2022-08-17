package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.model.EmployeeGetRequest;
import com.irsan.springbootbackend.model.EmployeeSaveRequest;
import com.irsan.springbootbackend.service.EmployeeService;
import com.irsan.springbootbackend.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/getAll")
    public BaseResponse<?> getAllEmployee(@RequestBody EmployeeGetRequest request) {
        return employeeService.getAllEmployee(request);
    }

    @PostMapping("/save")
    public BaseResponse<?> saveEmployee(@RequestBody EmployeeSaveRequest request) {
        return employeeService.saveEmployee(request);
    }

}
