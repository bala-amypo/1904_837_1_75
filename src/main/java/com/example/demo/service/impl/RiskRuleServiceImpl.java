package com.example.demo.service.impl;

import com.example.demo.model.RiskRule;
import com.example.demo.repository.RiskRuleRepository;
import com.example.demo.service.RiskRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskRuleServiceImpl implements RiskRuleService {

    private final RiskRuleRepository repository;

    public RiskRuleServiceImpl(RiskRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public RiskRule createRule(RiskRule rule) {

        if (rule.getThreshold() != null && rule.getThreshold() < 0) {
            throw new IllegalArgumentException("threshold must be non-negative");
        }

        if (rule.getScoreImpact() != null && rule.getScoreImpact() < 0) {
            throw new IllegalArgumentException("scoreImpact must be non-negative");
        }

        return repository.save(rule);
    }

    @Override
    public RiskRule getRule(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<RiskRule> getAllRules() {
        return repository.findAll();
    }
}
