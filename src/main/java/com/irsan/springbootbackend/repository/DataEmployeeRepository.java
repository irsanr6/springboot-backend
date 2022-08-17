package com.irsan.springbootbackend.repository;

import com.irsan.springbootbackend.entity.DataEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Repository
public interface DataEmployeeRepository extends JpaRepository<DataEmployee, String> {
    Optional<DataEmployee> findByEmployeeId(Long employeeId);
}
