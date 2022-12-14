package com.irsan.springbootbackend.repository;

import com.irsan.springbootbackend.entity.CronJobTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Repository
public interface CronJobTriggerRepository extends JpaRepository<CronJobTrigger, String> {
    Optional<CronJobTrigger> findByCodeTrigger(String codeTrigger);
}
