package com.irsan.springbootbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Entity
@Table(schema = "db_localhost", name = "employee_gallery")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGallery {

    @Id
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "gallery_name")
    private String galleryName;
    @Column(name = "data_type")
    private String dataType;
    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;
}
