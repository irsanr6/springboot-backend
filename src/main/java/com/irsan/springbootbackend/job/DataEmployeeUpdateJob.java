//package com.irsan.springbootbackend.job;
//
//import com.irsan.springbootbackend.service.DataEmployeeUpdateService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//
//import java.util.Date;
//
///**
// * @author: Irsan Ramadhan
// * @email: irsan.ramadhan@iconpln.co.id
// */
//@Configuration
//@RequiredArgsConstructor
//@Slf4j
//public class DataEmployeeUpdateJob implements SchedulingConfigurer {
//
//    private final DataEmployeeUpdateService dataEmployeeUpdateService;
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        // Do not put @Scheduled annotation above this method, we don't need it anymore.
//        taskRegistrar.addTriggerTask(dataEmployeeUpdateService::dataEmployeeUpdate, triggerContext -> {
//            String cron = dataEmployeeUpdateService.cronConfig();
//            log.info(cron);
//            CronTrigger trigger = new CronTrigger(cron);
//            Date nextExec = trigger.nextExecutionTime(triggerContext);
//            return nextExec;
//        });
//    }
//}
