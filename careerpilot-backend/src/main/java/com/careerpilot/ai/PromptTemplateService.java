package com.careerpilot.ai;

import com.careerpilot.exception.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Central prompt management service.
 *
 * Design decisions:
 *  - Every prompt is a named template stored as a Java String constant.
 *  - Templates use a single placeholder {INPUT} for user-supplied content.
 *  - No prompts are hardcoded in controllers or services — all callers
 *    go through buildPrompt(templateKey, userContent).
 *  - In Part 3.2 the contents of each template will be fleshed out.
 *    For now each template has a clearly labelled structure so that
 *    every AI module can already be wired up and tested end-to-end.
 *
 * Supported template keys (case-insensitive):
 *   RESUME_ANALYSIS, CAREER_RECOMMENDATION, INTERVIEW_QUESTIONS,
 *   MOCK_INTERVIEW_EVAL, CODING_ASSISTANT, COMPANY_PREPARATION,
 *   CAREER_CHAT, TEST
 */
@Service
public class PromptTemplateService {

    // ── Template key constants ────────────────────────────────────────────
    public static final String RESUME_ANALYSIS       = "RESUME_ANALYSIS";
    public static final String CAREER_RECOMMENDATION = "CAREER_RECOMMENDATION";
    public static final String INTERVIEW_QUESTIONS   = "INTERVIEW_QUESTIONS";
    public static final String MOCK_INTERVIEW_EVAL   = "MOCK_INTERVIEW_EVAL";
    public static final String CODING_ASSISTANT      = "CODING_ASSISTANT";
    public static final String COMPANY_PREPARATION   = "COMPANY_PREPARATION";
    public static final String CAREER_CHAT           = "CAREER_CHAT";
    public static final String TEST                  = "TEST";

    // ── Template bodies ───────────────────────────────────────────────────

    private static final String RESUME_ANALYSIS_TEMPLATE =
        "You are a professional resume reviewer and career coach.\n" +
        "Analyze the following resume text and return a structured evaluation.\n\n" +
        "Resume Text:\n{INPUT}\n\n" +
        "Return your analysis using exactly these sections:\n" +
        "1. Professional Summary\n" +
        "2. Skills Detected\n" +
        "3. Missing Skills\n" +
        "4. Strengths\n" +
        "5. Weaknesses\n" +
        "6. ATS Score (0-100 with brief reasoning)\n" +
        "7. Resume Improvement Suggestions\n" +
        "8. Recommended Certifications\n" +
        "9. Recommended Technologies\n" +
        "10. Suggested Job Roles\n\n" +
        "Be specific, actionable, and concise.";

    private static final String CAREER_RECOMMENDATION_TEMPLATE =
        "You are an expert career counselor specializing in technology placement.\n" +
        "Based on the following profile, provide personalized career recommendations.\n\n" +
        "Profile:\n{INPUT}\n\n" +
        "Return recommendations in these sections:\n" +
        "1. Recommended Job Roles (top 5)\n" +
        "2. Why Each Role Fits\n" +
        "3. Skills Required per Role\n" +
        "4. Skills Gap\n" +
        "5. Learning Roadmap (week-by-week for 12 weeks)\n" +
        "6. Approximate Salary Range (INR per annum)\n" +
        "7. Interview Preparation Tips per Role\n\n" +
        "Be practical and tailored to the Indian job market.";

    private static final String INTERVIEW_QUESTIONS_TEMPLATE =
        "You are a senior technical interviewer at a top tech company.\n" +
        "Generate a comprehensive set of interview questions for the following context.\n\n" +
        "Context:\n{INPUT}\n\n" +
        "Return questions in these categories:\n" +
        "1. Technical Questions (10 questions with expected answers)\n" +
        "2. HR Questions (5 questions with expected answers)\n" +
        "3. Behavioral Questions (5 questions with expected answers)\n\n" +
        "For each question include:\n" +
        "- The question\n" +
        "- Expected answer / key points\n" +
        "- Difficulty level (Easy/Medium/Hard)\n\n" +
        "Focus on depth and relevance to the specific context provided.";

    private static final String MOCK_INTERVIEW_EVAL_TEMPLATE =
        "You are an expert interviewer evaluating a candidate's response.\n\n" +
        "Interview context and candidate answer:\n{INPUT}\n\n" +
        "Evaluate the response across these dimensions:\n" +
        "1. Technical Quality (score 1-10)\n" +
        "2. Grammar and Communication (score 1-10)\n" +
        "3. Clarity and Structure (score 1-10)\n" +
        "4. Completeness (score 1-10)\n" +
        "5. Overall Score (average)\n\n" +
        "Then provide:\n" +
        "6. Detailed Feedback (what was good)\n" +
        "7. Areas for Improvement\n" +
        "8. Model Answer (ideal response)\n" +
        "9. Tips for Next Time\n\n" +
        "Be constructive, honest, and specific.";

