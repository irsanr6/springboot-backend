package com.irsan.springbootbackend;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.repository.CronJobTriggerRepository;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.Helper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Slf4j
class SpringbootBackendApplicationTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DataEmployeeRepository dataEmployeeRepository;
    @Autowired
    private CronJobTriggerRepository cronJobTriggerRepository;

    @Autowired
    private EntityManager entityManager;

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
                        .address("-")
                        .phoneNumber("-")
                        .nik("-")
                        .isAktif("-")
                        .position("-")
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

    @Test
    void findCronValue() {
        log.info("Cron: {}", cronJobTriggerRepository.findByCodeTrigger("AA").get().getCronValue());

    }

    @Test
    void loop() {
        Long[] employeeId = {1L, 2l, 10L};

        List<Employee> employees = new ArrayList<>();

        for (Long id :
                employeeId) {
            log.info("employeeId {}", id);
            employees.add(Optional.ofNullable(employeeRepository.findByEmployeeId(id)).map(Optional::get).orElseGet(null));
        }
        log.info("employee {}", employees);
    }

    @Test
    void equalsCharSequenceString() {
        CharSequence firstString = "baeldung";
        String secondString = "baeldung";

        if (firstString == secondString) {
            log.info("equals");
        } else {
            log.info("not equals");
        }
    }

    @Test
    void testLagi() {
        List<String> input = new LinkedList<>(Arrays.asList("a", "b", "c"));
        List<CharSequence> result;

//    result = input; // <-- Type mismatch: cannot convert from List<String> to List<CharSequence>
        result = new ArrayList<>(input);

        System.out.println(result);
    }

    @Test
    void streamJoin() {
        Stream<String> words = Arrays.asList("Irsan", "Ramadhan").stream();
        String fullname = words.collect(Collectors.joining(" "));
        log.info("Full Name: {}", fullname);
    }

    @Test
    void countString() {
        for (int i = 0; i < 11; i++) {
            String id = UUID.randomUUID().toString().replace("-", "");
            long countId = id.chars().count();
            log.info("Count: {}", countId);
        }
    }
}
