package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class CareerPromptBuilder {

    public String buildCareerGuidancePrompt(String currentSkills, String currentSemester, 
                                            String targetCompany, String targetRole, Double currentCGPA) {
        return """
You are an expert career mentor with 20+ years of experience in tech industry placements and career development.

Based on the student's profile, provide comprehensive career guidance and personalized roadmap.

STUDENT PROFILE:
- Current Skills: %s
- Current Semester/Year: %s
- Target Company: %s
- Target Role: %s
- Current CGPA: %s

Please provide career guidance in the following JSON format:
{
  "suitable_job_roles": ["List of 5-7 job roles suited for this student"],
  "skill_gap": "Detailed analysis of what skills are missing to reach target role",
  "learning_roadmap": ["Step-by-step learning path (month-wise if possible)"],
  "recommended_courses": ["Specific online courses and platforms (Coursera, Udemy, etc.)"],
  "project_ideas": ["5-7 real-world projects to build"],
  "certification_suggestions": ["Relevant certifications to pursue"],
  "future_career_path": "Detailed description of career trajectory and salary growth",
  "timeline": "Realistic timeline to achieve target role with specific milestones"
}

Be specific, actionable, and realistic. Consider the student's current level and provide a progressive roadmap.
""".formatted(
            currentSkills != null ? currentSkills : "Not specified",
            currentSemester != null ? currentSemester : "Not specified",
            targetCompany != null ? targetCompany : "Not specified",
            targetRole != null ? targetRole : "Not specified",
            currentCGPA != null ? currentCGPA : "Not specified"
        );
    }

    public String buildPersonalizedRecommendationPrompt(String currentSkills, String targetRole, 
                                                        String experience, String interests) {
        return String.format(
            "You are a career guidance expert. Provide personalized recommendations.\n\n" +
            "PROFILE:\n" +
            "- Current Skills: %s\n" +
            "- Target Role: %s\n" +
            "- Experience Level: %s\n" +
            "- Interests: %s\n\n" +
            "Provide recommendations in JSON format:\n" +
            "{\n" +
            "  \"personalized_roles\": [\"<Role 1>\", \"<Role 2>\"],\n" +
            "  \"unique_strengths\": [\"<Strength 1>\", \"<Strength 2>\"],\n" +
            "  \"differentiation_strategies\": [\"<Strategy 1>\", \"<Strategy 2>\"],\n" +
            "  \"career_opportunities\": [\"<Opportunity 1>\", \"<Opportunity 2>\"],\n" +
            "  \"personalization_note\": \"<Note about candidate fit>\"\n" +
            "}\n",
            currentSkills, targetRole, experience, interests
        );
    }

    public String buildIndustrySpecificGuidancePrompt(String targetRole, String industry, String level) {
        return String.format(
            "You are an industry expert for %s industry.\n\n" +
            "REQUIREMENTS:\n" +
            "- Target Role: %s\n" +
            "- Experience Level: %s\n" +
            "- Industry: %s\n\n" +
            "Provide industry-specific guidance in JSON format:\n" +
            "{\n" +
            "  \"industry_trends\": [\"<Trend 1>\", \"<Trend 2>\"],\n" +
            "  \"required_skills\": [\"<Skill 1>\", \"<Skill 2>\"],\n" +
            "  \"industry_certifications\": [\"<Certification 1>\", \"<Certification 2>\"],\n" +
            "  \"market_insights\": \"<Current market insights>\",\n" +
            "  \"career_growth_prospects\": \"<Growth prospects in this field>\"\n" +
            "}\n",
            industry, targetRole, level, industry
        );
    }

    public String buildLearningRoadmapPrompt(String targetRole, String currentLevel, String timeframe) {
        return String.format(
            "You are a learning pathway expert. Create a detailed learning roadmap.\n\n" +
            "REQUIREMENTS:\n" +
            "- Target Role: %s\n" +
            "- Current Level: %s\n" +
            "- Timeframe: %s\n\n" +
            "Provide roadmap in JSON format:\n" +
            "{\n" +
            "  \"phase_1_foundation\": {\"duration\": \"<weeks>\", \"topics\": [\"<Topic 1>\"], \"milestones\": [\"<Milestone 1>\"]},\n" +
            "  \"phase_2_intermediate\": {\"duration\": \"<weeks>\", \"topics\": [\"<Topic 1>\"], \"milestones\": [\"<Milestone 1>\"]},\n" +
            "  \"phase_3_advanced\": {\"duration\": \"<weeks>\", \"topics\": [\"<Topic 1>\"], \"milestones\": [\"<Milestone 1>\"]},\n" +
            "  \"total_duration\": \"<total timeframe>\",\n" +
            "  \"success_metrics\": [\"<Metric 1>\", \"<Metric 2>\"]\n" +
            "}\n",
            targetRole, currentLevel, timeframe
        );
    }

    public String buildCertificationRecommendationPrompt(String targetRole, String budget, String timeframe) {
        return String.format(
            "You are a certification expert. Recommend relevant certifications.\n\n" +
            "REQUIREMENTS:\n" +
            "- Target Role: %s\n" +
            "- Budget: %s\n" +
            "- Timeframe: %s\n\n" +
            "Provide recommendations in JSON format:\n" +
            "{\n" +
            "  \"top_certifications\": [{\"name\": \"<Name>\", \"provider\": \"<Provider>\", \"cost\": \"<Cost>\", \"duration\": \"<Duration>\", \"value\": \"<Career Value>\"}],\n" +
            "  \"budget_friendly_options\": [\"<Option 1>\", \"<Option 2>\"],\n" +
            "  \"premium_certifications\": [\"<Premium Cert 1>\", \"<Premium Cert 2>\"],\n" +
            "  \"recommended_order\": [\"<Cert 1>\", \"<Cert 2>\"],\n" +
            "  \"estimated_total_cost\": \"<Total cost estimate>\"\n" +
            "}\n",
            targetRole, budget, timeframe
        );
    }
}
