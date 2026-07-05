package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class CodingPromptBuilder {

    public String buildCodingAnalysisPrompt(String code, String language, String problemStatement) {
        return """
You are a senior Java developer and technical interviewer with 20+ years of experience.

Analyze the provided code thoroughly and provide comprehensive feedback.

LANGUAGE: %s
PROBLEM STATEMENT: %s

CODE:
---
%s
---

Please analyze the code and provide your evaluation in the following JSON format:
{
  "bug_detection": ["List of bugs found in the code"],
  "syntax_issues": ["Syntax errors or warnings"],
  "logical_errors": ["Logical flaws in the algorithm"],
  "time_complexity": "Big O time complexity analysis (e.g., O(n log n))",
  "space_complexity": "Big O space complexity analysis (e.g., O(n))",
  "optimized_solution": "Brief explanation of how to optimize this code",
  "clean_code_suggestions": ["Code style and readability improvements"],
  "interview_tips": ["What to explain during interviews about this approach"],
  "overall_verdict": "Overall assessment of the code quality and readiness for interview"
}

Be specific, constructive, and provide actionable recommendations. Point out both strengths and weaknesses.
""".formatted(
            language != null ? language : "Java",
            problemStatement != null && !problemStatement.isEmpty() ? problemStatement : "Not provided",
            code
        );
    }
}
