package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.RiskRule;
import com.example.demo.repository.RiskRuleRepository;
import com.example.demo.service.RiskRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskRuleServiceImpl implements RiskRuleService {

    private final RiskRuleRepository riskRuleRepository;

    public RiskRuleServiceImpl(RiskRuleRepository riskRuleRepository) {
        this.riskRuleRepository = riskRuleRepository;
    }

    @Override
    public RiskRule createRule(RiskRule rule) {

        riskRuleRepository.findByRuleName(rule.getRuleName())
                .ifPresent(r -> {
                    throw new BadRequestException("Rule name must be unique");
                });

        if (rule.getThreshold() < 0 || rule.getScoreImpact() < 0) {
            throw new BadRequestException("threshold and scoreImpact must be non-negative");
        }

        return riskRuleRepository.save(rule);
    }

    @Override
    public RiskRule getRule(Long id) {
        return riskRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public List<RiskRule> getAllRules() {
        return riskRuleRepository.findAll();
    }
}
