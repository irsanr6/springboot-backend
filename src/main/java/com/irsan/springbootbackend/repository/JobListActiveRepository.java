package com.irsan.springbootbackend.repository;

import com.irsan.springbootbackend.entity.JobListActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Repository
public interface JobListActiveRepository extends JpaRepository<JobListActive, String> {
    Optional<JobListActive> findByJobId(String jobId);
}
