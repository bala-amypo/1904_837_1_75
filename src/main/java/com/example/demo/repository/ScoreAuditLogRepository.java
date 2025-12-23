package com.example.demo.repository;

import com.example.demo.model.ScoreAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreAuditLogRepository extends JpaRepository<ScoreAuditLog, Long> {

    List<ScoreAuditLog> findByVisitorId(Long visitorId);
}
