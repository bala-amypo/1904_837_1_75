package com.example.demo.util;

public class RiskLevelUtils {

    public static String determineRiskLevel(int score) {
        if (score < 20) return "LOW";
        if (score < 50) return "MEDIUM";
        if (score < 80) return "HIGH";
        return "CRITICAL";
    }
}