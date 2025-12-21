package com.example.demo.service;

import com.example.demo.model.RiskRule;
import java.util.List;

public interface RiskRuleService {

    RiskRule createRule(RiskRule rule);

    RiskRule getRule(Long id);

    List<RiskRule> getAllRules();
}
