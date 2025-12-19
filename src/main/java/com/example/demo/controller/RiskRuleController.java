package com.example.demo.controller;

import com.example.demo.model.RiskRule;
import com.example.demo.service.RiskRuleService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/risk-rules")
@Tag(name = "Risk Rule")
public class RiskRuleController {

    private final RiskRuleService service;

    public RiskRuleController(RiskRuleService s) { this.service = s; }

    @PostMapping
    public RiskRule create(@RequestBody RiskRule r) {
        return service.createRule(r);
    }

    @GetMapping("/{id}")
    public RiskRule get(@PathVariable Long id) {
        return service.getRule(id);
    }

    @GetMapping
    public List<RiskRule> all() {
        return service.getAllRules();
    }
}