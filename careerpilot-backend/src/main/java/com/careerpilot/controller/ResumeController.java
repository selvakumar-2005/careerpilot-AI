package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.ResumeDto;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.ResumeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    /** Upload a PDF resume (multipart/form-data, field name: file) */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ResumeDto>> upload(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal principal) {

        ResumeDto dto = resumeService.uploadResume(file, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Resume uploaded successfully", dto));
    }

    /** List all active resumes for the logged-in user */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ResumeDto>>> getMyResumes(
            @AuthenticationPrincipal UserPrincipal principal) {

        List<ResumeDto> list = resumeService.getMyResumes(principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Resumes retrieved", list));
    }

    /** Get a single resume by ID */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResumeDto>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        ResumeDto dto = resumeService.getResumeById(id, principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Resume retrieved", dto));
    }

    /** Download the PDF file */
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        Resource resource      = resumeService.downloadResume(id, principal.getId());
        ResumeDto meta         = resumeService.getResumeById(id, principal.getId());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + meta.getOriginalFileName() + "\"")
                .body(resource);
    }

    /** Soft-delete a resume */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        resumeService.deleteResume(id, principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Resume deleted successfully"));
    }
}
