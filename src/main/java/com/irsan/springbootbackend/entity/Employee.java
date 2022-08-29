package com.irsan.springbootbackend.entity;

//import com.irsan.springbootbackend.listener.EmployeeAuditTrailListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
//@EntityListeners(EmployeeAuditTrailListener.class)
@Entity
@Table(schema = "db_localhost", name = "m_employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String username;
    private String password;
    @Column(name = "encode_password")
    private String encodePassword;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "created_at")
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Column(name = "updated_at")
    private Date updatedAt;
    @Column(name = "updated_by")
    private Long updatedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id",
            insertable = false, updatable = false)
    private DataEmployee dataEmployee;

}
