package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.service.DataEmployeeUpdateService;
import com.irsan.springbootbackend.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@RestController
@RequestMapping("/api/v1/scheduler")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {

    private final DataEmployeeUpdateService dataEmployeeUpdateService;

    @GetMapping(value = "/start")
    public BaseResponse<?> startSchedule() {
        return dataEmployeeUpdateService.startScheduler();
    }

    @GetMapping(value = "/stop/{id}")
    public BaseResponse<?> stopSchedule(@PathVariable("id") String id) {
        return dataEmployeeUpdateService.stopScheduler(id);
    }

    @GetMapping(value = "/list")
    public BaseResponse<?> listSchedule() {
        return dataEmployeeUpdateService.listScheduler();
    }

}
