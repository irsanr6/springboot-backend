package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataEmployeeUpdateService {

    private final EmployeeRepository employeeRepository;
    private final DataEmployeeRepository dataEmployeeRepository;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Jakarta")
    public void dataEmployeeUpdate() {
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee :
                employeeList) {
            Optional<DataEmployee> dataEmployee = dataEmployeeRepository.findByEmployeeId(employee.getEmployeeId());
            if (!dataEmployee.isPresent()) {
                DataEmployee dataEmpCreate = DataEmployee.builder()
                        .employeeId(employee.getEmployeeId())
                        .address("")
                        .phoneNumber("")
                        .nik("")
                        .isAktif("")
                        .position("")
                        .build();
                DataEmployee dataEmpSave = dataEmployeeRepository.save(dataEmpCreate);

                Helper.ok("Data berhasil ditambah", EmployeeResponse.builder()
                        .employeeId(employee.getEmployeeId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .fullName(employee.getFirstName() + " " + employee.getLastName())
                        .email(employee.getEmail())
                        .address(dataEmpSave.getAddress())
                        .phoneNumber(dataEmpSave.getPhoneNumber())
                        .nik(dataEmpSave.getNik())
                        .isAktif(dataEmpSave.getIsAktif())
                        .position(dataEmpSave.getPosition())
                        .build());
            }
        }
        Helper.ok("Semua data telah disimpan");
    }

}
