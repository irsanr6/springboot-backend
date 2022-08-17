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
public class EmployeeGetRequest {

    private int limit;
    private int pageIn;
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String nik;
    private String position;

}
