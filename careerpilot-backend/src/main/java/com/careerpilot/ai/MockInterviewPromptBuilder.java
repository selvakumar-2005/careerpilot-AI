package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class MockInterviewPromptBuilder {

    public String buildMockInterviewQuestionPrompt(String jobRole, String experienceLevel, String company, String technology, Integer questionNumber, Integer totalQuestions) {
        return String.format(
            "You are an expert technical interviewer for %s at %s.\n\n" +
            "Generate interview question #%d of %d for a %s level %s candidate.\n\n" +
            "REQUIREMENTS:\n" +
            "1. Question should test practical knowledge and problem-solving skills\n" +
            "2. Question should be relevant to %s technology\n" +
            "3. Question difficulty should match %s level (entry/mid/senior)\n" +
            "4. Provide a single, clear, focused question\n" +
            "5. Avoid yes/no questions\n\n" +
            "Respond in JSON format:\n" +
            "{\n" +
            "  \"question\": \"<Interview question>\",\n" +
            "  \"question_number\": %d,\n" +
            "  \"total_questions\": %d,\n" +
            "  \"expected_topics\": [\"<Topic 1>\", \"<Topic 2>\"]\n" +
            "}\n",
            company, company, questionNumber, totalQuestions, experienceLevel, jobRole,
            technology, experienceLevel, questionNumber, totalQuestions
        );
    }

    public String buildAnswerEvaluationPrompt(String jobRole, String experienceLevel, String question, String userAnswer, String technology) {
        return String.format(
            "You are an expert technical interviewer evaluating a candidate's response.\n\n" +
            "JOB ROLE: %s\n" +
            "EXPERIENCE LEVEL: %s\n" +
            "TECHNOLOGY: %s\n\n" +
            "INTERVIEW QUESTION:\n%s\n\n" +
            "CANDIDATE'S ANSWER:\n%s\n\n" +
            "Evaluate the answer comprehensively and provide feedback in JSON format:\n" +
            "{\n" +
            "  \"score\": <0-100>,\n" +
            "  \"strengths\": [\"<Strength 1>\", \"<Strength 2>\", \"<Strength 3>\"],\n" +
            "  \"weaknesses\": [\"<Weakness 1>\", \"<Weakness 2>\"],\n" +
            "  \"improvement_suggestions\": [\"<Suggestion 1>\", \"<Suggestion 2>\"],\n" +
            "  \"feedback\": \"<Detailed feedback on the answer>\"\n" +
            "}\n",
            jobRole, experienceLevel, technology, question, userAnswer
        );
    }

    public String buildInterviewSummaryPrompt(String jobRole, String experienceLevel, String scores, Double averageScore) {
        return String.format(
            "You are summarizing a mock interview performance.\n\n" +
            "JOB ROLE: %s\n" +
            "EXPERIENCE LEVEL: %s\n" +
            "SCORES: %s\n" +
            "AVERAGE SCORE: %.1f/100\n\n" +
            "Provide an overall performance summary and recommendations in JSON format:\n" +
            "{\n" +
            "  \"overall_performance\": \"<Excellent/Good/Average/Needs Improvement>\",\n" +
            "  \"average_score\": %.1f,\n" +
            "  \"key_strengths\": [\"<Strength 1>\", \"<Strength 2>\"],\n" +
            "  \"areas_to_improve\": [\"<Area 1>\", \"<Area 2>\"],\n" +
            "  \"preparation_recommendations\": [\"<Recommendation 1>\", \"<Recommendation 2>\"],\n" +
            "  \"next_steps\": \"<Next steps for interview preparation>\"\n" +
            "}\n",
            jobRole, experienceLevel, scores, averageScore, averageScore
        );
    }
}
