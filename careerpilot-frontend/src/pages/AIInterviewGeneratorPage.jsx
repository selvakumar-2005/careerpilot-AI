import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AIInterviewGeneratorPage = () => {
  const [formData, setFormData] = useState({
    company: '',
    technology: '',
    difficulty: 'Medium',
    experienceLevel: 'Fresher',
  });
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');
  const [expandedQuestion, setExpandedQuestion] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleGenerate = async () => {
    if (!formData.company.trim()) {
      setError('Please enter a company name');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.post(
        'http://localhost:8080/api/ai/interview/generate',
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
      setError(err.response?.data?.message || 'Error generating interview questions');
    } finally {
      setLoading(false);
    }
  };

  const QuestionCard = ({ question, index }) => (
    <div className="question-card">
      <div
        className="question-header"
        onClick={() => setExpandedQuestion(expandedQuestion === index ? null : index)}
      >
        <h4>Q{index + 1}: {question.question}</h4>
        <span className="expand-icon">{expandedQuestion === index ? '▼' : '▶'}</span>
      </div>
      {expandedQuestion === index && (
        <div className="question-content">
          <div className="answer-section">
            <h5>💡 Model Answer:</h5>
            <p>{question.model_answer}</p>
          </div>
          {question.follow_up_questions && question.follow_up_questions.length > 0 && (
            <div className="followup-section">
              <h5>🔗 Follow-up Questions:</h5>
              <ul>
                {question.follow_up_questions.map((followup, i) => (
                  <li key={i}>{followup}</li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>🎤 AI Interview Generator</h1>
        <p>Generate realistic interview questions tailored to your target company</p>
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
            <label>Technology</label>
            <input
              type="text"
              name="technology"
              value={formData.technology}
              onChange={handleChange}
              placeholder="e.g., Java, Python, Full Stack"
            />
          </div>
        </div>

        <div className="input-row">
          <div className="input-group">
            <label>Difficulty Level</label>
            <select name="difficulty" value={formData.difficulty} onChange={handleChange}>
              <option>Easy</option>
              <option>Medium</option>
              <option>Hard</option>
            </select>
          </div>

          <div className="input-group">
            <label>Experience Level</label>
            <select name="experienceLevel" value={formData.experienceLevel} onChange={handleChange}>
              <option>Fresher</option>
              <option>1 Year</option>
              <option>2 Years</option>
              <option>3+ Years</option>
            </select>
          </div>
        </div>

        <button
          className="analyze-btn"
          onClick={handleGenerate}
          disabled={loading}
        >
          {loading ? 'Generating Questions...' : 'Generate Interview Questions'}
        </button>

        {error && <div className="error-message">{error}</div>}
      </div>

      {response && (
        <div className="ai-results-section">
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

          {response.confidence_tips && response.confidence_tips.length > 0 && (
            <div className="feedback-card">
              <h3>🌟 Confidence Tips</h3>
              <ul>
                {response.confidence_tips.map((tip, i) => (
                  <li key={i}>{tip}</li>
                ))}
              </ul>
            </div>
          )}

          {response.technical_questions && response.technical_questions.length > 0 && (
            <div className="questions-section">
              <h3>👨‍💻 Technical Questions</h3>
              {response.technical_questions.map((q, i) => (
                <QuestionCard key={i} question={q} index={i} />
              ))}
            </div>
          )}

          {response.hr_questions && response.hr_questions.length > 0 && (
            <div className="questions-section">
              <h3>👥 HR Questions</h3>
              {response.hr_questions.map((q, i) => (
                <QuestionCard
                  key={i}
                  question={q}
                  index={`hr-${i}`}
                />
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default AIInterviewGeneratorPage;
