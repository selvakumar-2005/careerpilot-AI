package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class ResumePromptBuilder {

    public String buildResumeAnalysisPrompt(String resumeText, String jobDescription, String targetCompany) {
        return """
You are an expert ATS (Applicant Tracking System) Resume Analyzer and HR professional with 15+ years of experience.

Analyze the provided resume carefully and provide a comprehensive evaluation.

RESUME TEXT:
---
%s
---

%s

TARGET COMPANY: %s

Please provide your analysis in the following JSON format:
{
  "resume_score": "Score out of 100 (e.g., 78/100)",
  "ats_score": "ATS compatibility score out of 100 (e.g., 85/100)",
  "grammar_mistakes": ["List of grammar/spelling errors found"],
  "formatting_suggestions": ["Specific formatting improvements needed"],
  "weak_sections": ["Identify weak or underdeveloped sections"],
  "missing_skills": ["Important skills missing for target role"],
  "projects_to_add": ["Project ideas that would strengthen resume"],
  "certifications": ["Recommended certifications to add"],
  "internship_suggestions": ["Internship experiences that would help"],
  "technical_improvements": ["Technical or content improvements"],
  "overall_feedback": "2-3 sentence summary with actionable next steps"
}

Be specific, constructive, and provide actionable recommendations. Focus on practical improvements.
""".formatted(
            resumeText,
            jobDescription != null && !jobDescription.isEmpty() 
                ? "JOB DESCRIPTION:\n---\n" + jobDescription + "\n---"
                : "",
            targetCompany != null && !targetCompany.isEmpty() ? targetCompany : "Not specified"
        );
    }
}
