//package com.irsan.springbootbackend.listener;
//
//import com.irsan.springbootbackend.entity.DataEmployee;
//import com.irsan.springbootbackend.entity.Employee;
//import lombok.extern.slf4j.Slf4j;
//
//import javax.persistence.*;
//
///**
// * @author: Irsan Ramadhan
// * @email: irsan.ramadhan@iconpln.co.id
// */
//@Slf4j
//public class DataEmployeeAuditTrailListener {
//
//    public static DataEmployee dataAjah(String message, DataEmployee dataEmployee) {
//        DataEmployee dataEmp = dataEmployee;
//        log.info(message, dataEmp);
//        return dataEmp;
//    }
//
//    @PrePersist
//    @PreUpdate
//    @PreRemove
//    private void beforeAnyUpdate(DataEmployee dataEmployee) {
//        DataEmployee dataEmp = DataEmployee.builder()
//                .employeeId(dataEmployee.getEmployeeId())
//                .address(dataEmployee.getAddress())
//                .phoneNumber(dataEmployee.getPhoneNumber())
//                .nik(dataEmployee.getNik())
//                .isAktif(dataEmployee.getIsAktif())
//                .position(dataEmployee.getPosition())
//                .build();
//        dataAjah("add/update/delete complete for dataAjah: {}", dataEmp);
//        log.info("add/update/delete complete for DataEmployee: {}", dataEmp);
//    }
//
//    @PostPersist
//    @PostUpdate
//    @PostRemove
//    private void afterAnyUpdate(DataEmployee dataEmployee) {
//        DataEmployee dataEmp = DataEmployee.builder()
//                .employeeId(dataEmployee.getEmployeeId())
//                .address(dataEmployee.getAddress())
//                .phoneNumber(dataEmployee.getPhoneNumber())
//                .nik(dataEmployee.getNik())
//                .isAktif(dataEmployee.getIsAktif())
//                .position(dataEmployee.getPosition())
//                .build();
//        dataAjah("[dataAjah AUDIT] add/update/delete complete for dataAjah: {}", dataEmp);
//        log.info("[DataEmployee AUDIT] add/update/delete complete for DataEmployee: {}", dataEmp);
//    }
//
//    @PostLoad
//    private void afterLoad(DataEmployee dataEmployee) {
//        DataEmployee dataEmp = DataEmployee.builder()
//                .employeeId(dataEmployee.getEmployeeId())
//                .address(dataEmployee.getAddress())
//                .phoneNumber(dataEmployee.getPhoneNumber())
//                .nik(dataEmployee.getNik())
//                .isAktif(dataEmployee.getIsAktif())
//                .position(dataEmployee.getPosition())
//                .build();
//        dataAjah("[dataAjah AUDIT] dataAjah loaded from database: {}", dataEmp);
//        log.info("[DataEmployee AUDIT] DataEmployee loaded from database: {}", dataEmp);
//    }
//
//}
