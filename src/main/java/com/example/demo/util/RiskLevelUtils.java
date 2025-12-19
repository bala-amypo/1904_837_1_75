package com.example.demo.util;

public class RiskLevelUtils {

    public static String determineRiskLevel(int score) {
        if (score < 20) {
            return "LOW";
        } else if (score < 50) {
            return "MEDIUM";
        } else if (score < 80) {
            return "HIGH";
        } else {
            return "CRITICAL";
        }
    }
}
