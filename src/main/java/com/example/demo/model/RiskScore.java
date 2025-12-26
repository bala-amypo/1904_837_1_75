package com.example.demo.model;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Visitor visitor;

    private Integer totalScore;
    private String riskLevel;
    private LocalDateTime evaluatedAt;

    @PrePersist
    void onEvaluate() {
        evaluatedAt = LocalDateTime.now();
    }
}