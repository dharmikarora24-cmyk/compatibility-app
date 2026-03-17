package com.compatibility.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class CompatibilityService {

    private static final Map<String, Double> SCORES = new HashMap<>();

    static {
        SCORES.put("0_1_1", 80.5); SCORES.put("0_2_2", 55.6); SCORES.put("0_3_3", 89.3);
        SCORES.put("0_1_2", 43.9); SCORES.put("0_1_3", 74.6);
        SCORES.put("0_2_1", 55.2); SCORES.put("0_2_3", 59.9);
        SCORES.put("0_3_1", 75.6); SCORES.put("0_3_2", 46.6);

        SCORES.put("1_1_1", 84.9); SCORES.put("1_2_2", 46.0); SCORES.put("1_3_3", 76.6);
        SCORES.put("1_1_2", 51.2); SCORES.put("1_1_3", 70.8);
        SCORES.put("1_2_1", 53.4); SCORES.put("1_2_3", 68.0);
        SCORES.put("1_3_1", 81.2); SCORES.put("1_3_2", 56.4);

        SCORES.put("2_1_1", 80.7); SCORES.put("2_2_2", 13.8); SCORES.put("2_3_3", 77.2);
        SCORES.put("2_1_2", 27.0); SCORES.put("2_1_3", 81.2);
        SCORES.put("2_2_1", 31.8); SCORES.put("2_2_3", 32.2);
        SCORES.put("2_3_1", 75.2); SCORES.put("2_3_2", 15.8);

        SCORES.put("3_1_1", 78.2); SCORES.put("3_2_2", 60.0); SCORES.put("3_3_3", 89.8);
        SCORES.put("3_1_2", 56.0); SCORES.put("3_1_3", 77.6);
        SCORES.put("3_2_1", 75.5); SCORES.put("3_2_3", 74.2);
        SCORES.put("3_3_1", 74.4); SCORES.put("3_3_2", 70.6);

        SCORES.put("4_1_1", 51.0); SCORES.put("4_2_2", 27.0); SCORES.put("4_3_3", 91.4);
        SCORES.put("4_1_2", 27.6); SCORES.put("4_1_3", 70.0);
        SCORES.put("4_2_1", 37.8); SCORES.put("4_2_3", 44.6);
        SCORES.put("4_3_1", 74.0); SCORES.put("4_3_2", 51.0);
    }

    public double[] calculateScores(Integer[] girlAnswers, Integer[] boyAnswers) {
        double[] scores = new double[5];
        for (int i = 0; i < 5; i++) {
            String key = i + "_" + girlAnswers[i] + "_" + boyAnswers[i];
            scores[i] = SCORES.getOrDefault(key, 0.0);
        }
        return scores;
    }

    public double calculateFinalScore(double[] scores) {
        double total = 0;
        for (double s : scores) total += s;
        return Math.round((total / scores.length) * 10.0) / 10.0;
    }

    public String getCompatibilityLabel(double score) {
        if (score >= 85) return "Soulmates✨";
        if (score >= 70) return "Deeply Compatible💞";
        if (score >= 55) return "Good Match💛";
        if (score >= 40) return "Some Differences🤔";
        return "Opposites🌪️";
    }
}
