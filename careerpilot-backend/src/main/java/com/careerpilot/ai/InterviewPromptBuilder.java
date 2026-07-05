package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class InterviewPromptBuilder {

    public String buildInterviewGenerationPrompt(String company, String technology, 
                                                 String difficulty, String experienceLevel) {
        return """
You are a senior technical interviewer with 15+ years of experience conducting interviews at top tech companies.

Generate a comprehensive interview preparation package based on the student's profile.

INTERVIEW DETAILS:
- Target Company: %s
- Technology: %s
- Difficulty Level: %s
- Experience Level: %s

Please generate interview questions in the following JSON format:
{
  "technical_questions": [
    {
      "question": "Question text",
      "model_answer": "Detailed model answer",
      "follow_up_questions": ["Follow-up 1", "Follow-up 2"]
    }
    // 10 questions total
  ],
  "hr_questions": [
    {
      "question": "HR question",
      "model_answer": "Model answer focusing on communication",
      "follow_up_questions": ["Follow-up for HR question"]
    }
    // 10 questions total
  ],
  "interview_tips": ["5-7 practical tips specific to this company and role"],
  "confidence_tips": ["5 tips to build confidence during the interview"]
}

Make questions realistic, challenging, and similar to what is actually asked at %s.
Provide comprehensive model answers that demonstrate best practices.
""".formatted(
            company != null ? company : "Top Tech Company",
            technology != null ? technology : "Java",
            difficulty != null ? difficulty : "Medium",
            experienceLevel != null ? experienceLevel : "Fresher",
            company != null ? company : "Target Company"
        );
    }
}
