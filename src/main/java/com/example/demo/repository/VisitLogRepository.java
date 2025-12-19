package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    // EXACTLY as required by testcases
    @Query("SELECT v FROM VisitLog v WHERE v.visitor.id = :visitorId AND v.entryTime >= :since")
    List<VisitLog> findByVisitorSince(Long visitorId, LocalDateTime since);

    @Query("SELECT COUNT(v) FROM VisitLog v WHERE v.visitor.id = :visitorId AND v.entryTime >= :start AND v.entryTime <= :end")
    long countVisitsInWindow(Long visitorId, LocalDateTime start, LocalDateTime end);
}
