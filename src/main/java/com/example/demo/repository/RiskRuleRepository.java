package com.example.demo.repository;

import com.example.demo.model.RiskRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskRuleRepository extends JpaRepository<RiskRule, Long> {
}
