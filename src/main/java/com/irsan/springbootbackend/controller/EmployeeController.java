package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.model.EmployeeGetRequest;
import com.irsan.springbootbackend.model.EmployeeSaveRequest;
import com.irsan.springbootbackend.service.EmployeeService;
import com.irsan.springbootbackend.utils.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/getAll")
    public BaseResponse<?> getAllEmployee(@RequestBody EmployeeGetRequest request,
                                          HttpServletRequest httpRequest) {
        return employeeService.getAllEmployee(request, httpRequest);
    }

    @PostMapping("/save")
    public BaseResponse<?> saveEmployee(@RequestBody EmployeeSaveRequest request) {
        return employeeService.saveEmployee(request);
    }

}
