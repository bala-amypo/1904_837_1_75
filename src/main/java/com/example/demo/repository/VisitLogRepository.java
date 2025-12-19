package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    // Fetch logs for a visitor after a given time
    List<VisitLog> findByVisitor_IdAndEntryTimeAfter(Long visitorId, LocalDateTime since);

}
