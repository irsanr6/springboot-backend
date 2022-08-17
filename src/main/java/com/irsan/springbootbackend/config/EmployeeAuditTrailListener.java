package com.irsan.springbootbackend.config;

import com.irsan.springbootbackend.entity.Employee;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Slf4j
public class EmployeeAuditTrailListener {

    public static Employee dataAjah(String message, Employee employee) {
        Employee emp = employee;
        log.info(message, emp);
        return emp;
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    public static void beforeAnyUpdate(Employee employee) {
        Employee emp = Employee.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
        dataAjah("add/update/delete complete for dataAjah: {}", emp);
        log.info("add/update/delete complete for Employee: {}", emp);
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    public static void afterAnyUpdate(Employee employee) {
        Employee emp = Employee.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
        dataAjah("[dataAjah AUDIT] add/update/delete complete for dataAjah: {}", emp);
        log.info("[Employee AUDIT] add/update/delete complete for Employee: {}", emp);
    }

    @PostLoad
    public static void afterLoad(Employee employee) {
        Employee emp = Employee.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .build();
        dataAjah("[dataAjah AUDIT] dataAjah loaded from database: {}", emp);
        log.info("[Employee AUDIT] Employee loaded from database: {}", emp);
    }

}
