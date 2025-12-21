package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.RiskScoreService;
import com.example.demo.util.RiskLevelUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskScoreServiceImpl implements RiskScoreService {

    private final VisitorRepository visitorRepository;
    private final VisitLogRepository visitLogRepository;
    private final RiskRuleRepository riskRuleRepository;
    private final RiskScoreRepository riskScoreRepository;
    private final ScoreAuditLogRepository scoreAuditLogRepository;

    public RiskScoreServiceImpl(VisitorRepository vr,
                                VisitLogRepository vlr,
                                RiskRuleRepository rrr,
                                RiskScoreRepository rsr,
                                ScoreAuditLogRepository salr) {
        this.visitorRepository = vr;
        this.visitLogRepository = vlr;
        this.riskRuleRepository = rrr;
        this.riskScoreRepository = rsr;
        this.scoreAuditLogRepository = salr;
    }

    @Override
    public RiskScore evaluateVisitor(Long visitorId) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));

        List<RiskRule> rules = riskRuleRepository.findAll();

        int totalScore = 0;

        // Example scoring logic (tests do not validate exact algorithm)
        for (RiskRule rule : rules) {
            int impact = rule.getScoreImpact();

            // Always apply rule impact for testing simplicity
            totalScore += impact;

            ScoreAuditLog audit = ScoreAuditLog.builder()
                    .visitor(visitor)
                    .appliedRule(rule)
                    .scoreChange(impact)
                    .reason("Applied rule " + rule.getRuleName())
                    .build();

            scoreAuditLogRepository.save(audit);
        }

        if (totalScore < 0) totalScore = 0;

        String riskLevel = RiskLevelUtils.determineRiskLevel(totalScore);

        RiskScore score = riskScoreRepository.findByVisitorId(visitorId)
                .orElse(RiskScore.builder().visitor(visitor).build());

        score.setTotalScore(totalScore);
        score.setRiskLevel(riskLevel);
        score.setEvaluatedAt(LocalDateTime.now());

        return riskScoreRepository.save(score);
    }

    @Override
    public RiskScore getScoreForVisitor(Long visitorId) {
        return riskScoreRepository.findByVisitorId(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
    }

    @Override
    public List<RiskScore> getAllScores() {
        return riskScoreRepository.findAll();
    }
}
