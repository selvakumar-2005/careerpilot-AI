import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AIMockInterviewPage = () => {
  const [stage, setStage] = useState('setup'); // setup, interviewing, reviewing
  const [formData, setFormData] = useState({
    jobRole: '',
    experienceLevel: 'Mid-Level',
    company: '',
    technology: '',
    totalQuestions: 5,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [currentQuestion, setCurrentQuestion] = useState(null);
  const [userAnswer, setUserAnswer] = useState('');
  const [answers, setAnswers] = useState([]);
  const [evaluations, setEvaluations] = useState([]);
  const [currentQuestionNumber, setCurrentQuestionNumber] = useState(1);
  const [summary, setSummary] = useState(null);

  const token = localStorage.getItem('careerpilot_token');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const startInterview = async () => {
    if (!formData.jobRole.trim()) {
      setError('Please enter a job role');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/mock-interview/generate-question',
        { ...formData, questionNumber: 1, totalQuestions: formData.totalQuestions },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setCurrentQuestion(res.data);
      setCurrentQuestionNumber(1);
      setStage('interviewing');
    } catch (err) {
      setError(err.response?.data || 'Error starting interview');
    } finally {
      setLoading(false);
    }
  };

  const submitAnswer = async () => {
    if (!userAnswer.trim()) {
      setError('Please provide an answer');
      return;
    }

    setLoading(true);
    setError('');

    try {
      // Evaluate the answer
      const evalRes = await axios.post(
        'http://localhost:8080/api/ai/mock-interview/evaluate-answer',
        {
          jobRole: formData.jobRole,
          experienceLevel: formData.experienceLevel,
          technology: formData.technology,
          answer: userAnswer,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      const evaluation = evalRes.data;
      setEvaluations((prev) => [...prev, evaluation]);
      setAnswers((prev) => [...prev, { question: currentQuestion.question, answer: userAnswer }]);

      // Check if more questions
      if (currentQuestionNumber < formData.totalQuestions) {
        // Get next question
        const nextRes = await axios.post(
          'http://localhost:8080/api/ai/mock-interview/generate-question',
          {
            ...formData,
            questionNumber: currentQuestionNumber + 1,
            totalQuestions: formData.totalQuestions,
          },
          {
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          }
        );

        setCurrentQuestion(nextRes.data);
        setCurrentQuestionNumber((prev) => prev + 1);
        setUserAnswer('');
      } else {
        // Interview complete - get summary
        getSummary();
      }
    } catch (err) {
      setError(err.response?.data || 'Error evaluating answer');
    } finally {
      setLoading(false);
    }
  };

  const getSummary = async () => {
    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/mock-interview/summary',
        { jobRole: formData.jobRole, experienceLevel: formData.experienceLevel },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setSummary(res.data);
      setStage('reviewing');
    } catch (err) {
      setError(err.response?.data || 'Error getting summary');
    }
  };

  const restartInterview = () => {
    setStage('setup');
    setUserAnswer('');
    setAnswers([]);
    setEvaluations([]);
    setCurrentQuestion(null);
    setSummary(null);
    setCurrentQuestionNumber(1);
  };

  if (stage === 'setup') {
    return (
      <div className="ai-page-container">
        <div className="ai-header">
          <h1>🎤 Mock Interview Practice</h1>
          <p>Practice with AI-generated interview questions and get instant feedback</p>
        </div>

        <div className="ai-input-section">
          <div className="input-row">
            <div className="input-group">
              <label>Job Role *</label>
              <input
                type="text"
                name="jobRole"
                value={formData.jobRole}
                onChange={handleChange}
                placeholder="e.g., Software Engineer, Data Scientist"
              />
            </div>

            <div className="input-group">
              <label>Experience Level</label>
              <select name="experienceLevel" value={formData.experienceLevel} onChange={handleChange}>
                <option>Entry-Level</option>
                <option>Mid-Level</option>
                <option>Senior</option>
              </select>
            </div>
          </div>

          <div className="input-row">
            <div className="input-group">
              <label>Company (Optional)</label>
              <input
                type="text"
                name="company"
                value={formData.company}
                onChange={handleChange}
                placeholder="e.g., Google, Microsoft"
              />
            </div>

            <div className="input-group">
              <label>Technology (Optional)</label>
              <input
                type="text"
                name="technology"
                value={formData.technology}
                onChange={handleChange}
                placeholder="e.g., React, Python, Full Stack"
              />
            </div>
          </div>

          <div className="input-row">
            <div className="input-group">
              <label>Number of Questions</label>
              <select name="totalQuestions" value={formData.totalQuestions} onChange={handleChange}>
                <option value={3}>3 Questions</option>
                <option value={5}>5 Questions</option>
                <option value={10}>10 Questions</option>
              </select>
            </div>
          </div>

          <button className="analyze-btn" onClick={startInterview} disabled={loading}>
            {loading ? 'Starting Interview...' : 'Start Mock Interview'}
          </button>

          {error && <div className="error-message">{error}</div>}
        </div>
      </div>
    );
  }

  if (stage === 'interviewing') {
    return (
      <div className="ai-page-container">
        <div className="ai-header">
          <h1>🎤 Mock Interview</h1>
          <p>Question {currentQuestionNumber} of {formData.totalQuestions}</p>
        </div>

        <div className="interview-section">
          <div className="question-display">
            <h3>Interview Question</h3>
            <div className="question-box">
              <p>{currentQuestion?.question}</p>
            </div>
          </div>

          <div className="answer-input">
            <label>Your Answer</label>
            <textarea
              value={userAnswer}
              onChange={(e) => setUserAnswer(e.target.value)}
              placeholder="Type your answer here..."
              rows="6"
            />
          </div>

          <button className="analyze-btn" onClick={submitAnswer} disabled={loading}>
            {loading ? 'Evaluating...' : currentQuestionNumber === formData.totalQuestions ? 'Submit & Complete' : 'Submit & Next Question'}
          </button>

          {error && <div className="error-message">{error}</div>}

          {evaluations.length > 0 && (
            <div className="previous-evaluations">
              <h4>Previous Evaluations</h4>
              {evaluations.map((evaluation, i) => (
                <div key={i} className="evaluation-summary">
                  <p><strong>Q{i + 1} Score: {evaluation.score}/100</strong></p>
                  <p className="small-text">{answers[i]?.question}</p>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    );
  }

  if (stage === 'reviewing') {
    return (
      <div className="ai-page-container">
        <div className="ai-header">
          <h1>📊 Interview Summary</h1>
          <p>Here's your performance analysis</p>
        </div>

        <div className="ai-results-section">
          {summary?.overall_performance && (
            <div className="feedback-card">
              <h3>📈 Overall Performance: {summary.overall_performance}</h3>
              <p><strong>Average Score: {summary.average_score?.toFixed(1)}/100</strong></p>
            </div>
          )}

          {summary?.key_strengths && summary.key_strengths.length > 0 && (
            <div className="feedback-card strengths">
              <h3>✨ Key Strengths</h3>
              <ul>
                {summary.key_strengths.map((s, i) => (
                  <li key={i}>{s}</li>
                ))}
              </ul>
            </div>
          )}

          {summary?.areas_to_improve && summary.areas_to_improve.length > 0 && (
            <div className="feedback-card improvements">
              <h3>🎯 Areas to Improve</h3>
              <ul>
                {summary.areas_to_improve.map((a, i) => (
                  <li key={i}>{a}</li>
                ))}
              </ul>
            </div>
          )}

          {summary?.preparation_recommendations && summary.preparation_recommendations.length > 0 && (
            <div className="feedback-card recommendations">
              <h3>💡 Preparation Recommendations</h3>
              <ul>
                {summary.preparation_recommendations.map((r, i) => (
                  <li key={i}>{r}</li>
                ))}
              </ul>
            </div>
          )}

          {summary?.next_steps && (
            <div className="feedback-card">
              <h3>📋 Next Steps</h3>
              <p>{summary.next_steps}</p>
            </div>
          )}

          <div className="detailed-feedback">
            <h3>📝 Detailed Feedback by Question</h3>
            {evaluations.map((evaluation, i) => (
              <div key={i} className="question-feedback">
                <h4>Question {i + 1}: Score {evaluation.score}/100</h4>
                <p><strong>Your Answer:</strong> {answers[i]?.answer}</p>
                
                {evaluation.strengths && evaluation.strengths.length > 0 && (
                  <div>
                    <strong>✓ Strengths:</strong>
                    <ul className="feedback-list">
                      {evaluation.strengths.map((s, j) => (
                        <li key={j}>{s}</li>
                      ))}
                    </ul>
                  </div>
                )}

                {evaluation.weaknesses && evaluation.weaknesses.length > 0 && (
                  <div>
                    <strong>✗ Weaknesses:</strong>
                    <ul className="feedback-list">
                      {evaluation.weaknesses.map((w, j) => (
                        <li key={j}>{w}</li>
                      ))}
                    </ul>
                  </div>
                )}

                {evaluation.improvement_suggestions && evaluation.improvement_suggestions.length > 0 && (
                  <div>
                    <strong>💡 Suggestions:</strong>
                    <ul className="feedback-list">
                      {evaluation.improvement_suggestions.map((s, j) => (
                        <li key={j}>{s}</li>
                      ))}
                    </ul>
                  </div>
                )}

                {evaluation.feedback && (
                  <div>
                    <strong>📌 Feedback:</strong>
                    <p>{evaluation.feedback}</p>
                  </div>
                )}
              </div>
            ))}
          </div>

          <button className="analyze-btn" onClick={restartInterview}>
            Start Another Mock Interview
          </button>
        </div>
      </div>
    );
  }
};

export default AIMockInterviewPage;
