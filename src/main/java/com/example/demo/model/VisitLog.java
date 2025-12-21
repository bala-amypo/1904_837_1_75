package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private String purpose;
    private String location;

    @PrePersist
    void prePersist() {
        if (entryTime == null)
            entryTime = LocalDateTime.now();
    }
}
