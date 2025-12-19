package com.example.demo.controller;

import com.example.demo.model.RiskScore;
import com.example.demo.service.RiskScoreService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/risk-scores")
@Tag(name = "Risk Score")
public class RiskScoreController {

    private final RiskScoreService service;

    public RiskScoreController(RiskScoreService s) { this.service = s; }

    @PostMapping("/evaluate/{visitorId}")
    public RiskScore eval(@PathVariable Long visitorId) {
        return service.evaluateVisitor(visitorId);
    }

    @GetMapping("/{visitorId}")
    public RiskScore get(@PathVariable Long visitorId) {
        return service.getScoreForVisitor(visitorId);
    }

    @GetMapping
    public List<RiskScore> all() {
        return service.getAllScores();
    }
}