package com.irsan.springbootbackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Entity
@Table(schema = "db_localhost", name = "m_job_list_active")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobListActive {

    @Id
    @Column(name = "job_id")
    private String jobId;
    @Column(name = "cron_expression")
    private String cronExpression;
    @Column(name = "start_datetime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    private Date startDatetime;

}
