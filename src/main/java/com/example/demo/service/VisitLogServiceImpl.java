package com.example.demo.service.impl;

import com.example.demo.model.VisitLog;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorRepository visitorRepository;

    @Override
    public VisitLog createVisitLog(Long visitorId, VisitLog log) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        log.setVisitor(visitor);

        if (log.getEntryTime() == null)
            log.setEntryTime(LocalDateTime.now());

        return visitLogRepository.save(log);
    }

    @Override
    @Transactional
    public VisitLog updateExitTime(Long logId) {

        VisitLog log = visitLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Visit log not found"));

        log.setExitTime(LocalDateTime.now());

        return log;
    }

    @Override
    public VisitLog getLog(Long logId) {
        return visitLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Visit log not found"));
    }

    @Override
    public List<VisitLog> getLogsByVisitor(Long visitorId) {
        return visitLogRepository.findByVisitorId(visitorId);
    }

    @Override
    public List<VisitLog> getLogsByVisitorSince(Long visitorId, LocalDateTime since) {
        return visitLogRepository.findByVisitorIdAndEntryTimeAfter(visitorId, since);
    }

    @Override
    public List<VisitLog> getAllLogs() {
        return visitLogRepository.findAll();
    }
}
