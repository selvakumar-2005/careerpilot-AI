package com.careerpilot.service.impl;

import com.careerpilot.dto.CodingSubmissionDto;
import com.careerpilot.dto.DashboardStatsDto;
import com.careerpilot.dto.ResumeDto;
import com.careerpilot.entity.CodingSubmission;
import com.careerpilot.entity.Resume;
import com.careerpilot.entity.ResumeStatus;
import com.careerpilot.repository.CodingQuestionRepository;
import com.careerpilot.repository.CodingSubmissionRepository;
import com.careerpilot.repository.CompanyRepository;
import com.careerpilot.repository.ResumeRepository;
import com.careerpilot.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ResumeRepository resumeRepository;
    private final CodingQuestionRepository questionRepository;
    private final CodingSubmissionRepository submissionRepository;
    private final CompanyRepository companyRepository;

    public DashboardServiceImpl(ResumeRepository resumeRepository,
                                CodingQuestionRepository questionRepository,
                                CodingSubmissionRepository submissionRepository,
                                CompanyRepository companyRepository) {
        this.resumeRepository     = resumeRepository;
        this.questionRepository   = questionRepository;
        this.submissionRepository = submissionRepository;
        this.companyRepository    = companyRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DashboardStatsDto getStats(Long userId) {
        DashboardStatsDto stats = new DashboardStatsDto();

        // Resume stats
        long totalResumes = resumeRepository.countByUserIdAndStatusNot(userId, ResumeStatus.DELETED);
        stats.setTotalResumes(totalResumes);

        // Coding stats
        stats.setTotalCodingQuestions(questionRepository.count());
        stats.setSolvedQuestions(submissionRepository.countDistinctQuestionsByUserId(userId));
        stats.setTotalSubmissions(submissionRepository.countByUserId(userId));

        // Company count
        stats.setTotalCompanies(companyRepository.count());

        // Recent resumes (last 3)
        List<ResumeDto> recentResumes = resumeRepository
                .findByUserIdAndStatusNotOrderByUploadDateDesc(userId, ResumeStatus.DELETED)
                .stream().limit(3).map(this::mapResume).collect(Collectors.toList());
        stats.setRecentResumes(recentResumes);

        // Recent submissions (last 5)
        List<CodingSubmissionDto> recentSubs = submissionRepository
                .findTop5ByUserIdOrderBySubmittedAtDesc(userId)
                .stream().map(this::mapSubmission).collect(Collectors.toList());
        stats.setRecentSubmissions(recentSubs);

        return stats;
    }

    private ResumeDto mapResume(Resume r) {
        ResumeDto dto = new ResumeDto();
        dto.setId(r.getId());
        dto.setOriginalFileName(r.getOriginalFileName());
        dto.setFileSize(r.getFileSize());
        dto.setUploadDate(r.getUploadDate());
        dto.setStatus(r.getStatus());
        dto.setUserId(r.getUser().getId());
        return dto;
    }

    private CodingSubmissionDto mapSubmission(CodingSubmission s) {
        CodingSubmissionDto dto = new CodingSubmissionDto();
        dto.setId(s.getId());
        dto.setQuestionId(s.getQuestion().getId());
        dto.setQuestionTitle(s.getQuestion().getTitle());
        dto.setLanguage(s.getLanguage());
        dto.setStatus(s.getStatus());
        dto.setScore(s.getScore());
        dto.setAttempts(s.getAttempts());
        dto.setSubmittedAt(s.getSubmittedAt());
        return dto;
    }
}
