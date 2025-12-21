package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Visitor visitor;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String purpose;
    private String location;

    @PrePersist
    public void prePersist() {
        this.entryTime = LocalDateTime.now();
    }
}
