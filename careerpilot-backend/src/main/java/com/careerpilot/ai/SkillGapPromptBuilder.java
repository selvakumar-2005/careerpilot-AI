package com.careerpilot.ai;

import org.springframework.stereotype.Component;

@Component
public class SkillGapPromptBuilder {

    public String buildSkillGapAnalysisPrompt(String currentSkills, String targetRole, String industry, String experienceLevel, String yearsOfExperience) {
        return String.format(
            "You are a career development expert analyzing skill gaps.\n\n" +
            "CURRENT PROFILE:\n" +
            "- Current Skills: %s\n" +
            "- Experience Level: %s\n" +
            "- Years of Experience: %s\n\n" +
            "TARGET PROFILE:\n" +
            "- Target Role: %s\n" +
            "- Industry: %s\n\n" +
            "ANALYSIS REQUIREMENTS:\n" +
            "1. Identify skills that match the target role from current skills\n" +
            "2. Identify missing skills required for the target role\n" +
            "3. Identify high-priority skills to learn first\n" +
            "4. Suggest learning order (prerequisites first)\n" +
            "5. Provide specific improvement suggestions\n" +
            "6. Estimate timeline to bridge the gap\n" +
            "7. Recommend learning resources\n\n" +
            "Respond in JSON format:\n" +
            "{\n" +
            "  \"matching_skills\": [\"<Skill 1>\", \"<Skill 2>\"],\n" +
            "  \"missing_skills\": [\"<Missing Skill 1>\", \"<Missing Skill 2>\"],\n" +
            "  \"high_priority_skills\": [\"<Priority Skill 1>\", \"<Priority Skill 2>\"],\n" +
            "  \"learning_order\": [\"<Learn First>\", \"<Learn Second>\"],\n" +
            "  \"improvement_suggestions\": [\"<Suggestion 1>\", \"<Suggestion 2>\"],\n" +
            "  \"timeline_estimate\": \"<Estimated time to bridge gap>\",\n" +
            "  \"resources_recommended\": [\"<Resource 1>\", \"<Resource 2>\"],\n" +
            "  \"skill_gap_summary\": \"<Overall summary of the skill gap>\"\n" +
            "}\n",
            currentSkills, experienceLevel, yearsOfExperience, targetRole, industry
        );
    }
}
