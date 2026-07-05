import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import codingService from '../services/codingService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import { DIFFICULTY_COLORS } from '../utils/constants';
import toast from 'react-hot-toast';
import '../styles/coding.css';

const DIFFICULTIES = ['ALL', 'EASY', 'MEDIUM', 'HARD'];

function QuestionRow({ question, index }) {
  const navigate = useNavigate();
  const dc = DIFFICULTY_COLORS[question.difficulty] || {};

  return (
    <tr
      className="question-row"
      onClick={() => navigate(`/coding/${question.id}`)}
      tabIndex={0}
      onKeyDown={(e) => e.key === 'Enter' && navigate(`/coding/${question.id}`)}
    >
      <td className="q-num">{index + 1}</td>
      <td className="q-title">{question.title}</td>
      <td className="q-topic">
        <span className="topic-tag">{question.topic}</span>
      </td>
      <td className="q-diff">
        <span className="diff-badge"
          style={{ background: dc.bg, color: dc.text }}>
          {question.difficulty}
        </span>
      </td>
      <td className="q-action">
        <button className="btn btn--primary btn--sm">Solve →</button>
      </td>
    </tr>
  );
}

function CodingListPage() {
  const [questions, setQuestions] = useState([]);
  const [filtered, setFiltered]   = useState([]);
  const [loading, setLoading]     = useState(true);
  const [difficulty, setDifficulty] = useState('ALL');
  const [search, setSearch]       = useState('');
  const navigate = useNavigate();

  const loadQuestions = useCallback(() => {
    setLoading(true);
    const diff = difficulty === 'ALL' ? null : difficulty;
    codingService.getQuestions(diff, search || null)
      .then((res) => {
        setQuestions(res.data.data || []);
        setFiltered(res.data.data || []);
      })
      .catch(() => toast.error('Failed to load questions'))
      .finally(() => setLoading(false));
  }, [difficulty, search]);

  useEffect(() => { loadQuestions(); }, [loadQuestions]);

  const counts = questions.reduce((acc, q) => {
    acc[q.difficulty] = (acc[q.difficulty] || 0) + 1;
    return acc;
  }, {});

  return (
    <div className="coding-page">
      <div className="page-header">
        <h1 className="page-title">💻 Coding Practice</h1>
        <p className="page-subtitle">
          {questions.length} questions · Solve, submit and track your progress
        </p>
      </div>

      {/* Stats bar */}
      <div className="coding-stats">
        {['EASY','MEDIUM','HARD'].map((d) => (
          <div key={d} className="coding-stat">
            <span className="coding-stat__value"
              style={{ color: DIFFICULTY_COLORS[d]?.text }}>
              {counts[d] || 0}
            </span>
            <span className="coding-stat__label">{d}</span>
          </div>
        ))}
        <div className="coding-stat">
          <span className="coding-stat__value">{questions.length}</span>
          <span className="coding-stat__label">TOTAL</span>
        </div>
      </div>

      {/* Filters */}
      <div className="coding-filters">
        <div className="filter-tabs">
          {DIFFICULTIES.map((d) => (
            <button
              key={d}
              className={`filter-tab ${difficulty === d ? 'filter-tab--active' : ''}`}
              onClick={() => setDifficulty(d)}
            >
              {d}
            </button>
          ))}
        </div>
        <input
          type="text"
          className="search-input"
          placeholder="Search by title or topic..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          aria-label="Search questions"
        />
        <button
          className="btn btn--outline btn--sm"
          onClick={() => navigate('/submissions')}
        >
          📝 My Submissions
        </button>
      </div>

      {/* Questions table */}
      {loading ? (
        <div className="center-spinner"><LoadingSpinner size="md" /></div>
      ) : questions.length === 0 ? (
        <div className="empty-state">
          <span className="empty-state__icon">🔍</span>
          <p>No questions found. Try adjusting your search.</p>
        </div>
      ) : (
        <div className="question-table-wrap">
          <table className="question-table">
            <thead>
              <tr>
                <th>#</th>
                <th>Title</th>
                <th>Topic</th>
                <th>Difficulty</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {questions.map((q, i) => (
                <QuestionRow key={q.id} question={q} index={i} />
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default CodingListPage;
