package com.example.demo.service.impl;

import com.example.demo.model.VisitLog;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        VisitLog newLog = VisitLog.builder()
                .visitor(visitor)
                .purpose(log.getPurpose())
                .location(log.getLocation())
                .entryTime(LocalDateTime.now())
                .build();

        return visitLogRepository.save(newLog);
    }

    @Override
    public VisitLog updateExit(Long logId) {
        VisitLog log = visitLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Visit log not found"));

        log.setExitTime(LocalDateTime.now());
        return visitLogRepository.save(log);
    }

    @Override
    public VisitLog getLog(Long id) {
        return visitLogRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<VisitLog> getLogsByVisitor(Long visitorId) {
        return visitLogRepository.findByVisitorSince(visitorId, LocalDateTime.MIN);
    }

    @Override
    public List<VisitLog> getLogsSince(Long visitorId, LocalDateTime since) {
        return visitLogRepository.findByVisitorSince(visitorId, since);
    }
}
