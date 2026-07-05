package com.careerpilot.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Persists every successful Gemini API call.
 * Failed requests are NOT logged here — they surface as exceptions.
 *
 * Table: ai_request_logs
 * Relationship: many-to-one with users (nullable — test calls without auth won't link a user)
 */
@Entity
@Table(name = "ai_request_logs",
       indexes = { @Index(name = "idx_ai_log_user", columnList = "user_id"),
                   @Index(name = "idx_ai_log_created", columnList = "created_at") })
@EntityListeners(AuditingEntityListener.class)
public class AIRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /** Truncated prompt — max 5000 chars stored in DB. */
    @Column(name = "prompt", nullable = false, columnDefinition = "TEXT")
    private String prompt;

    /** Truncated Gemini response — max 10000 chars. */
    @Column(name = "response", columnDefinition = "MEDIUMTEXT")
    private String response;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public AIRequestLog() {}

    // ── Getters & Setters ──────────────────────────────────────────────────
    public Long          getId()                 { return id; }
    public void          setId(Long v)           { this.id = v; }
    public User          getUser()               { return user; }
    public void          setUser(User v)         { this.user = v; }
    public String        getPrompt()             { return prompt; }
    public void          setPrompt(String v)     { this.prompt = v; }
    public String        getResponse()           { return response; }
    public void          setResponse(String v)   { this.response = v; }
    public LocalDateTime getCreatedAt()          { return createdAt; }
    public void          setCreatedAt(LocalDateTime v) { this.createdAt = v; }
}
