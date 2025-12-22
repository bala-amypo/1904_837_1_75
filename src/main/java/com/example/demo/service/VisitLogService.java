package com.example.demo.service;

import com.example.demo.model.VisitLog;
import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogService {

    VisitLog createVisitLog(Long visitorId, VisitLog log);

    VisitLog updateExitTime(Long logId);

    VisitLog getLog(Long logId);

    List<VisitLog> getLogsByVisitor(Long visitorId);

    List<VisitLog> getLogsByVisitorSince(Long visitorId, LocalDateTime since);

    List<VisitLog> getAllLogs();
}
