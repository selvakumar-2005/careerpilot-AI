package com.careerpilot.service.impl;

import com.careerpilot.dto.ResumeDto;
import com.careerpilot.entity.Resume;
import com.careerpilot.entity.ResumeStatus;
import com.careerpilot.entity.User;
import com.careerpilot.exception.BadRequestException;
import com.careerpilot.exception.ResourceNotFoundException;
import com.careerpilot.repository.ResumeRepository;
import com.careerpilot.repository.UserRepository;
import com.careerpilot.service.ResumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    private static final Logger log = LoggerFactory.getLogger(ResumeServiceImpl.class);
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024L; // 5 MB

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final Path uploadDir;

    public ResumeServiceImpl(ResumeRepository resumeRepository,
                             UserRepository userRepository,
                             @Value("${app.upload.dir:uploads/resumes}") String uploadDirPath) {
        this.resumeRepository = resumeRepository;
        this.userRepository   = userRepository;
        this.uploadDir        = Paths.get(uploadDirPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + this.uploadDir, e);
        }
    }

    @Override
    @Transactional
    public ResumeDto uploadResume(MultipartFile file, Long userId) {
        // Validate file presence
        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Please select a file to upload");
        }

        // Validate PDF type
        String contentType = file.getContentType();
        if (contentType == null || !contentType.equalsIgnoreCase("application/pdf")) {
            throw new BadRequestException("Only PDF files are allowed");
        }

        // Validate size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File size must not exceed 5 MB");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Generate a unique file name to prevent collisions
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueName   = UUID.randomUUID() + "_" + originalName;
        Path targetPath     = this.uploadDir.resolve(uniqueName);

        try {
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Failed to store file {}", uniqueName, e);
            throw new BadRequestException("Failed to store file. Please try again.");
        }

        Resume resume = new Resume();
        resume.setUser(user);
        resume.setFileName(uniqueName);
        resume.setOriginalFileName(originalName);
        resume.setFilePath(targetPath.toString());
        resume.setFileSize(file.getSize());
        resume.setStatus(ResumeStatus.ACTIVE);

        Resume saved = resumeRepository.save(resume);
        log.info("Resume uploaded by user {} → file {}", userId, uniqueName);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeDto> getMyResumes(Long userId) {
        return resumeRepository
                .findByUserIdAndStatusNotOrderByUploadDateDesc(userId, ResumeStatus.DELETED)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeDto getResumeById(Long resumeId, Long userId) {
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));
        return mapToDto(resume);
    }

    @Override
    @Transactional(readOnly = true)
    public Resource downloadResume(Long resumeId, Long userId) {
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));
        try {
            Path filePath = Paths.get(resume.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new BadRequestException("File not found or not readable");
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new BadRequestException("Could not resolve file path");
        }
    }

    @Override
    @Transactional
    public void deleteResume(Long resumeId, Long userId) {
        Resume resume = resumeRepository.findByIdAndUserId(resumeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume", "id", resumeId));
        resume.setStatus(ResumeStatus.DELETED);
        resumeRepository.save(resume);
        log.info("Resume {} soft-deleted by user {}", resumeId, userId);
    }

    private ResumeDto mapToDto(Resume r) {
        ResumeDto dto = new ResumeDto();
        dto.setId(r.getId());
        dto.setFileName(r.getFileName());
        dto.setOriginalFileName(r.getOriginalFileName());
        dto.setFileSize(r.getFileSize());
        dto.setUploadDate(r.getUploadDate());
        dto.setStatus(r.getStatus());
        dto.setUserId(r.getUser().getId());
        return dto;
    }
}
