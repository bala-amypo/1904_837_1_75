package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visit-log")
@RequiredArgsConstructor
public class VisitLogController {

private final VisitLogService visitLogService;

@PostMapping("/{visitorId}")
public VisitLog createVisitLog(
        @PathVariable Long visitorId,
        @RequestBody VisitLog log) {
    return visitLogService.createVisitLog(visitorId, log);
}

@GetMapping("/{logId}")
public VisitLog getVisitLog(@PathVariable Long logId) {
    return visitLogService.getLog(logId);
}

@GetMapping("/visitor/{visitorId}")
public List<VisitLog> getLogsByVisitor(@PathVariable Long visitorId) {
    return visitLogService.getLogsByVisitor(visitorId);
}


}