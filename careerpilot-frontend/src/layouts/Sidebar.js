import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { ROUTES } from '../utils/constants';
import '../styles/sidebar.css';

const NAV_SECTIONS = [
  {
    label: 'MAIN',
    items: [
      { label: 'Dashboard',       path: ROUTES.DASHBOARD,  icon: '🏠', exact: true },
    ],
  },
  {
    label: 'PLACEMENT PREP',
    items: [
      { label: 'Resume',          path: ROUTES.RESUME,     icon: '📄' },
      { label: 'Coding Practice', path: ROUTES.CODING,     icon: '💻' },
      { label: 'My Submissions',  path: ROUTES.SUBMISSIONS, icon: '📝' },
      { label: 'Companies',       path: ROUTES.COMPANIES,  icon: '🏢' },
    ],
  },
  {
    label: 'AI FEATURES',
    items: [
      { label: 'AI Test Console', path: ROUTES.AI_TEST,    icon: '🤖' },
      { label: 'Mock Interview',  path: ROUTES.INTERVIEW,  icon: '🎤', disabled: true },
      { label: 'Career Guidance', path: ROUTES.CAREER,     icon: '🧭', disabled: true },
      { label: 'Skill Gap',       path: ROUTES.SKILL_GAP,  icon: '📊', disabled: true },
    ],
  },
];

function Sidebar({ isOpen, onClose }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate(ROUTES.LOGIN, { replace: true });
  };

  const initials = user?.fullName
    ? user.fullName.split(' ').map((n) => n[0]).join('').toUpperCase().slice(0, 2)
    : 'U';

  return (
    <>
      {isOpen && (
        <div className="sidebar-backdrop" onClick={onClose} aria-hidden="true" />
      )}

      <aside className={`sidebar ${isOpen ? 'sidebar--open' : ''}`} aria-label="Main navigation">

        <div className="sidebar__header">
          <div className="sidebar__brand">
            <span className="sidebar__brand-icon">🎯</span>
            <span className="sidebar__brand-name">CareerPilot <strong>AI</strong></span>
          </div>
          <button className="sidebar__close" onClick={onClose} aria-label="Close navigation">✕</button>
        </div>

        <div className="sidebar__profile">
          <div className="sidebar__avatar">{initials}</div>
          <div className="sidebar__profile-info">
            <p className="sidebar__profile-name">{user?.fullName}</p>
            <span className="sidebar__profile-role">{user?.role}</span>
          </div>
        </div>

        <hr className="sidebar__divider" />

        <nav className="sidebar__nav" role="navigation">
          {NAV_SECTIONS.map((section) => (
            <div key={section.label} className="sidebar__nav-section">
              <p className="sidebar__nav-label">{section.label}</p>
              <ul className="sidebar__nav-list">
                {section.items.map((item) =>
                  item.disabled ? (
                    <li key={item.path}>
                      <span className="sidebar__nav-item sidebar__nav-item--disabled">
                        <span className="sidebar__nav-icon">{item.icon}</span>
                        <span className="sidebar__nav-label-text">{item.label}</span>
                        <span className="sidebar__nav-soon">Soon</span>
                      </span>
                    </li>
                  ) : (
                    <li key={item.path}>
                      <NavLink
                        to={item.path}
                        end={item.exact}
                        className={({ isActive }) =>
                          `sidebar__nav-item ${isActive ? 'sidebar__nav-item--active' : ''}`
                        }
                        onClick={onClose}
                      >
                        <span className="sidebar__nav-icon">{item.icon}</span>
                        <span className="sidebar__nav-label-text">{item.label}</span>
                      </NavLink>
                    </li>
                  )
                )}
              </ul>
            </div>
          ))}
        </nav>

        <div className="sidebar__spacer" />

        <div className="sidebar__footer">
          <button className="sidebar__logout-btn" onClick={handleLogout}>
            <span>🚪</span>
            <span>Sign Out</span>
          </button>
        </div>
      </aside>
    </>
  );
}

export default Sidebar;
