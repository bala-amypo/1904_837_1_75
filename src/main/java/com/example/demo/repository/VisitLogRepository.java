package com.example.demo.repository;

import com.example.demo.model.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {

    List<VisitLog> findByVisitorId(Long visitorId);

    List<VisitLog> findByVisitorIdAndEntryTimeAfter(Long visitorId, LocalDateTime since);
}
