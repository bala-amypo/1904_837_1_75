package com.example.demo.service;

import com.example.demo.model.RiskScore;
import java.util.List;

public interface RiskScoreService {

    RiskScore evaluateVisitor(Long visitorId);

    RiskScore getScoreForVisitor(Long visitorId);

    List<RiskScore> getAllScores();
}
