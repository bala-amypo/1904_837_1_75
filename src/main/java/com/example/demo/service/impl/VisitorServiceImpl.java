package com.example.demo.service.impl;

import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repository;

    public VisitorServiceImpl(VisitorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Visitor createVisitor(Visitor visitor) {

        if (visitor.getPhone() == null || visitor.getPhone().isBlank()) {
            throw new IllegalArgumentException("phone required");
        }

        return repository.save(visitor);
    }

    @Override
    public Visitor getVisitor(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return repository.findAll();
    }
}
