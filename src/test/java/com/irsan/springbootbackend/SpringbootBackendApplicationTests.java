package com.irsan.springbootbackend;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class SpringbootBackendApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DataEmployeeRepository dataEmployeeRepository;

    @Test
    void getAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee e :
                employees) {
            log.info("Data {} ", e);
        }
    }

    @Test
    void saveEmployee() {
        String emailExt = "@gmail.com";
        Employee employee1 = Employee.builder()
                .firstName("Hendra")
                .lastName("Prasetyo")
                .email("hendra" + emailExt)
                .build();
        Employee employee2 = Employee.builder()
                .firstName("Ikhsan")
                .lastName("Okto")
                .email("Okto" + emailExt)
                .build();
        Employee employee3 = Employee.builder()
                .firstName("Galang")
                .lastName("Saputra")
                .email("galang" + emailExt)
                .build();

        employeeRepository.saveAll(List.of(employee1, employee2, employee3));
        log.info("Data berhasil ditambahkan");
    }

    @Test
    void currentDateTime() {
        Helper.dateTimeNow();
    }

    @Test
    void threadSleep() {
        log.info("Kita coba");
        try {
            Thread.sleep(30000);
            log.info("Dari Thread sleep");
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void dataEmployeeUpdate() {
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
        Helper.ok("Semua data telah di simpan");
    }
}
