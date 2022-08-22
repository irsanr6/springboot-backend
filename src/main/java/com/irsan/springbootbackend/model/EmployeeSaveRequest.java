package com.irsan.springbootbackend.model;

import lombok.*;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSaveRequest {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
    private String nik;
    private String isAktif;
    private String position;

}
