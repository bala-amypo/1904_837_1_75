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
    public VisitLog saveVisitLog(Long visitorId, String purpose, String location) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        VisitLog log = VisitLog.builder()
                .visitor(visitor)
                .purpose(purpose)
                .location(location)
                .entryTime(LocalDateTime.now())
                .build();

        return visitLogRepository.save(log);
    }

    @Override
    public VisitLog updateExitTime(Long logId) {

        VisitLog log = visitLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Visit log not found"));

        log.setExitTime(LocalDateTime.now());
        return visitLogRepository.save(log);
    }

    @Override
    public List<VisitLog> getVisitLogsForVisitor(Long visitorId, LocalDateTime since) {
        return visitLogRepository.findByVisitor_IdAndEntryTimeAfter(visitorId, since);
    }

    @Override
    public List<VisitLog> getAllLogs() {
        return visitLogRepository.findAll();
    }
}