    private static final String CODING_ASSISTANT_TEMPLATE =
        "You are an expert programming mentor helping a student understand a coding problem.\n\n" +
        "Problem / Question:\n{INPUT}\n\n" +
        "Provide help in this order:\n" +
        "1. Problem Explanation (in simple terms)\n" +
        "2. Hint (without giving away the solution)\n" +
        "3. Optimal Approach (algorithm name and brief description)\n" +
        "4. Time Complexity\n" +
        "5. Space Complexity\n" +
        "6. Edge Cases to Consider\n\n" +
        "Do NOT provide the full solution code unless the student explicitly asks for it.\n" +
        "Encourage thinking and learning.";

    private static final String COMPANY_PREPARATION_TEMPLATE =
        "You are a placement expert with deep knowledge of the IT industry.\n\n" +
        "Company / Context:\n{INPUT}\n\n" +
        "Provide a complete preparation guide:\n" +
        "1. Company Overview\n" +
        "2. Common Interview Topics\n" +
        "3. Expected Technical Stack\n" +
        "4. Preparation Tips (actionable, step-by-step)\n" +
        "5. Suggested Coding Topics\n" +
        "6. HR Round Preparation Tips\n" +
        "7. Frequently Asked Questions (5 questions with short answers)\n\n" +
        "Tailor everything specifically to the company mentioned.";

    private static final String CAREER_CHAT_TEMPLATE =
        "You are CareerPilot, an AI career assistant for placement preparation.\n" +
        "You help students with resume improvement, career planning, technical skills,\n" +
        "interview preparation, and placement guidance.\n\n" +
        "Conversation / Question:\n{INPUT}\n\n" +
        "Respond helpfully, concisely, and professionally.\n" +
        "If the question is outside career/placement topics, politely redirect.\n" +
        "Always encourage and motivate the student.";

    private static final String TEST_TEMPLATE =
        "You are a helpful AI assistant.\n\n" +
        "User input:\n{INPUT}\n\n" +
        "Respond clearly and helpfully.";

    // ── Public API ────────────────────────────────────────────────────────

    /**
     * Builds a complete prompt by injecting userContent into the named template.
     *
     * @param templateKey  One of the public constants defined in this class
     * @param userContent  The user-supplied text to inject into {INPUT}
     * @return             Full prompt string ready to send to Gemini
     */
    public String buildPrompt(String templateKey, String userContent) {
        String template = getTemplate(templateKey);
        return template.replace("{INPUT}", userContent == null ? "" : userContent.trim());
    }

    /**
     * Builds a prompt with extra parameters beyond the primary content.
     * extraParams is appended after the {INPUT} substitution.
     */
    public String buildPrompt(String templateKey, String userContent, String extraParams) {
        String base = buildPrompt(templateKey, userContent);
        if (extraParams != null && !extraParams.isBlank()) {
            return base + "\n\nAdditional context: " + extraParams.trim();
        }
        return base;
    }

    /** Returns the raw template string for a given key. */
    public String getTemplate(String templateKey) {
        if (templateKey == null) throw new BadRequestException("Template key cannot be null");
        return switch (templateKey.toUpperCase()) {
            case RESUME_ANALYSIS       -> RESUME_ANALYSIS_TEMPLATE;
            case CAREER_RECOMMENDATION -> CAREER_RECOMMENDATION_TEMPLATE;
            case INTERVIEW_QUESTIONS   -> INTERVIEW_QUESTIONS_TEMPLATE;
            case MOCK_INTERVIEW_EVAL   -> MOCK_INTERVIEW_EVAL_TEMPLATE;
            case CODING_ASSISTANT      -> CODING_ASSISTANT_TEMPLATE;
            case COMPANY_PREPARATION   -> COMPANY_PREPARATION_TEMPLATE;
            case CAREER_CHAT           -> CAREER_CHAT_TEMPLATE;
            case TEST                  -> TEST_TEMPLATE;
            default -> throw new BadRequestException(
                "Unknown template key: '" + templateKey + "'. " +
                "Valid keys: RESUME_ANALYSIS, CAREER_RECOMMENDATION, INTERVIEW_QUESTIONS, " +
                "MOCK_INTERVIEW_EVAL, CODING_ASSISTANT, COMPANY_PREPARATION, CAREER_CHAT, TEST");
        };
    }

    /** Returns all valid template keys. */
    public String[] getAllTemplateKeys() {
        return new String[]{
            RESUME_ANALYSIS, CAREER_RECOMMENDATION, INTERVIEW_QUESTIONS,
            MOCK_INTERVIEW_EVAL, CODING_ASSISTANT, COMPANY_PREPARATION,
            CAREER_CHAT, TEST
        };
    }
}
