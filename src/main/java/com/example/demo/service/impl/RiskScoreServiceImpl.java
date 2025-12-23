package com.example.demo.service.impl;

import com.example.demo.model.RiskScore;
import com.example.demo.model.Visitor;
import com.example.demo.repository.RiskScoreRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.RiskScoreService;
import com.example.demo.util.RiskLevelUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiskScoreServiceImpl implements RiskScoreService {

    private final RiskScoreRepository repository;
    private final VisitorRepository visitorRepository;

    public RiskScoreServiceImpl(RiskScoreRepository repository,
                                VisitorRepository visitorRepository) {
        this.repository = repository;
        this.visitorRepository = visitorRepository;
    }

    @Override
    public RiskScore evaluateVisitor(Long visitorId) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("not found"));

        int score = 0;

        RiskScore riskScore = RiskScore.builder()
                .visitor(visitor)
                .totalScore(score)
                .riskLevel(RiskLevelUtils.determineRiskLevel(score))
                .evaluatedAt(LocalDateTime.now())
                .build();

        return repository.save(riskScore);
    }

    @Override
    public RiskScore getScoreForVisitor(Long visitorId) {
        return repository.findByVisitorId(visitorId)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<RiskScore> getAllScores() {
        return repository.findAll();
    }
}
