import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ROUTES } from '../utils/constants';
import '../styles/notfound.css';

function NotFoundPage() {
  const navigate = useNavigate();

  return (
    <div className="notfound-page">
      <div className="notfound-content">
        <div className="notfound-code">404</div>
        <div className="notfound-icon">🧭</div>
        <h1 className="notfound-title">Page Not Found</h1>
        <p className="notfound-subtitle">
          Looks like this page took a wrong turn. It might have been moved,
          deleted, or never existed.
        </p>
        <div className="notfound-actions">
          <button
            className="btn btn--primary btn--lg"
            onClick={() => navigate(-1)}
          >
            ← Go Back
          </button>
          <Link to={ROUTES.HOME} className="btn btn--outline btn--lg">
            Go to Home
          </Link>
          <Link to={ROUTES.DASHBOARD} className="btn btn--ghost btn--lg">
            Dashboard
          </Link>
        </div>
      </div>
    </div>
  );
}

export default NotFoundPage;
