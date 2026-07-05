import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AIResumeAnalyzerPage = () => {
  const [resumeText, setResumeText] = useState('');
  const [jobDescription, setJobDescription] = useState('');
  const [targetCompany, setTargetCompany] = useState('');
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const handleAnalyze = async () => {
    if (!resumeText.trim()) {
      setError('Please enter your resume text');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.post(
        'http://localhost:8080/api/ai/resume/analyze',
        {
          resumeText,
          jobDescription,
          targetCompany,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setResponse(res.data.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Error analyzing resume');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>🔍 AI Resume Analyzer</h1>
        <p>Get instant feedback on your resume with ATS optimization</p>
      </div>

      <div className="ai-input-section">
        <div className="input-group">
          <label>Your Resume</label>
          <textarea
            value={resumeText}
            onChange={(e) => setResumeText(e.target.value)}
            placeholder="Paste your resume content here..."
            rows={8}
          />
        </div>

        <div className="input-row">
          <div className="input-group">
            <label>Job Description (Optional)</label>
            <textarea
              value={jobDescription}
              onChange={(e) => setJobDescription(e.target.value)}
              placeholder="Paste the job description..."
              rows={4}
            />
          </div>

          <div className="input-group">
            <label>Target Company (Optional)</label>
            <input
              type="text"
              value={targetCompany}
              onChange={(e) => setTargetCompany(e.target.value)}
              placeholder="e.g., Google, Microsoft, Amazon"
            />
          </div>
        </div>

        <button
          className="analyze-btn"
          onClick={handleAnalyze}
          disabled={loading}
        >
          {loading ? 'Analyzing...' : 'Analyze Resume'}
        </button>

        {error && <div className="error-message">{error}</div>}
      </div>

      {response && (
        <div className="ai-results-section">
          <div className="scores-container">
            <div className="score-card">
              <h3>Resume Score</h3>
              <p className="score">{response.resume_score}</p>
            </div>
            <div className="score-card">
              <h3>ATS Score</h3>
              <p className="score">{response.ats_score}</p>
            </div>
          </div>

          <div className="feedback-card">
            <h3>Overall Feedback</h3>
            <p>{response.overall_feedback}</p>
          </div>

          {response.grammar_mistakes && response.grammar_mistakes.length > 0 && (
            <div className="feedback-card warning">
              <h3>🔴 Grammar Mistakes</h3>
              <ul>
                {response.grammar_mistakes.map((item, i) => (
                  <li key={i}>{item}</li>
                ))}
              </ul>
            </div>
          )}

          {response.weak_sections && response.weak_sections.length > 0 && (
            <div className="feedback-card warning">
              <h3>⚠️ Weak Sections</h3>
              <ul>
                {response.weak_sections.map((item, i) => (
                  <li key={i}>{item}</li>
                ))}
              </ul>
            </div>
          )}

          {response.missing_skills && response.missing_skills.length > 0 && (
            <div className="feedback-card">
              <h3>📚 Missing Skills</h3>
              <ul>
                {response.missing_skills.map((skill, i) => (
                  <li key={i}>{skill}</li>
                ))}
              </ul>
            </div>
          )}

          {response.formatting_suggestions && response.formatting_suggestions.length > 0 && (
            <div className="feedback-card">
              <h3>✏️ Formatting Suggestions</h3>
              <ul>
                {response.formatting_suggestions.map((suggestion, i) => (
                  <li key={i}>{suggestion}</li>
                ))}
              </ul>
            </div>
          )}

          {response.projects_to_add && response.projects_to_add.length > 0 && (
            <div className="feedback-card">
              <h3>💡 Projects to Add</h3>
              <ul>
                {response.projects_to_add.map((project, i) => (
                  <li key={i}>{project}</li>
                ))}
              </ul>
            </div>
          )}

          {response.certifications && response.certifications.length > 0 && (
            <div className="feedback-card">
              <h3>🎓 Recommended Certifications</h3>
              <ul>
                {response.certifications.map((cert, i) => (
                  <li key={i}>{cert}</li>
                ))}
              </ul>
            </div>
          )}

          {response.technical_improvements && response.technical_improvements.length > 0 && (
            <div className="feedback-card">
              <h3>🔧 Technical Improvements</h3>
              <ul>
                {response.technical_improvements.map((improvement, i) => (
                  <li key={i}>{improvement}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default AIResumeAnalyzerPage;
