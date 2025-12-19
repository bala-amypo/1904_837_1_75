package com.example.demo.service;

import com.example.demo.model.ScoreAuditLog;
import java.util.List;

public interface ScoreAuditLogService {

    ScoreAuditLog logScoreChange(Long visitorId, Long ruleId, ScoreAuditLog log);

    ScoreAuditLog getLog(Long id);

    List<ScoreAuditLog> getLogsByVisitor(Long visitorId);
}
