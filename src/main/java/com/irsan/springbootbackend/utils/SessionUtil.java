package com.irsan.springbootbackend.utils;

import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    public static String getUserData(HttpServletRequest request) {
        return (String) request.getAttribute(Constant.HEADER_DATA);
    }
}
