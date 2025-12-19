package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.dto.*;
import java.util.List;

public interface VisitorService {
    Visitor createVisitor(Visitor visitor);
    Visitor getVisitor(Long id);
    List<Visitor> getAllVisitors();
}

public interface VisitLogService {
    VisitLog createVisitLog(Long visitorId, VisitLog log);
    VisitLog getLog(Long id);
    List<VisitLog> getLogsByVisitor(Long visitorId);
}

public interface RiskRuleService {
    RiskRule createRule(RiskRule rule);
    RiskRule getRule(Long id);
    List<RiskRule> getAllRules();
}

public interface RiskScoreService {
    RiskScore evaluateVisitor(Long visitorId);
    RiskScore getScoreForVisitor(Long visitorId);
    List<RiskScore> getAllScores();
}

public interface ScoreAuditLogService {
    ScoreAuditLog logScoreChange(Long visitorId, Long ruleId, ScoreAuditLog log);
    ScoreAuditLog getLog(Long id);
    List<ScoreAuditLog> getLogsByVisitor(Long visitorId);
}

public interface UserService {
    User register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    User getByEmail(String email);
}