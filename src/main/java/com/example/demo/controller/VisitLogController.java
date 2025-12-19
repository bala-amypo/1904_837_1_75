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

    // Create new visit log
    @PostMapping("/{visitorId}")
    public VisitLog createVisit(
            @PathVariable Long visitorId,
            @RequestBody VisitLog log
    ) {
        return visitLogService.saveVisitLog(
                visitorId,
                log.getPurpose(),
                log.getLocation()
        );
    }

    // Update exit time
    @PutMapping("/exit/{logId}")
    public VisitLog updateExit(@PathVariable Long logId) {
        return visitLogService.updateExitTime(logId);
    }

    // Get logs for visitor since last X hours (optional)
    @GetMapping("/{visitorId}")
    public List<VisitLog> getLogsForVisitor(
            @PathVariable Long visitorId,
            @RequestParam(required = false) Integer hours
    ) {
        LocalDateTime since = (hours != null)
