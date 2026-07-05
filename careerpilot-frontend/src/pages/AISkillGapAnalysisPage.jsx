import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AISkillGapAnalysisPage = () => {
  const [formData, setFormData] = useState({
    currentSkills: '',
    targetJobRole: '',
    yearsOfExperience: 0,
  });
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const token = localStorage.getItem('careerpilot_token');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleAnalyze = async () => {
    if (!formData.currentSkills.trim() || !formData.targetJobRole.trim()) {
      setError('Please enter both current skills and target job role');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/skill-gap/analyze',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setResponse(res.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Error analyzing skill gap');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>🎯 Skill Gap Analysis</h1>
        <p>Analyze the gap between your current skills and your target job role</p>
      </div>

      <div className="ai-input-section">
        <div className="input-group full-width">
          <label>Current Skills *</label>
          <textarea
            name="currentSkills"
            value={formData.currentSkills}
            onChange={handleChange}
            placeholder="Enter your current skills (e.g., Java, Spring Boot, React, SQL, AWS)"
            rows="4"
          />
        </div>

        <div className="input-row">
          <div className="input-group">
            <label>Target Job Role *</label>
            <input
              type="text"
              name="targetJobRole"
              value={formData.targetJobRole}
              onChange={handleChange}
              placeholder="e.g., Full Stack Developer, Data Engineer"
            />
          </div>

          <div className="input-group">
            <label>Years of Experience</label>
            <input
              type="number"
              name="yearsOfExperience"
              value={formData.yearsOfExperience}
              onChange={handleChange}
              min="0"
              max="50"
            />
          </div>
        </div>

        <button
          className="analyze-btn"
          onClick={handleAnalyze}
          disabled={loading}
        >
          {loading ? 'Analyzing Skills...' : 'Analyze Skill Gap'}
        </button>

        {error && <div className="error-message">{error}</div>}
      </div>

      {response && (
        <div className="ai-results-section">
          {response.matching_skills && response.matching_skills.length > 0 && (
            <div className="feedback-card strengths">
              <h3>✅ Matching Skills</h3>
              <p className="card-description">Skills you already have that align with the target role:</p>
              <ul>
                {response.matching_skills.map((skill, i) => (
                  <li key={i}>{skill}</li>
                ))}
              </ul>
            </div>
          )}

          {response.missing_skills && response.missing_skills.length > 0 && (
            <div className="feedback-card improvements">
              <h3>❌ Missing Skills</h3>
              <p className="card-description">Skills you need to acquire for the target role:</p>
              <ul>
                {response.missing_skills.map((skill, i) => (
                  <li key={i}>{skill}</li>
                ))}
              </ul>
            </div>
          )}

          {response.high_priority_skills && response.high_priority_skills.length > 0 && (
            <div className="feedback-card recommendations">
              <h3>🔴 High-Priority Skills</h3>
              <p className="card-description">Critical skills to focus on first:</p>
              <ul>
                {response.high_priority_skills.map((skill, i) => (
                  <li key={i}>{skill}</li>
                ))}
              </ul>
            </div>
          )}

          {response.learning_order && response.learning_order.length > 0 && (
            <div className="feedback-card">
              <h3>📚 Recommended Learning Order</h3>
              <p className="card-description">Learn these skills in this sequence for optimal progression:</p>
              <ol>
                {response.learning_order.map((skill, i) => (
                  <li key={i}>{skill}</li>
                ))}
              </ol>
            </div>
          )}

          {response.improvement_suggestions && response.improvement_suggestions.length > 0 && (
            <div className="feedback-card">
              <h3>💡 Improvement Suggestions</h3>
              <ul>
                {response.improvement_suggestions.map((suggestion, i) => (
                  <li key={i}>{suggestion}</li>
                ))}
              </ul>
            </div>
          )}

          {response.estimated_timeline && (
            <div className="feedback-card">
              <h3>⏱️ Estimated Timeline</h3>
              <p>{response.estimated_timeline}</p>
            </div>
          )}

          {response.resources && (
            <div className="feedback-card">
              <h3>🔗 Recommended Resources</h3>
              <p>{response.resources}</p>
            </div>
          )}

          <button
            className="analyze-btn secondary"
            onClick={() => {
              setResponse(null);
              setFormData({ currentSkills: '', targetJobRole: '', yearsOfExperience: 0 });
            }}
          >
            Analyze Another Role
          </button>
        </div>
      )}
    </div>
  );
};

export default AISkillGapAnalysisPage;
