package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.model.EmployeeGetRequest;
import com.irsan.springbootbackend.model.EmployeeSaveRequest;
import com.irsan.springbootbackend.utils.BaseResponse;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
public interface EmployeeService {
    BaseResponse<?> getAllEmployee(EmployeeGetRequest request);

    BaseResponse<?> saveEmployee(EmployeeSaveRequest request);
}
