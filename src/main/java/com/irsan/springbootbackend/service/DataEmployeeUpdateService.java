package com.irsan.springbootbackend.service;

import com.irsan.springbootbackend.entity.CronJobTrigger;
import com.irsan.springbootbackend.entity.DataEmployee;
import com.irsan.springbootbackend.entity.Employee;
import com.irsan.springbootbackend.entity.JobListActive;
import com.irsan.springbootbackend.model.EmployeeResponse;
import com.irsan.springbootbackend.repository.CronJobTriggerRepository;
import com.irsan.springbootbackend.repository.DataEmployeeRepository;
import com.irsan.springbootbackend.repository.EmployeeRepository;
import com.irsan.springbootbackend.repository.JobListActiveRepository;
import com.irsan.springbootbackend.utils.BaseResponse;
import com.irsan.springbootbackend.utils.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataEmployeeUpdateService {

    private final TaskScheduler taskScheduler;

    private final EmployeeRepository employeeRepository;
    private final DataEmployeeRepository dataEmployeeRepository;
    private final CronJobTriggerRepository cronJobTriggerRepository;
    private final JobListActiveRepository jobListActiveRepository;

    private Map<String, Wrapper> map = new ConcurrentHashMap<>();
    private Map<Wrapper, String> cronMap = new ConcurrentHashMap<>();
    private Map<String, String> listJobMap = new ConcurrentHashMap<>();


    public BaseResponse<?> startScheduler() {
        final String cron = cronConfig();
        final CronTrigger cronTrigger = new CronTrigger(cron);
        final ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(dataEmployeeUpdate(), cronTrigger);
        final Wrapper wrapper = new Wrapper(cron, scheduledFuture);
        final String id = UUID.randomUUID().toString().replace("-", "");
        JobListActive jobListActive = JobListActive.builder()
                .jobId(id)
                .cronExpression(wrapper.getCron())
                .startDatetime(Helper.currentDate())
                .build();
        jobListActiveRepository.save(jobListActive);
        map.put(id, wrapper);
        cronMap.put(wrapper, wrapper.getCron());
        log.info("START-{} CRON-{}", id, cron);
        return BaseResponse.ok("START-" + id + " CRON-" + cron);
    }

    public BaseResponse<?> stopScheduler(String id) {
        Wrapper wrapper = map.get(id);
        Optional<JobListActive> jobId = jobListActiveRepository.findByJobId(id);
        if (wrapper != null && jobId.isPresent()) {
            wrapper.getFuture().cancel(false);
            jobListActiveRepository.deleteById(jobId.get().getJobId());
            map.remove(id);
            cronMap.remove(wrapper);
            listJobMap.remove(id);
            if (listJobMap.isEmpty()) {
                log.info("Job has deleted all");
                return BaseResponse.error200("Job has deleted all");
            } else {
                log.info("STOP-{}", id);
                return BaseResponse.ok("STOP-" + id, listJobMap);
            }
        } else {
            log.info("Fail: not found");
            return BaseResponse.error200("Fail: not found");
        }
    }

    public BaseResponse<?> listScheduler() {
        for (Map.Entry<String, Wrapper> job :
                map.entrySet()) {
            Wrapper wrapper = map.get(job.getKey());
            String cron = cronMap.get(wrapper);
            String id = job.getKey();
            listJobMap.put(id, cron);
        }
        if (listJobMap.isEmpty()) {
            log.info("Job has deleted all");
            return BaseResponse.error200("Job has deleted all");
        } else {
            log.info("JOBLIST-{}", listJobMap);
            return BaseResponse.ok(listJobMap);
        }
    }

    public String cronConfig() {
        final Optional<CronJobTrigger> cronValue = cronJobTriggerRepository.findByCodeTrigger("AA");
        if (cronValue.isPresent()) {
            return cronValue.get().getCronValue();
        }
        log.info("Fail: not found");
        return "Fail: not found";
    }

    public Runnable dataEmployeeUpdate() {
        return () -> {

            List<Employee> employeeList = employeeRepository.findAll();
            for (Employee employee :
                    employeeList) {
                Optional<DataEmployee> dataEmployee = dataEmployeeRepository.findByEmployeeId(employee.getEmployeeId());
                if (dataEmployee.isEmpty()) {
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
                            .fullName(Helper.fullName(employee.getFirstName(), employee.getLastName()))
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
        };
    }

    public static final class Wrapper<V> {

        private final String cron;
        private final ScheduledFuture<V> future;

        public Wrapper(String cron, ScheduledFuture<V> future) {
            this.cron = cron;
            this.future = future;
        }

        public String getCron() {
            return cron;
        }

        public ScheduledFuture<V> getFuture() {
            return future;
        }
    }

}
