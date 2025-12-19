package com.example.demo.service;

import com.example.demo.model.VisitLog;

import java.time.LocalDateTime;
import java.util.List;

public interface VisitLogService {

    VisitLog saveVisitLog(Long visitorId, String purpose, String location);

    VisitLog updateExitTime(Long logId);

    List<VisitLog> getVisitLogsForVisitor(Long visitorId, LocalDateTime since);

    List<VisitLog> getAllLogs();
}
