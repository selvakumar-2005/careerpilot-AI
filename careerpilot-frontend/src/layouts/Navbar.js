import React, { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { ROUTES } from '../utils/constants';
import '../styles/navbar.css';

function Navbar({ onMenuToggle, sidebarOpen }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const handleLogout = async () => {
    setDropdownOpen(false);
    await logout();
    navigate(ROUTES.LOGIN, { replace: true });
  };

  const initials = user?.fullName
    ? user.fullName.split(' ').map((n) => n[0]).join('').toUpperCase().slice(0, 2)
    : 'U';

  return (
    <header className="navbar" role="banner">
      <div className="navbar__left">
        {/* Hamburger — toggles sidebar on mobile */}
        <button
          className={`navbar__menu-btn ${sidebarOpen ? 'navbar__menu-btn--active' : ''}`}
          onClick={onMenuToggle}
          aria-label="Toggle navigation menu"
          aria-expanded={sidebarOpen}
        >
          <span />
          <span />
          <span />
        </button>

        {/* Brand */}
        <Link to={ROUTES.DASHBOARD} className="navbar__brand">
          <span className="navbar__brand-icon">🎯</span>
          <span className="navbar__brand-name">
            CareerPilot <strong>AI</strong>
          </span>
        </Link>
      </div>

      <div className="navbar__right">
        {/* Page title hint on mobile */}
        <div className="navbar__page-info">
          {getBreadcrumb(location.pathname)}
        </div>

        {/* User menu */}
        <div className="navbar__user" onBlur={() => setTimeout(() => setDropdownOpen(false), 150)}>
          <button
            className="navbar__avatar-btn"
            onClick={() => setDropdownOpen((prev) => !prev)}
            aria-haspopup="true"
            aria-expanded={dropdownOpen}
            aria-label="User menu"
          >
            <div className="navbar__avatar">{initials}</div>
            <div className="navbar__user-info">
              <span className="navbar__user-name">{user?.fullName}</span>
              <span className="navbar__user-role">{user?.role}</span>
            </div>
            <span className="navbar__chevron">{dropdownOpen ? '▲' : '▼'}</span>
          </button>

          {dropdownOpen && (
            <div className="navbar__dropdown" role="menu">
              <div className="navbar__dropdown-header">
                <div className="navbar__avatar navbar__avatar--lg">{initials}</div>
                <div>
                  <p className="dropdown__name">{user?.fullName}</p>
                  <p className="dropdown__email">{user?.email}</p>
                </div>
              </div>
              <hr className="dropdown__divider" />
              <button
                className="dropdown__item"
                role="menuitem"
                onClick={() => { setDropdownOpen(false); navigate(ROUTES.DASHBOARD); }}
              >
                🏠 Dashboard
              </button>
              <hr className="dropdown__divider" />
              <button
                className="dropdown__item dropdown__item--danger"
                role="menuitem"
                onClick={handleLogout}
              >
                🚪 Sign Out
              </button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}

function getBreadcrumb(pathname) {
  const map = {
    '/dashboard': 'Dashboard',
    '/resume':    'Resume Analysis',
    '/coding':    'Coding Practice',
    '/interview': 'Mock Interview',
    '/career':    'Career Guidance',
    '/skill-gap': 'Skill Gap Analysis',
  };
  return map[pathname] || '';
}

export default Navbar;
