package com.irsan.springbootbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String username;
    private String address;
    private String phoneNumber;
    private String nik;
    private String isAktif;
    private String position;

}
