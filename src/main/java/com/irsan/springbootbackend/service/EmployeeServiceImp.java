package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.model.EmployeeGetRequest;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.model.EmployeeSaveRequest;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import com.irsan.springbootbackend.utils.EmployeeSpecification;
import com.irsan.springbootbackend.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImp implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DataEmployeeRepository dataEmployeeRepository;

    @Override
    public BaseResponse<?> getAllEmployee(EmployeeGetRequest request) {
        Pageable pageable = Helper.getPageRequest(request.getPageIn(), request.getLimit(), "firstName");
        Page<Employee> employeePage = employeeRepository.findAll(EmployeeSpecification.findSpec(request), pageable);
        List<Employee> employeeList = employeePage.getContent();

        if (employeeList.isEmpty()) {
            Helper.logError200("Data tidak ditemukan");
            return BaseResponse.error200("Data tidak ditemukan");
        } else {
            Helper.ok("Data berhasil ditemukan");
            return BaseResponse.ok(employeeList.stream()
                    .map(employee -> EmployeeResponse.builder()
                            .employeeId(employee.getEmployeeId())
                            .firstName(employee.getFirstName())
                            .lastName(employee.getLastName())
                            .fullName(Helper.fullName(employee.getFirstName(), employee.getLastName()))
                            .email(employee.getEmail())
                            .username(employee.getUsername())
                            .address(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getAddress).orElse("-"))
                            .phoneNumber(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getPhoneNumber).orElse("-"))
                            .nik(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getNik).orElse("-"))
                            .isAktif(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getIsAktif).orElse("-"))
                            .position(Optional.ofNullable(employee.getDataEmployee()).map(DataEmployee::getPosition).orElse("-"))
                            .build())
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public BaseResponse<?> saveEmployee(EmployeeSaveRequest request) {
        if (StringUtils.isNotEmpty(request.getEmployeeId())
                && StringUtils.isNotBlank(request.getEmployeeId())
                && !request.getEmployeeId().equals("")) {
            Optional<Employee> employee = employeeRepository.findByEmployeeId(Long.parseLong(request.getEmployeeId()));
            Optional<DataEmployee> dataEmployee = dataEmployeeRepository.findByEmployeeId(Long.parseLong(request.getEmployeeId()));
            if (employee.isPresent() && dataEmployee.isPresent()) {
                Employee emUpdate = employee.get();
                emUpdate.setFirstName(request.getFirstName());
                emUpdate.setLastName(request.getLastName());
                emUpdate.setEmail(request.getEmail());
                Employee emSave = employeeRepository.save(emUpdate);

                DataEmployee dataEmpUpdate = dataEmployee.get();
                dataEmpUpdate.setAddress(request.getAddress());
                dataEmpUpdate.setPhoneNumber(request.getPhoneNumber());
                dataEmpUpdate.setNik(request.getNik());
                dataEmpUpdate.setIsAktif(request.getIsAktif());
                dataEmpUpdate.setPosition(request.getPosition());
                DataEmployee dataEmpSave = dataEmployeeRepository.save(dataEmpUpdate);

                Helper.ok("Data berhasil diperbaharui", EmployeeResponse.builder()
                        .employeeId(emSave.getEmployeeId())
                        .firstName(emSave.getFirstName())
                        .lastName(emSave.getLastName())
                        .fullName(Helper.fullName(emSave.getFirstName(), emSave.getLastName()))
                        .email(emSave.getEmail())
                        .address(dataEmpSave.getAddress())
                        .phoneNumber(dataEmpSave.getPhoneNumber())
                        .nik(dataEmpSave.getNik())
                        .isAktif(dataEmpSave.getIsAktif())
                        .position(dataEmpSave.getPosition())
                        .build());
                return BaseResponse.ok("Data berhasil disimpan", EmployeeResponse.builder()
                        .employeeId(emSave.getEmployeeId())
                        .firstName(emSave.getFirstName())
                        .lastName(emSave.getLastName())
                        .fullName(Helper.fullName(emSave.getFirstName(), emSave.getLastName()))
                        .email(emSave.getEmail())
                        .address(dataEmpSave.getAddress())
                        .phoneNumber(dataEmpSave.getPhoneNumber())
                        .nik(dataEmpSave.getNik())
                        .isAktif(dataEmpSave.getIsAktif())
                        .position(dataEmpSave.getPosition())
                        .build());
            }
        }

        Employee emCreate = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
        Employee emSave = employeeRepository.save(emCreate);

        DataEmployee dataEmpCreate = DataEmployee.builder()
                .employeeId(emSave.getEmployeeId())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .nik(request.getNik())
                .isAktif(request.getIsAktif())
                .position(request.getPosition())
                .build();
        DataEmployee dataEmpSave = dataEmployeeRepository.save(dataEmpCreate);

        Helper.ok("Data berhasil ditambah", EmployeeResponse.builder()
                .employeeId(emSave.getEmployeeId())
                .firstName(emSave.getFirstName())
                .lastName(emSave.getLastName())
                .fullName(Helper.fullName(emSave.getFirstName(), emSave.getLastName()))
                .email(emSave.getEmail())
                .address(dataEmpSave.getAddress())
                .phoneNumber(dataEmpSave.getPhoneNumber())
                .nik(dataEmpSave.getNik())
                .isAktif(dataEmpSave.getIsAktif())
                .position(dataEmpSave.getPosition())
                .build());
        return BaseResponse.ok("Data berhasil disimpan", EmployeeResponse.builder()
                .employeeId(emSave.getEmployeeId())
                .firstName(emSave.getFirstName())
                .lastName(emSave.getLastName())
                .fullName(Helper.fullName(emSave.getFirstName(), emSave.getLastName()))
                .email(emSave.getEmail())
                .address(dataEmpSave.getAddress())
                .phoneNumber(dataEmpSave.getPhoneNumber())
                .nik(dataEmpSave.getNik())
                .isAktif(dataEmpSave.getIsAktif())
                .position(dataEmpSave.getPosition())
                .build());
    }

}
