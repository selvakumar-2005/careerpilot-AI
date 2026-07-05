import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import codingService from '../services/codingService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import { DIFFICULTY_COLORS, LANGUAGES } from '../utils/constants';
import '../styles/coding.css';

function CodingDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [question, setQuestion]   = useState(null);
  const [loading, setLoading]     = useState(true);
  const [code, setCode]           = useState('// Write your solution here\n');
  const [language, setLanguage]   = useState('Java');
  const [submitting, setSubmitting] = useState(false);
  const [lastSubmission, setLastSub] = useState(null);
  const [subHistory, setSubHistory] = useState([]);

  useEffect(() => {
    setLoading(true);
    Promise.all([
      codingService.getQuestionById(id),
      codingService.getSubmissionsByQuestion(id),
    ])
      .then(([qRes, sRes]) => {
        setQuestion(qRes.data.data);
        setSubHistory(sRes.data.data || []);
        if (sRes.data.data?.length > 0) setLastSub(sRes.data.data[0]);
      })
      .catch(() => toast.error('Failed to load question'))
      .finally(() => setLoading(false));
  }, [id]);

  const handleSubmit = async () => {
    if (!code.trim()) {
      toast.error('Code cannot be empty');
      return;
    }
    setSubmitting(true);
    try {
      const res = await codingService.submit({
        questionId:    parseInt(id),
        submittedCode: code,
        language,
      });
      const sub = res.data.data;
      setLastSub(sub);
      setSubHistory((prev) => [sub, ...prev]);
      toast.success(`Submitted! Attempt #${sub.attempts}`);
    } catch (err) {
      toast.error(err.response?.data?.message || 'Submission failed');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <div className="center-spinner"><LoadingSpinner size="lg" /></div>;
  if (!question) return <div className="empty-state">Question not found.</div>;

  const dc = DIFFICULTY_COLORS[question.difficulty] || {};

  return (
    <div className="coding-detail">

      {/* Back link */}
      <button className="back-btn" onClick={() => navigate('/coding')}>
        ← Back to Questions
      </button>

      <div className="detail-layout">

        {/* Left — Question panel */}
        <div className="question-panel">
          <div className="question-panel__header">
            <h1 className="question-panel__title">{question.title}</h1>
            <span className="diff-badge"
              style={{ background: dc.bg, color: dc.text }}>
              {question.difficulty}
            </span>
            <span className="topic-tag">{question.topic}</span>
          </div>

          <div className="question-section">
            <h3>Problem Statement</h3>
            <p className="question-text">{question.description}</p>
          </div>

          {question.inputFormat && (
            <div className="question-section">
              <h3>Input Format</h3>
              <p className="question-text">{question.inputFormat}</p>
            </div>
          )}

          {question.outputFormat && (
            <div className="question-section">
              <h3>Output Format</h3>
              <p className="question-text">{question.outputFormat}</p>
            </div>
          )}

          {question.constraints && (
            <div className="question-section">
              <h3>Constraints</h3>
              <pre className="code-block">{question.constraints}</pre>
            </div>
          )}

          <div className="question-section">
            <h3>Sample Input</h3>
            <pre className="code-block">{question.sampleInput}</pre>
            <h3 style={{ marginTop: '12px' }}>Sample Output</h3>
            <pre className="code-block">{question.sampleOutput}</pre>
          </div>

          {/* Submission history */}
          {subHistory.length > 0 && (
            <div className="question-section">
              <h3>Your Submissions ({subHistory.length})</h3>
              <div className="sub-history">
                {subHistory.slice(0, 5).map((s) => (
                  <div key={s.id} className="sub-history-item">
                    <span className={`sub-status sub-status--${s.status?.toLowerCase()}`}>
                      {s.status}
                    </span>
                    <span className="sub-lang">{s.language}</span>
                    <span className="sub-attempt">Attempt #{s.attempts}</span>
                    <span className="sub-date">
                      {s.submittedAt ? new Date(s.submittedAt).toLocaleString() : '—'}
                    </span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>

        {/* Right — Editor panel */}
        <div className="editor-panel">
          <div className="editor-panel__toolbar">
            <select
              className="lang-select"
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
              aria-label="Select language"
            >
              {LANGUAGES.map((l) => (
                <option key={l} value={l}>{l}</option>
              ))}
            </select>
            <button
              className="btn btn--primary btn--md"
              onClick={handleSubmit}
              disabled={submitting}
            >
              {submitting ? <LoadingSpinner size="sm" color="#fff" /> : '▶ Submit'}
            </button>
          </div>

          <textarea
            className="code-editor"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            spellCheck="false"
            aria-label="Code editor"
          />

          {lastSubmission && (
            <div className={`submission-result submission-result--${lastSubmission.status?.toLowerCase()}`}>
              <strong>Last submission:</strong>{' '}
              <span>{lastSubmission.status}</span>{' '}
              <span>· Attempt #{lastSubmission.attempts}</span>{' '}
              <span>· {lastSubmission.language}</span>
            </div>
          )}
        </div>

      </div>
    </div>
  );
}

export default CodingDetailPage;
