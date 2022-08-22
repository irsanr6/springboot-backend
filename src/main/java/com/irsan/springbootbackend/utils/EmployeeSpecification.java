package com.irsan.springbootbackend.utils;

import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeGetRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
public class EmployeeSpecification {

    public static Specification<Employee> findSpec(EmployeeGetRequest request) {
        return getEmployeeSpecification(request.getEmployeeId(), request.getFirstName(), request.getLastName(), request.getEmail(), request.getAddress(), request.getNik(), request.getPosition());

    }

    private static Specification<Employee> getEmployeeSpecification(String employeeId, String firstName, String lastName, String email, String address, String nik, String position) {
        return (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> p = new ArrayList<>();

            if (StringUtils.isNotBlank(employeeId)) {
                p.add(cb.equal(root.get("employeeId"), Long.parseLong(employeeId)));
            }
            if (StringUtils.isNotBlank(firstName)) {
                p.add(cb.like(root.get("firstName"), "%" + firstName + "%"));
            }
            if (StringUtils.isNotBlank(lastName)) {
                p.add(cb.like(root.get("lastName"), "%" + lastName + "%"));
            }
            if (StringUtils.isNotBlank(email)) {
                p.add(cb.like(root.get("email"), "%" + email + "%"));
            }
            if (StringUtils.isNotBlank(address)) {
                p.add(cb.like(root.get("dataEmployee").get("address"), "%" + address + "%"));
            }
            if (StringUtils.isNotBlank(nik)) {
                p.add(cb.like(root.get("dataEmployee").get("nik"), "%" + nik + "%"));
            }
            if (StringUtils.isNotBlank(position)) {
                p.add(cb.like(root.get("dataEmployee").get("position"), "%" + position + "%"));
            }

            return cb.and(p.toArray(new Predicate[]{}));
        };
    }

}
