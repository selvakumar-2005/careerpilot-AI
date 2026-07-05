package com.careerpilot.service;

import com.careerpilot.dto.ResumeDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeDto uploadResume(MultipartFile file, Long userId);

    List<ResumeDto> getMyResumes(Long userId);

    ResumeDto getResumeById(Long resumeId, Long userId);

    Resource downloadResume(Long resumeId, Long userId);

    void deleteResume(Long resumeId, Long userId);
}
