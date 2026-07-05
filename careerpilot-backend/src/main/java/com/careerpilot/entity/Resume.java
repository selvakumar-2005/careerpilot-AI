package com.careerpilot.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@EntityListeners(AuditingEntityListener.class)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @CreatedDate
    @Column(name = "upload_date", nullable = false, updatable = false)
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ResumeStatus status;

    public Resume() {}

    // ── Getters & Setters ──────────────────────────────────────────────
    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }
    public User getUser()                        { return user; }
    public void setUser(User user)               { this.user = user; }
    public String getFileName()                  { return fileName; }
    public void setFileName(String v)            { this.fileName = v; }
    public String getOriginalFileName()          { return originalFileName; }
    public void setOriginalFileName(String v)    { this.originalFileName = v; }
    public String getFilePath()                  { return filePath; }
    public void setFilePath(String v)            { this.filePath = v; }
    public Long getFileSize()                    { return fileSize; }
    public void setFileSize(Long v)              { this.fileSize = v; }
    public LocalDateTime getUploadDate()         { return uploadDate; }
    public void setUploadDate(LocalDateTime v)   { this.uploadDate = v; }
    public ResumeStatus getStatus()              { return status; }
    public void setStatus(ResumeStatus v)        { this.status = v; }
}
