import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { FEATURE_CARDS, ROUTES } from '../utils/constants';
import dashboardService from '../services/dashboardService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import '../styles/dashboard.css';

function StatCard({ icon, value, label, color }) {
  return (
    <div className="stat-card">
      <div className="stat-card__icon" style={{ background: `${color}18`, color }}>
        {icon}
      </div>
      <div className="stat-card__info">
        <span className="stat-card__value">{value}</span>
        <span className="stat-card__label">{label}</span>
      </div>
    </div>
  );
}

function FeatureCard({ card }) {
  const navigate = useNavigate();
  return (
    <div
      className={`dash-card ${!card.available ? 'dash-card--disabled' : ''}`}
      onClick={() => card.available && navigate(card.path)}
      role="button"
      tabIndex={0}
      aria-label={`Open ${card.title}`}
      onKeyDown={(e) => e.key === 'Enter' && card.available && navigate(card.path)}
    >
      <div className="dash-card__icon-wrap" style={{ background: `${card.color}18` }}>
        <span className="dash-card__icon">{card.icon}</span>
      </div>
      <div className="dash-card__body">
        <h3 className="dash-card__title">{card.title}</h3>
        <p className="dash-card__desc">{card.description}</p>
      </div>
      <div className="dash-card__footer">
        {card.available
          ? <span className="badge badge--active" style={{ background: card.color }}>Open →</span>
          : <span className="badge badge--soon">Coming in Part 3</span>
        }
      </div>
    </div>
  );
}

function DashboardPage() {
  const { user } = useAuth();
  const [stats, setStats]     = useState(null);
  const [loading, setLoading] = useState(true);

  const firstName = user?.fullName?.split(' ')[0] || 'Student';
  const today = new Date().toLocaleDateString('en-US', {
    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric',
  });

  useEffect(() => {
    dashboardService.getStats()
      .then((res) => setStats(res.data.data))
      .catch(() => setStats(null))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="dashboard-loading">
        <LoadingSpinner size="lg" text="Loading dashboard..." />
      </div>
    );
  }

  return (
    <div className="dashboard">

      {/* Welcome banner */}
      <section className="dash-welcome">
        <div className="dash-welcome__text">
          <p className="dash-welcome__date">{today}</p>
          <h1 className="dash-welcome__heading">
            Welcome back, <span className="dash-welcome__name">{firstName}</span> 👋
          </h1>
          <p className="dash-welcome__sub">
            Your placement preparation dashboard. Track your progress and keep going!
          </p>
        </div>
        <div className="dash-welcome__avatar">
          <div className="avatar-circle">
            {user?.fullName?.charAt(0)?.toUpperCase() || 'S'}
          </div>
        </div>
      </section>

      {/* Stats row */}
      {stats && (
        <div className="stats-row">
          <StatCard icon="📄" value={stats.totalResumes}        label="Resumes Uploaded"    color="#2563EB" />
          <StatCard icon="💻" value={stats.totalCodingQuestions} label="Total Questions"    color="#7C3AED" />
          <StatCard icon="✅" value={stats.solvedQuestions}     label="Questions Attempted" color="#059669" />
          <StatCard icon="📨" value={stats.totalSubmissions}    label="Submissions"         color="#0891B2" />
          <StatCard icon="🏢" value={stats.totalCompanies}      label="Companies"           color="#D97706" />
        </div>
      )}

      {/* Feature cards */}
      <section className="dash-features">
        <h2 className="dash-features__heading">Modules</h2>
        <div className="dash-features__grid">
          {FEATURE_CARDS.map((card) => (
            <FeatureCard key={card.id} card={card} />
          ))}
        </div>
      </section>

      {/* Recent activity */}
      {stats && (
        <div className="activity-row">

          {/* Recent resumes */}
          <div className="activity-card">
            <h3 className="activity-card__title">📄 Recent Resumes</h3>
            {stats.recentResumes?.length === 0 ? (
              <p className="activity-empty">No resumes uploaded yet.</p>
            ) : (
              <ul className="activity-list">
                {stats.recentResumes?.map((r) => (
                  <li key={r.id} className="activity-item">
                    <span className="activity-item__name">{r.originalFileName}</span>
                    <span className="activity-item__meta">
                      {r.uploadDate ? new Date(r.uploadDate).toLocaleDateString() : '—'}
                    </span>
                  </li>
                ))}
              </ul>
            )}
          </div>

          {/* Recent submissions */}
          <div className="activity-card">
            <h3 className="activity-card__title">💻 Recent Submissions</h3>
            {stats.recentSubmissions?.length === 0 ? (
              <p className="activity-empty">No submissions yet.</p>
            ) : (
              <ul className="activity-list">
                {stats.recentSubmissions?.map((s) => (
                  <li key={s.id} className="activity-item">
                    <span className="activity-item__name">{s.questionTitle}</span>
                    <span className={`activity-item__status status--${s.status?.toLowerCase()}`}>
                      {s.status}
                    </span>
                  </li>
                ))}
              </ul>
            )}
          </div>

        </div>
      )}

    </div>
  );
}

export default DashboardPage;
