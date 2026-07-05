import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import codingService from '../services/codingService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import '../styles/coding.css';

function SubmissionsPage() {
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading]         = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    codingService.getMySubmissions()
      .then((res) => setSubmissions(res.data.data || []))
      .catch(() => toast.error('Failed to load submissions'))
      .finally(() => setLoading(false));
  }, []);

  return (
    <div className="coding-page">
      <div className="page-header">
        <div style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
          <button className="back-btn" onClick={() => navigate('/coding')}>← Back</button>
          <h1 className="page-title">📝 My Submissions</h1>
        </div>
        <p className="page-subtitle">All your code submissions across every question.</p>
      </div>

      {loading ? (
        <div className="center-spinner"><LoadingSpinner size="md" /></div>
      ) : submissions.length === 0 ? (
        <div className="empty-state">
          <span className="empty-state__icon">📭</span>
          <p>No submissions yet. Go solve some problems!</p>
          <button className="btn btn--primary btn--md" onClick={() => navigate('/coding')}>
            Start Coding
          </button>
        </div>
      ) : (
        <div className="question-table-wrap">
          <table className="question-table">
            <thead>
              <tr>
                <th>#</th>
                <th>Question</th>
                <th>Language</th>
                <th>Status</th>
                <th>Attempt</th>
                <th>Submitted At</th>
              </tr>
            </thead>
            <tbody>
              {submissions.map((s, i) => (
                <tr
                  key={s.id}
                  className="question-row"
                  onClick={() => navigate(`/coding/${s.questionId}`)}
                  tabIndex={0}
                  onKeyDown={(e) => e.key === 'Enter' && navigate(`/coding/${s.questionId}`)}
                >
                  <td className="q-num">{i + 1}</td>
                  <td className="q-title">{s.questionTitle}</td>
                  <td><span className="topic-tag">{s.language}</span></td>
                  <td>
                    <span className={`sub-status sub-status--${s.status?.toLowerCase()}`}>
                      {s.status}
                    </span>
                  </td>
                  <td>#{s.attempts}</td>
                  <td className="q-meta">
                    {s.submittedAt ? new Date(s.submittedAt).toLocaleString() : '—'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default SubmissionsPage;
