import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AICodingMentorPage = () => {
  const [code, setCode] = useState('');
  const [language, setLanguage] = useState('Java');
  const [problemStatement, setProblemStatement] = useState('');
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const handleAnalyze = async () => {
    if (!code.trim()) {
      setError('Please enter your code');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.post(
        'http://localhost:8080/api/ai/coding/analyze',
        {
          code,
          language,
          problemStatement,
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
      setError(err.response?.data?.message || 'Error analyzing code');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>👨‍💻 AI Coding Mentor</h1>
        <p>Get expert feedback on your code with optimization suggestions</p>
      </div>

      <div className="ai-input-section">
        <div className="input-row">
          <div className="input-group">
            <label>Programming Language</label>
            <select
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
            >
              <option>Java</option>
              <option>Python</option>
              <option>C++</option>
              <option>JavaScript</option>
            </select>
          </div>
        </div>

        <div className="input-group">
          <label>Your Code</label>
          <textarea
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="Paste your code here..."
            rows={10}
            className="code-textarea"
          />
        </div>

        <div className="input-group">
          <label>Problem Statement (Optional)</label>
          <textarea
            value={problemStatement}
            onChange={(e) => setProblemStatement(e.target.value)}
            placeholder="Describe the problem your code solves..."
            rows={4}
          />
        </div>

        <button
          className="analyze-btn"
          onClick={handleAnalyze}
          disabled={loading}
        >
          {loading ? 'Analyzing...' : 'Analyze Code'}
        </button>

        {error && <div className="error-message">{error}</div>}
      </div>

      {response && (
        <div className="ai-results-section">
          <div className="feedback-card">
            <h3>📊 Overall Verdict</h3>
            <p>{response.overall_verdict}</p>
          </div>

          {response.bug_detection && response.bug_detection.length > 0 && (
            <div className="feedback-card warning">
              <h3>🐛 Bugs Detected</h3>
              <ul>
                {response.bug_detection.map((bug, i) => (
                  <li key={i}>{bug}</li>
                ))}
              </ul>
            </div>
          )}

          {response.syntax_issues && response.syntax_issues.length > 0 && (
            <div className="feedback-card warning">
              <h3>⚠️ Syntax Issues</h3>
              <ul>
                {response.syntax_issues.map((issue, i) => (
                  <li key={i}>{issue}</li>
                ))}
              </ul>
            </div>
          )}

          {response.logical_errors && response.logical_errors.length > 0 && (
            <div className="feedback-card warning">
              <h3>❌ Logical Errors</h3>
              <ul>
                {response.logical_errors.map((error, i) => (
                  <li key={i}>{error}</li>
                ))}
              </ul>
            </div>
          )}

          <div className="complexity-cards">
            <div className="complexity-card">
              <h4>⏱️ Time Complexity</h4>
              <p>{response.time_complexity}</p>
            </div>
            <div className="complexity-card">
              <h4>💾 Space Complexity</h4>
              <p>{response.space_complexity}</p>
            </div>
          </div>

          {response.optimized_solution && (
            <div className="feedback-card">
              <h3>✨ Optimized Solution</h3>
              <p>{response.optimized_solution}</p>
            </div>
          )}

          {response.clean_code_suggestions && response.clean_code_suggestions.length > 0 && (
            <div className="feedback-card">
              <h3>📝 Clean Code Suggestions</h3>
              <ul>
                {response.clean_code_suggestions.map((suggestion, i) => (
                  <li key={i}>{suggestion}</li>
                ))}
              </ul>
            </div>
          )}

          {response.interview_tips && response.interview_tips.length > 0 && (
            <div className="feedback-card">
              <h3>💡 Interview Tips</h3>
              <ul>
                {response.interview_tips.map((tip, i) => (
                  <li key={i}>{tip}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default AICodingMentorPage;
