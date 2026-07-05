package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class CompanyPromptBuilder {

    public String buildCompanyPreparationPrompt(String company, String role) {
        return """
You are a hiring manager at %s with 15+ years of recruiting and interview experience.

Provide a comprehensive preparation guide for candidates applying to %s for the %s role.

Please provide company preparation details in the following JSON format:
{
  "company_overview": "Brief overview of the company, culture, and tech stack",
  "hiring_process": ["Step-by-step hiring process with timeline (e.g., Online Test → Technical Round 1 → HR Round)"],
  "technical_topics": ["Core technical topics to study for this role"],
  "coding_topics": ["Specific coding/DSA topics based on company preference"],
  "interview_questions": ["10 typical technical questions asked by this company"],
  "hr_questions": ["5-7 common HR questions asked"],
  "preparation_strategy": "Detailed month-by-month preparation strategy",
  "expected_difficulty": "Difficulty level assessment (Easy/Medium/Hard)",
  "resources": ["Recommended books, websites, and resources specific to this company"]
}

Be specific to %s and provide realistic expectations based on actual interview processes.
""".formatted(
            company != null ? company : "Tech Company",
            company != null ? company : "Tech Company",
            role != null ? role : "Software Engineer",
            company != null ? company : "Target Company"
        );
    }
}
