package com.irsan.springbootbackend.controller;

import com.irsan.springbootbackend.service.DataEmployeeUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

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
    public String startSchedule() {
        return dataEmployeeUpdateService.startScheduler();
    }

    @GetMapping(value = "/stop/{id}")
    public String stopSchedule(@PathVariable("id") String id) {
        return dataEmployeeUpdateService.stopScheduler(id);
    }

    @GetMapping(value = "/list")
    public Map<String, String> listSchedule() {
        return dataEmployeeUpdateService.listScheduler();
    }

}
