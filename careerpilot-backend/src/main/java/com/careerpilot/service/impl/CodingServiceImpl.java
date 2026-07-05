package com.careerpilot.service.impl;

import com.careerpilot.dto.CodingQuestionDto;
import com.careerpilot.dto.CodingSubmissionDto;
import com.careerpilot.dto.SubmitCodeRequest;
import com.careerpilot.entity.*;
import com.careerpilot.exception.BadRequestException;
import com.careerpilot.exception.ResourceNotFoundException;
import com.careerpilot.repository.CodingQuestionRepository;
import com.careerpilot.repository.CodingSubmissionRepository;
import com.careerpilot.repository.UserRepository;
import com.careerpilot.service.CodingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodingServiceImpl implements CodingService {

    private static final Logger log = LoggerFactory.getLogger(CodingServiceImpl.class);

    private final CodingQuestionRepository questionRepository;
    private final CodingSubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    public CodingServiceImpl(CodingQuestionRepository questionRepository,
                             CodingSubmissionRepository submissionRepository,
                             UserRepository userRepository) {
        this.questionRepository   = questionRepository;
        this.submissionRepository = submissionRepository;
        this.userRepository       = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodingQuestionDto> getQuestions(Difficulty difficulty, String search) {
        List<CodingQuestion> questions = questionRepository.searchQuestions(difficulty, search);
        return questions.stream().map(this::mapQuestionToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CodingQuestionDto getQuestionById(Long id) {
        CodingQuestion question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CodingQuestion", "id", id));
        return mapQuestionToDto(question);
    }

    @Override
    @Transactional
    public CodingSubmissionDto submitCode(SubmitCodeRequest request, Long userId) {
        if (request.getSubmittedCode() == null || request.getSubmittedCode().isBlank()) {
            throw new BadRequestException("Submitted code cannot be empty");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        CodingQuestion question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new ResourceNotFoundException("CodingQuestion", "id", request.getQuestionId()));

        // Increment attempts if already submitted
        int attemptCount = (int) submissionRepository
                .findByUserIdAndQuestionIdOrderBySubmittedAtDesc(userId, request.getQuestionId())
                .size() + 1;

        CodingSubmission submission = new CodingSubmission();
        submission.setUser(user);
        submission.setQuestion(question);
        submission.setSubmittedCode(request.getSubmittedCode());
        submission.setLanguage(request.getLanguage());
        submission.setStatus(SubmissionStatus.SUBMITTED);
        submission.setScore(0);
        submission.setAttempts(attemptCount);

        CodingSubmission saved = submissionRepository.save(submission);
        log.info("Code submitted by user {} for question {}, attempt #{}", userId, question.getId(), attemptCount);
        return mapSubmissionToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodingSubmissionDto> getMySubmissions(Long userId) {
        return submissionRepository.findByUserIdOrderBySubmittedAtDesc(userId)
                .stream().map(this::mapSubmissionToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CodingSubmissionDto> getSubmissionsByQuestion(Long questionId, Long userId) {
        return submissionRepository
                .findByUserIdAndQuestionIdOrderBySubmittedAtDesc(userId, questionId)
                .stream().map(this::mapSubmissionToDto).collect(Collectors.toList());
    }

    private CodingQuestionDto mapQuestionToDto(CodingQuestion q) {
        CodingQuestionDto dto = new CodingQuestionDto();
        dto.setId(q.getId());
        dto.setTitle(q.getTitle());
        dto.setDescription(q.getDescription());
        dto.setDifficulty(q.getDifficulty());
        dto.setInputFormat(q.getInputFormat());
        dto.setOutputFormat(q.getOutputFormat());
        dto.setConstraints(q.getConstraints());
        dto.setSampleInput(q.getSampleInput());
        dto.setSampleOutput(q.getSampleOutput());
        dto.setTopic(q.getTopic());
        return dto;
    }

    private CodingSubmissionDto mapSubmissionToDto(CodingSubmission s) {
        CodingSubmissionDto dto = new CodingSubmissionDto();
        dto.setId(s.getId());
        dto.setQuestionId(s.getQuestion().getId());
        dto.setQuestionTitle(s.getQuestion().getTitle());
        dto.setSubmittedCode(s.getSubmittedCode());
        dto.setLanguage(s.getLanguage());
        dto.setStatus(s.getStatus());
        dto.setScore(s.getScore());
        dto.setAttempts(s.getAttempts());
        dto.setSubmittedAt(s.getSubmittedAt());
        return dto;
    }
}
