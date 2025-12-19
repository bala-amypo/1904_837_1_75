package com.example.demo.repository;

import com.example.demo.model.ScoreAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreAuditLogRepository extends JpaRepository<ScoreAuditLog, Long> {

    List<ScoreAuditLog> findByVisitorId(Long visitorId);
}
