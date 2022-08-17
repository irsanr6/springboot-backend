package com.irsan.springbootbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Entity
@Table(schema = "db_localhost", name = "m_data_employee")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataEmployee {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;
    private String address;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String nik;
    @Column(name = "is_aktif")
    private String isAktif;
    private String position;

}
