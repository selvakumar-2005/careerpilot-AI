import React from 'react';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '../utils/constants';
import '../styles/placeholder.css';

/**
 * Generic placeholder used for features not yet implemented.
 * Each feature route renders this with its own title/icon/description.
 */
function PlaceholderPage({ title, icon, description, comingSoon = true }) {
  const navigate = useNavigate();

  return (
    <div className="placeholder-page">
      <div className="placeholder-card">
        <div className="placeholder-icon">{icon}</div>
        <h1 className="placeholder-title">{title}</h1>
        <p className="placeholder-desc">{description}</p>

        {comingSoon && (
          <div className="placeholder-badge">
            <span>🔧</span> Under Development — Coming in Part 2
          </div>
        )}

        <div className="placeholder-actions">
          <button
            className="btn btn--primary btn--md"
            onClick={() => navigate(ROUTES.DASHBOARD)}
          >
            ← Back to Dashboard
          </button>
        </div>
      </div>
    </div>
  );
}

export default PlaceholderPage;
