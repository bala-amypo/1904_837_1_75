package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    // Used in tests: testVisitCountQuery_simulation
    List<VisitLog> findByVisitorSince(Long visitorId, LocalDateTime since);

    // Used in tests: testCustomQuery_countVisitsInWindow
    Long countVisitsInWindow(Long visitorId,
                             LocalDateTime start,
                             LocalDateTime end);
}
