package com.careerpilot.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "coding_submissions")
@EntityListeners(AuditingEntityListener.class)
public class CodingSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private CodingQuestion question;

    @Column(name = "submitted_code", nullable = false, columnDefinition = "TEXT")
    private String submittedCode;

    @Column(name = "language", nullable = false, length = 30)
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private SubmissionStatus status;

    @Column(name = "score")
    private Integer score;

    @Column(name = "attempts", nullable = false)
    private Integer attempts;

    @CreatedDate
    @Column(name = "submitted_at", nullable = false, updatable = false)
    private LocalDateTime submittedAt;

    public CodingSubmission() {
        this.attempts = 1;
        this.status   = SubmissionStatus.SUBMITTED;
    }

    public Long getId()                         { return id; }
    public void setId(Long v)                   { this.id = v; }
    public User getUser()                       { return user; }
    public void setUser(User v)                 { this.user = v; }
    public CodingQuestion getQuestion()         { return question; }
    public void setQuestion(CodingQuestion v)   { this.question = v; }
    public String getSubmittedCode()            { return submittedCode; }
    public void setSubmittedCode(String v)      { this.submittedCode = v; }
    public String getLanguage()                 { return language; }
    public void setLanguage(String v)           { this.language = v; }
    public SubmissionStatus getStatus()         { return status; }
    public void setStatus(SubmissionStatus v)   { this.status = v; }
    public Integer getScore()                   { return score; }
    public void setScore(Integer v)             { this.score = v; }
    public Integer getAttempts()                { return attempts; }
    public void setAttempts(Integer v)          { this.attempts = v; }
    public LocalDateTime getSubmittedAt()       { return submittedAt; }
    public void setSubmittedAt(LocalDateTime v) { this.submittedAt = v; }
}
