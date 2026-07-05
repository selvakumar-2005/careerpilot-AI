import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AICompanyPreparationPage = () => {
  const [formData, setFormData] = useState({
    company: '',
    role: '',
  });
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleGetPreparation = async () => {
    if (!formData.company.trim()) {
      setError('Please enter a company name');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.post(
        'http://localhost:8080/api/ai/company/preparation',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setResponse(res.data.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting company preparation');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>🏢 AI Company Preparation</h1>
        <p>Get detailed company-specific preparation guide</p>
      </div>

      <div className="ai-input-section">
        <div className="input-row">
          <div className="input-group">
            <label>Company Name *</label>
            <input
              type="text"
              name="company"
              value={formData.company}
              onChange={handleChange}
              placeholder="e.g., Google, Microsoft, Amazon"
            />
          </div>

          <div className="input-group">
            <label>Target Role</label>
            <input
              type="text"
              name="role"
              value={formData.role}
              onChange={handleChange}
              placeholder="e.g., Software Engineer, Full Stack Developer"
            />
          </div>
        </div>

        <button
          className="analyze-btn"
          onClick={handleGetPreparation}
          disabled={loading}
        >
          {loading ? 'Generating Guide...' : 'Get Preparation Guide'}
        </button>

        {error && <div className="error-message">{error}</div>}
      </div>

      {response && (
        <div className="ai-results-section">
          {response.company_overview && (
            <div className="feedback-card">
              <h3>🏢 Company Overview</h3>
              <p>{response.company_overview}</p>
            </div>
          )}

          {response.expected_difficulty && (
            <div className="difficulty-card">
              <h4>📊 Expected Difficulty</h4>
              <p>{response.expected_difficulty}</p>
            </div>
          )}

          {response.hiring_process && response.hiring_process.length > 0 && (
            <div className="feedback-card">
              <h3>📋 Hiring Process</h3>
              <ol>
                {response.hiring_process.map((step, i) => (
                  <li key={i}>{step}</li>
                ))}
              </ol>
            </div>
          )}

          {response.preparation_strategy && (
            <div className="feedback-card">
              <h3>🗺️ Preparation Strategy</h3>
              <p>{response.preparation_strategy}</p>
            </div>
          )}

          {response.technical_topics && response.technical_topics.length > 0 && (
            <div className="feedback-card">
              <h3>📚 Technical Topics</h3>
              <ul>
                {response.technical_topics.map((topic, i) => (
                  <li key={i}>{topic}</li>
                ))}
              </ul>
            </div>
          )}

          {response.coding_topics && response.coding_topics.length > 0 && (
            <div className="feedback-card">
              <h3>💻 Coding Topics</h3>
              <ul>
                {response.coding_topics.map((topic, i) => (
                  <li key={i}>{topic}</li>
                ))}
              </ul>
            </div>
          )}

          {response.interview_questions && response.interview_questions.length > 0 && (
            <div className="feedback-card">
              <h3>🎤 Typical Interview Questions</h3>
              <ul>
                {response.interview_questions.map((q, i) => (
                  <li key={i}>{q}</li>
                ))}
              </ul>
            </div>
          )}

          {response.hr_questions && response.hr_questions.length > 0 && (
            <div className="feedback-card">
              <h3>👥 HR Questions</h3>
              <ul>
                {response.hr_questions.map((q, i) => (
                  <li key={i}>{q}</li>
                ))}
              </ul>
            </div>
          )}

          {response.resources && response.resources.length > 0 && (
            <div className="feedback-card">
              <h3>📖 Recommended Resources</h3>
              <ul>
                {response.resources.map((resource, i) => (
                  <li key={i}>{resource}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default AICompanyPreparationPage;
