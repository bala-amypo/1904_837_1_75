package com.example.demo.service.impl;

import com.example.demo.model.ScoreAuditLog;
import com.example.demo.model.Visitor;
import com.example.demo.repository.ScoreAuditLogRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.ScoreAuditLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreAuditLogServiceImpl implements ScoreAuditLogService {

    private final ScoreAuditLogRepository repository;
    private final VisitorRepository visitorRepository;

    public ScoreAuditLogServiceImpl(ScoreAuditLogRepository repository,
                                    VisitorRepository visitorRepository) {
        this.repository = repository;
        this.visitorRepository = visitorRepository;
    }

    @Override
    public ScoreAuditLog logScoreChange(Long visitorId, Long ruleId, ScoreAuditLog log) {

        if (log.getReason() == null || log.getReason().isBlank()) {
            throw new IllegalArgumentException("reason required");
        }

        if (log.getScoreChange() != null && log.getScoreChange() < 0) {
            throw new IllegalArgumentException("scoreChange must be non-negative");
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("not found"));

        log.setVisitor(visitor);
        return repository.save(log);
    }

    @Override
    public ScoreAuditLog getLog(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<ScoreAuditLog> getLogsByVisitor(Long visitorId) {
        return repository.findByVisitorId(visitorId);
    }
}
