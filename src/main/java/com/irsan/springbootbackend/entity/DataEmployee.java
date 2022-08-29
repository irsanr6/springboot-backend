package com.irsan.springbootbackend.entity;

//import com.irsan.springbootbackend.listener.DataEmployeeAuditTrailListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import javax.persistence.*;
import java.util.Date;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
//@EntityListeners(DataEmployeeAuditTrailListener.class)
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "updated_by")
    private Long updatedBy;

}
