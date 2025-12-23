package com.example.demo.service.impl;

import com.example.demo.model.VisitLog;
import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitLogRepository;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository logRepository;
    private final VisitorRepository visitorRepository;

    public VisitLogServiceImpl(VisitLogRepository logRepository,
                               VisitorRepository visitorRepository) {
        this.logRepository = logRepository;
        this.visitorRepository = visitorRepository;
    }

    @Override
    public VisitLog createVisitLog(Long visitorId, VisitLog log) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("not found"));

        if (log.getExitTime() != null &&
                log.getEntryTime() != null &&
                log.getExitTime().isBefore(log.getEntryTime())) {
            throw new IllegalArgumentException("exitTime must be after entryTime");
        }

        log.setVisitor(visitor);
        return logRepository.save(log);
    }

    @Override
    public VisitLog getLog(Long id) {
        return logRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<VisitLog> getLogsByVisitor(Long visitorId) {
        return logRepository.findAll();
    }
}
