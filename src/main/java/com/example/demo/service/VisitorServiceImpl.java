package com.example.demo.service.impl;

import com.example.demo.model.Visitor;
import com.example.demo.repository.VisitorRepository;
import com.example.demo.service.VisitorService;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import java.util.List;

public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repo;

    public VisitorServiceImpl(VisitorRepository repo) {
        this.repo = repo;
    }

    public Visitor createVisitor(Visitor visitor) {
        if (visitor.getPhone() == null || visitor.getPhone().isBlank()) {
            throw new BadRequestException("phone required");
        }
        return repo.save(visitor);
    }

    public Visitor getVisitor(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
    }

    public List<Visitor> getAllVisitors() {
        return repo.findAll();
    }
}
