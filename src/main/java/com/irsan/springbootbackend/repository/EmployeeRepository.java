package com.irsan.springbootbackend.repository;

import com.irsan.springbootbackend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByEmployeeId(Long employeeId);
    Optional<Employee> findByUsernameOrEmail(String username, String email);
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByEmail(String email);

    List<Employee> findByEmailContainingOrFirstNameContainingOrLastNameContainingOrUsernameContaining(String email,
                                                                                                      String firstName,
                                                                                                      String lastName,
                                                                                                      String username);
}
