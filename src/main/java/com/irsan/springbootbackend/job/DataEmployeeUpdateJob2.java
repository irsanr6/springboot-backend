//package com.irsan.springbootbackend.job;
//
//import com.irsan.springbootbackend.entity.DataEmployee;
//import com.irsan.springbootbackend.entity.Employee;
//import com.irsan.springbootbackend.model.EmployeeResponse;
//import com.irsan.springbootbackend.repository.CronJobTriggerRepository;
//import com.irsan.springbootbackend.repository.DataEmployeeRepository;
//import com.irsan.springbootbackend.repository.EmployeeRepository;
//import com.irsan.springbootbackend.service.DataEmployeeUpdateService;
//import com.irsan.springbootbackend.utils.Helper;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author: Irsan Ramadhan
// * @email: irsan.ramadhan@iconpln.co.id
// */
//@Component
//@Slf4j
//public class DataEmployeeUpdateJob2 implements Runnable {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    @Autowired
//    private DataEmployeeRepository dataEmployeeRepository;
//    @Autowired
//    private CronJobTriggerRepository cronJobTriggerRepository;
//
//    @Override
//    public void run() {
//        List<Employee> employeeList = employeeRepository.findAll();
//        for (Employee employee :
//                employeeList) {
//            Optional<DataEmployee> dataEmployee = dataEmployeeRepository.findByEmployeeId(employee.getEmployeeId());
//            if (!dataEmployee.isPresent()) {
//                DataEmployee dataEmpCreate = DataEmployee.builder()
//                        .employeeId(employee.getEmployeeId())
//                        .address("")
//                        .phoneNumber("")
//                        .nik("")
//                        .isAktif("")
//                        .position("")
//                        .build();
//                DataEmployee dataEmpSave = dataEmployeeRepository.save(dataEmpCreate);
//
//                Helper.ok("Data berhasil ditambah", EmployeeResponse.builder()
//                        .employeeId(employee.getEmployeeId())
//                        .firstName(employee.getFirstName())
//                        .lastName(employee.getLastName())
//                        .fullName(employee.getFirstName() + " " + employee.getLastName())
//                        .email(employee.getEmail())
//                        .address(dataEmpSave.getAddress())
//                        .phoneNumber(dataEmpSave.getPhoneNumber())
//                        .nik(dataEmpSave.getNik())
//                        .isAktif(dataEmpSave.getIsAktif())
//                        .position(dataEmpSave.getPosition())
//                        .build());
//            }
//        }
//        Helper.ok("Semua data telah disimpan");;
//    }
//
//    public CronTrigger cronTrigger() {
//        CronTrigger cronTrigger = new CronTrigger(cronJobTriggerRepository.findByCodeTrigger("AA").get().getCronValue());
//        log.info("from trigger");
//        return cronTrigger;
//    }
//}
