package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visit-log")
@RequiredArgsConstructor
public class VisitLogController {

    private final VisitLogService visitLogService;

    @PostMapping("/{visitorId}")
    public VisitLog createVisit(@PathVariable Long visitorId,
                                @RequestBody VisitLog log) {

        return visitLogService.createVisitLog(visitorId, log);
    }

    @PutMapping("/exit/{logId}")
    public VisitLog exit(@PathVariable Long logId) {
        return visitLogService.updateExitTime(logId);
    }

    @GetMapping("/{logId}")
    public VisitLog getLog(@PathVariable Long logId) {
        return visitLogService.getLog(logId);
    }

    @GetMapping("/visitor/{visitorId}")
    public List<VisitLog> getAllVisits(@PathVariable Long visitorId) {
        return visitLogService.getLogsByVisitor(visitorId);
    }

    @GetMapping("/visitor/{visitorId}/since")
    public List<VisitLog> getVisitsSince(@PathVariable Long visitorId,
                                         @RequestParam Integer hours) {
        return visitLogService.getLogsByVisitorSince(
                visitorId,
                LocalDateTime.now().minusHours(hours)
        );
    }

    @GetMapping
    public List<VisitLog> getAllLogs() {
        return visitLogService.getAllLogs();
    }
}
