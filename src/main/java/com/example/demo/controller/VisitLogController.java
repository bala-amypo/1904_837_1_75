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
    public VisitLog create(@PathVariable Long visitorId, @RequestBody VisitLog log) {
        return visitLogService.createVisitLog(visitorId, log);
    }

    @PutMapping("/exit/{logId}")
    public VisitLog updateExit(@PathVariable Long logId) {
        return visitLogService.updateExit(logId);
    }

    @GetMapping("/{logId}")
    public VisitLog getOne(@PathVariable Long logId) {
        return visitLogService.getLog(logId);
    }

    @GetMapping("/visitor/{visitorId}")
    public List<VisitLog> getByVisitor(@PathVariable Long visitorId) {
        return visitLogService.getLogsByVisitor(visitorId);
    }

    @GetMapping("/visitor/{visitorId}/since")
    public List<VisitLog> getSince(
            @PathVariable Long visitorId,
            @RequestParam("hours") int hours
    ) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return visitLogService.getLogsSince(visitorId, since);
    }
}
