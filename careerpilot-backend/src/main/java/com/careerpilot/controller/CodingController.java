package com.careerpilot.controller;

import com.careerpilot.dto.ApiResponse;
import com.careerpilot.dto.CodingQuestionDto;
import com.careerpilot.dto.CodingSubmissionDto;
import com.careerpilot.dto.SubmitCodeRequest;
import com.careerpilot.entity.Difficulty;
import com.careerpilot.security.UserPrincipal;
import com.careerpilot.service.CodingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coding")
public class CodingController {

    private final CodingService codingService;

    public CodingController(CodingService codingService) {
        this.codingService = codingService;
    }

    /** List all questions, optionally filtered by difficulty and/or search keyword */
    @GetMapping("/questions")
    public ResponseEntity<ApiResponse<List<CodingQuestionDto>>> getQuestions(
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) String search) {

        List<CodingQuestionDto> questions = codingService.getQuestions(difficulty, search);
        return ResponseEntity.ok(ApiResponse.success("Questions retrieved", questions));
    }

    /** Get full details for one question */
    @GetMapping("/questions/{id}")
    public ResponseEntity<ApiResponse<CodingQuestionDto>> getQuestion(@PathVariable Long id) {
        CodingQuestionDto dto = codingService.getQuestionById(id);
        return ResponseEntity.ok(ApiResponse.success("Question retrieved", dto));
    }

    /** Submit code for a question */
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<CodingSubmissionDto>> submit(
            @Valid @RequestBody SubmitCodeRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {

        CodingSubmissionDto dto = codingService.submitCode(request, principal.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Code submitted successfully", dto));
    }

    /** All submissions by the current user */
    @GetMapping("/submissions")
    public ResponseEntity<ApiResponse<List<CodingSubmissionDto>>> getMySubmissions(
            @AuthenticationPrincipal UserPrincipal principal) {

        List<CodingSubmissionDto> list = codingService.getMySubmissions(principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Submissions retrieved", list));
    }

    /** Submissions for a specific question by the current user */
    @GetMapping("/questions/{questionId}/submissions")
    public ResponseEntity<ApiResponse<List<CodingSubmissionDto>>> getSubmissionsByQuestion(
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserPrincipal principal) {

        List<CodingSubmissionDto> list = codingService.getSubmissionsByQuestion(questionId, principal.getId());
        return ResponseEntity.ok(ApiResponse.success("Submissions retrieved", list));
    }
}
