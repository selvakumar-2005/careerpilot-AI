package com.careerpilot.dto;

import com.careerpilot.entity.ResumeStatus;

import java.time.LocalDateTime;

public class ResumeDto {

    private Long id;
    private String fileName;
    private String originalFileName;
    private Long fileSize;
    private LocalDateTime uploadDate;
    private ResumeStatus status;
    private Long userId;

    public ResumeDto() {}

    // ── Getters & Setters ──────────────────────────────────────────────
    public Long getId()                           { return id; }
    public void setId(Long v)                     { this.id = v; }
    public String getFileName()                   { return fileName; }
    public void setFileName(String v)             { this.fileName = v; }
    public String getOriginalFileName()           { return originalFileName; }
    public void setOriginalFileName(String v)     { this.originalFileName = v; }
    public Long getFileSize()                     { return fileSize; }
    public void setFileSize(Long v)               { this.fileSize = v; }
    public LocalDateTime getUploadDate()          { return uploadDate; }
    public void setUploadDate(LocalDateTime v)    { this.uploadDate = v; }
    public ResumeStatus getStatus()               { return status; }
    public void setStatus(ResumeStatus v)         { this.status = v; }
    public Long getUserId()                       { return userId; }
    public void setUserId(Long v)                 { this.userId = v; }
}
