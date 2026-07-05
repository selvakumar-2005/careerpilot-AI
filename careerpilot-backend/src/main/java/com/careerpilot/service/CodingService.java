package com.careerpilot.service;

import com.careerpilot.dto.CodingQuestionDto;
import com.careerpilot.dto.CodingSubmissionDto;
import com.careerpilot.dto.SubmitCodeRequest;
import com.careerpilot.entity.Difficulty;

import java.util.List;

public interface CodingService {

    List<CodingQuestionDto> getQuestions(Difficulty difficulty, String search);

    CodingQuestionDto getQuestionById(Long id);

    CodingSubmissionDto submitCode(SubmitCodeRequest request, Long userId);

    List<CodingSubmissionDto> getMySubmissions(Long userId);

    List<CodingSubmissionDto> getSubmissionsByQuestion(Long questionId, Long userId);
}
