package com.example.demo.controller;

import com.example.demo.model.ScoreAuditLog;
import com.example.demo.service.ScoreAuditLogService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/score-logs")
@Tag(name = "Score Audit Log")
public class ScoreAuditLogController {

    private final ScoreAuditLogService service;

    public ScoreAuditLogController(ScoreAuditLogService s) { this.service = s; }

    @PostMapping("/{visitorId}/{ruleId}")
    public ScoreAuditLog create(@PathVariable Long visitorId,
                                @PathVariable Long ruleId,
                                @RequestBody ScoreAuditLog log) {
        return service.logScoreChange(visitorId, ruleId, log);
    }

    @GetMapping("/{id}")
    public ScoreAuditLog get(@PathVariable Long id) {
        return service.getLog(id);
    }

    @GetMapping("/visitor/{visitorId}")
    public List<ScoreAuditLog> byVisitor(@PathVariable Long visitorId) {
        return service.getLogsByVisitor(visitorId);
    }
}