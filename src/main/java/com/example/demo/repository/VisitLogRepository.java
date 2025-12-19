package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    List<VisitLog> findByVisit​orSince(Long visitorId, LocalDateTime since);

    long countVisitsInWindow(Long visitorId, LocalDateTime start, LocalDateTime end);
}
