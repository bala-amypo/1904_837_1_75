package com.example.demo.util;

public class RiskLevelUtils {

    public static String determineRiskLevel(int score) {
        if (score <= 19) return "LOW";
        if (score <= 49) return "MEDIUM";
        if (score <= 79) return "HIGH";
        return "CRITICAL";
    }
}
