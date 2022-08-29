package com.irsan.springbootbackend.filter;

import com.irsan.springbootbackend.model.EmployeeData;
import com.irsan.springbootbackend.service.EmployeeDetailsServiceImpl;
import com.irsan.springbootbackend.utils.Constant;
import com.irsan.springbootbackend.utils.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private EmployeeDetailsServiceImpl employeeDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String usernameOrEmail = null;
        String jwtToken = null;
        EmployeeData employeeData = new EmployeeData();
        String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.replace("Bearer ", "");
            employeeData = jwtTokenUtil.extractToken(jwtToken);
            try {
                usernameOrEmail = employeeData.getUsername();
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (usernameOrEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = employeeDetailsService.loadUserByUsername(usernameOrEmail);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        request.setAttribute(Constant.HEADER_DATA, employeeData);
        filterChain.doFilter(request, response);

    }
}
