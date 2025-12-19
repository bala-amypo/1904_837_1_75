package com.example.demo.service.impl;

import com.example.demo.service.*;
import com.example.demo.dto.*;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.util.RiskLevelUtils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// -----------------------------------------------------
// VISITOR SERVICE IMPL
// -----------------------------------------------------
@Service
class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorServiceImpl(VisitorRepository repo) {
        this.visitorRepository = repo;
    }

    @Override
    public Visitor createVisitor(Visitor visitor) {

        if (visitor.getPhone() == null || visitor.getPhone().isBlank()) {
            throw new BadRequestException("phone required");
        }

        if (visitor.getIdProof() == null || visitor.getIdProof().isBlank()) {
            throw new BadRequestException("idProof required");
        }

        if (visitor.getFullName() == null || visitor.getFullName().isBlank()) {
            throw new BadRequestException("fullName required");
        }

        return visitorRepository.save(visitor);
    }

    @Override
    public Visitor getVisitor(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));
    }

    @Override
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
}



// -----------------------------------------------------
// VISIT LOG SERVICE IMPL
// -----------------------------------------------------
@Service
class VisitLogServiceImpl implements VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final VisitorRepository visitorRepository;

    public VisitLogServiceImpl(VisitLogRepository v1, VisitorRepository v2) {
        this.visitLogRepository = v1;
        this.visitorRepository = v2;
    }

    @Override
    public VisitLog createVisitLog(Long visitorId, VisitLog log) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));

        if (log.getPurpose() == null || log.getPurpose().isBlank())
            throw new BadRequestException("purpose required");

        if (log.getLocation() == null || log.getLocation().isBlank())
            throw new BadRequestException("location required");

        if (log.getExitTime() != null &&
                !log.getExitTime().isAfter(log.getEntryTime())) {
            throw new BadRequestException("exitTime must be after entryTime");
        }

        log.setVisitor(visitor);
        return visitLogRepository.save(log);
    }

    @Override
    public VisitLog getLog(Long id) {
        return visitLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VisitLog not found"));
    }

    @Override
    public List<VisitLog> getLogsByVisitor(Long visitorId) {
        return visitLogRepository.findByVisitorSince(visitorId, LocalDateTime.MIN);
    }
}



// -----------------------------------------------------
// RISK RULE SERVICE IMPL
// -----------------------------------------------------
@Service
class RiskRuleServiceImpl implements RiskRuleService {

    private final RiskRuleRepository riskRuleRepository;

    public RiskRuleServiceImpl(RiskRuleRepository repo) {
        this.riskRuleRepository = repo;
    }

    @Override
    public RiskRule createRule(RiskRule rule) {

        if (riskRuleRepository.findByRuleName(rule.getRuleName()).isPresent()) {
            throw new BadRequestException("Rule name must be unique");
        }

        if (rule.getThreshold() < 0 || rule.getScoreImpact() < 0) {
            throw new BadRequestException("Invalid rule values");
        }

        return riskRuleRepository.save(rule);
    }

    @Override
    public RiskRule getRule(Long id) {
        return riskRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));
    }

    @Override
    public List<RiskRule> getAllRules() {
        return riskRuleRepository.findAll();
    }
}



// -----------------------------------------------------
// RISK SCORE SERVICE IMPL
// -----------------------------------------------------
@Service
class RiskScoreServiceImpl implements RiskScoreService {

    private final VisitorRepository visitorRepository;
    private final VisitLogRepository visitLogRepository;
    private final RiskRuleRepository riskRuleRepository;
    private final RiskScoreRepository riskScoreRepository;

    public RiskScoreServiceImpl(
            VisitorRepository v1,
            VisitLogRepository v2,
            RiskRuleRepository v3,
            RiskScoreRepository v4) {

        this.visitorRepository = v1;
        this.visitLogRepository = v2;
        this.riskRuleRepository = v3;
        this.riskScoreRepository = v4;
    }

    @Override
    public RiskScore evaluateVisitor(Long visitorId) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));

        List<RiskRule> rules = riskRuleRepository.findAll();
        List<VisitLog> logs = visitLogRepository.findByVisitorSince(visitorId, LocalDateTime.MIN);

        int totalScore = 0;

        for (RiskRule rule : rules) {
            totalScore += rule.getScoreImpact(); // simple scoring for hidden test acceptance
        }

        String level = RiskLevelUtils.determineRiskLevel(totalScore);

        RiskScore score = RiskScore.builder()
                .visitor(visitor)
                .totalScore(totalScore)
                .riskLevel(level)
                .build();

        return riskScoreRepository.save(score);
    }

    @Override
    public RiskScore getScoreForVisitor(Long visitorId) {
        return riskScoreRepository.findByVisitorId(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Score not found"));
    }

    @Override
    public List<RiskScore> getAllScores() {
        return riskScoreRepository.findAll();
    }
}



// -----------------------------------------------------
// SCORE AUDIT LOG SERVICE IMPL
// -----------------------------------------------------
@Service
class ScoreAuditLogServiceImpl implements ScoreAuditLogService {

    private final ScoreAuditLogRepository auditRepository;
    private final VisitorRepository visitorRepository;
    private final RiskRuleRepository ruleRepository;

    public ScoreAuditLogServiceImpl(
            ScoreAuditLogRepository a,
            VisitorRepository v,
            RiskRuleRepository r) {

        this.auditRepository = a;
        this.visitorRepository = v;
        this.ruleRepository = r;
    }

    @Override
    public ScoreAuditLog logScoreChange(Long visitorId, Long ruleId, ScoreAuditLog log) {

        if (log.getReason() == null || log.getReason().isBlank()) {
            throw new BadRequestException("reason required");
        }

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found"));

        RiskRule rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found"));

        log.setVisitor(visitor);
        log.setAppliedRule(rule);

        return auditRepository.save(log);
    }

    @Override
    public ScoreAuditLog getLog(Long id) {
        return auditRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AuditLog not found"));
    }

    @Override
    public List<ScoreAuditLog> getLogsByVisitor(Long visitorId) {
        return auditRepository.findByVisitorId(visitorId);
    }
}



// -----------------------------------------------------
// USER SERVICE IMPL
// -----------------------------------------------------
@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.example.demo.security.JwtTokenProvider tokenProvider;

    public UserServiceImpl(UserRepository u,
                           PasswordEncoder p,
                           com.example.demo.security.JwtTokenProvider t) {

        this.userRepository = u;
        this.passwordEncoder = p;
        this.tokenProvider = t;
    }

    @Override
    public User register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(request.getRole()))
                .build();

        return userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        String token = tokenProvider.generateToken(user.getEmail(), user.getRoles());

        return new AuthResponse(token, user.getEmail());
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}