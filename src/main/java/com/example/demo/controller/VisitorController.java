package com.example.demo.controller;

import com.example.demo.model.Visitor;
import com.example.demo.service.VisitorService;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/visitors")
@Tag(name = "Visitor")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService s) { this.visitorService = s; }

    @PostMapping
    public Visitor create(@RequestBody Visitor v) {
        return visitorService.createVisitor(v);
    }

    @GetMapping("/{id}")
    public Visitor get(@PathVariable Long id) {
        return visitorService.getVisitor(id);
    }

    @GetMapping
    public List<Visitor> all() {
        return visitorService.getAllVisitors();
    }
}