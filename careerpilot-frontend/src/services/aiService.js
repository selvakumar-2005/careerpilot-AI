import axiosInstance from './axiosInstance';

/**
 * AI service — thin wrappers around the /api/ai/* endpoints.
 * Every module that needs Gemini calls one of these functions.
 */
const aiService = {
  /**
   * Free-text prompt test.
   * POST /api/ai/test
   * @param {string} prompt - raw prompt string
   */
  test: (prompt) =>
    axiosInstance.post('/api/ai/test', { prompt }),

  /**
   * Template-based prompt.
   * POST /api/ai/prompt
   * @param {string} templateKey  - e.g. "RESUME_ANALYSIS"
   * @param {string} userContent  - content injected into the template
   * @param {string} extraParams  - optional extra context (can be null)
   */
  sendTemplatePrompt: (templateKey, userContent, extraParams = null) =>
    axiosInstance.post('/api/ai/prompt', { templateKey, userContent, extraParams }),
};

export default aiService;
