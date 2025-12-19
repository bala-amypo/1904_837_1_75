package com.example.demo.service;

import com.example.demo.model.VisitLog;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogService {

    VisitLog createVisitLog(Long visitorId, VisitLog log);

    VisitLog updateExit(Long logId);

    VisitLog getLog(Long id);

    List<VisitLog> getLogsByVisitor(Long visitorId);

    List<VisitLog> getLogsSince(Long visitorId, LocalDateTime since);
}
