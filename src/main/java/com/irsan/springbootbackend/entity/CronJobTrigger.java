package com.irsan.springbootbackend.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Entity
@Table(schema = "db_localhost", name = "cron_job_trigger")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CronJobTrigger {

    @Id
    @Column(name = "cron_value")
    private String cronValue;
    @Column(name = "code_trigger")
    private String codeTrigger;

}
