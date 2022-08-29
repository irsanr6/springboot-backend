package com.irsan.springbootbackend.utils;

import com.irsan.springbootbackend.model.EmployeeData;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static EmployeeData getEmployeeData(HttpServletRequest request) {
        return (EmployeeData) request.getAttribute(Constant.HEADER_DATA);
    }
}
