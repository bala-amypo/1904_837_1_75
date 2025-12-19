package com.example.demo.controller;

import com.example.demo.model.VisitLog;
import com.example.demo.service.VisitLogService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/visit-logs")
@Tag(name = "Visit Log")
public class VisitLogController {

    private final VisitLogService service;

    public VisitLogController(VisitLogService s) { this.service = s; }

    @PostMapping("/{visitorId}")
    public VisitLog create(@PathVariable Long visitorId, @RequestBody VisitLog log) {
        return service.createVisitLog(visitorId, log);
    }

    @GetMapping("/{id}")
    public VisitLog get(@PathVariable Long id) {
        return service.getLog(id);
    }

    @GetMapping("/visitor/{visitorId}")
    public List<VisitLog> byVisitor(@PathVariable Long visitorId) {
        return service.getLogsByVisitor(visitorId);
    }
}