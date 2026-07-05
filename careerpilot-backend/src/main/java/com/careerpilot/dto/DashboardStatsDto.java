package com.careerpilot.dto;

import java.util.List;

public class DashboardStatsDto {

    private long totalResumes;
    private long totalCodingQuestions;
    private long solvedQuestions;
    private long totalSubmissions;
    private long totalCompanies;
    private List<ResumeDto> recentResumes;
    private List<CodingSubmissionDto> recentSubmissions;

    public DashboardStatsDto() {}

    public long getTotalResumes()                              { return totalResumes; }
    public void setTotalResumes(long v)                        { this.totalResumes = v; }
    public long getTotalCodingQuestions()                      { return totalCodingQuestions; }
    public void setTotalCodingQuestions(long v)                { this.totalCodingQuestions = v; }
    public long getSolvedQuestions()                           { return solvedQuestions; }
    public void setSolvedQuestions(long v)                     { this.solvedQuestions = v; }
    public long getTotalSubmissions()                          { return totalSubmissions; }
    public void setTotalSubmissions(long v)                    { this.totalSubmissions = v; }
    public long getTotalCompanies()                            { return totalCompanies; }
    public void setTotalCompanies(long v)                      { this.totalCompanies = v; }
    public List<ResumeDto> getRecentResumes()                  { return recentResumes; }
    public void setRecentResumes(List<ResumeDto> v)            { this.recentResumes = v; }
    public List<CodingSubmissionDto> getRecentSubmissions()    { return recentSubmissions; }
    public void setRecentSubmissions(List<CodingSubmissionDto> v) { this.recentSubmissions = v; }
}
