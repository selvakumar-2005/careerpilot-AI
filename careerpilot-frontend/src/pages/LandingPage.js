import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { ROUTES, FEATURE_CARDS } from '../utils/constants';
import '../../src/styles/landing.css';

function LandingPage() {
  const { isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const handleGetStarted = () => {
    if (isAuthenticated()) {
      navigate(ROUTES.DASHBOARD);
    } else {
      navigate(ROUTES.REGISTER);
    }
  };

  return (
    <div className="landing">

      {/* ── Navbar ── */}
      <nav className="landing-nav">
        <div className="landing-nav__brand">
          <span className="brand-icon">🎯</span>
          <span className="brand-name">CareerPilot <span className="brand-ai">AI</span></span>
        </div>
        <div className="landing-nav__links">
          <a href="#features" className="nav-link">Features</a>
          <a href="#how-it-works" className="nav-link">How It Works</a>
          <Link to={ROUTES.LOGIN} className="nav-link">Login</Link>
          <Link to={ROUTES.REGISTER} className="btn btn--primary btn--sm">Get Started</Link>
        </div>
      </nav>

      {/* ── Hero ── */}
      <section className="hero">
        <div className="hero__content">
          <div className="hero__badge">🚀 AI-Powered Placement Preparation</div>
          <h1 className="hero__title">
            Land Your Dream Job with
            <span className="hero__title--highlight"> CareerPilot AI</span>
          </h1>
          <p className="hero__subtitle">
            The all-in-one platform that uses artificial intelligence to help students
            ace technical interviews, build standout resumes, and navigate their career paths
            with confidence.
          </p>
          <div className="hero__actions">
            <button className="btn btn--primary btn--lg" onClick={handleGetStarted}>
              Start Preparing Free
            </button>
            <a href="#features" className="btn btn--outline btn--lg">
              Explore Features
            </a>
          </div>
          <div className="hero__stats">
            <div className="stat">
              <span className="stat__value">10K+</span>
              <span className="stat__label">Students Placed</span>
            </div>
            <div className="stat__divider" />
            <div className="stat">
              <span className="stat__value">500+</span>
              <span className="stat__label">Companies</span>
            </div>
            <div className="stat__divider" />
            <div className="stat">
              <span className="stat__value">95%</span>
              <span className="stat__label">Success Rate</span>
            </div>
          </div>
        </div>
        <div className="hero__visual">
          <div className="hero__card-stack">
            <div className="floating-card floating-card--1">
              <span>📄</span> Resume Score: <strong>92/100</strong>
            </div>
            <div className="floating-card floating-card--2">
              <span>✅</span> Interview Ready!
            </div>
            <div className="floating-card floating-card--3">
              <span>💡</span> 3 Skills to Learn
            </div>
          </div>
        </div>
      </section>

      {/* ── Features ── */}
      <section className="features" id="features">
        <div className="section-header">
          <h2 className="section-title">Everything You Need to Get Placed</h2>
          <p className="section-subtitle">
            Five powerful AI modules designed to prepare you for every stage of the hiring process.
          </p>
        </div>
        <div className="features__grid">
          {FEATURE_CARDS.map((card) => (
            <div key={card.id} className="feature-card">
              <div
                className="feature-card__icon"
                style={{ background: `${card.color}18`, color: card.color }}
              >
                {card.icon}
              </div>
              <h3 className="feature-card__title">{card.title}</h3>
              <p className="feature-card__desc">{card.description}</p>
              <span className="feature-card__badge">Coming Soon</span>
            </div>
          ))}
        </div>
      </section>

      {/* ── How It Works ── */}
      <section className="how-it-works" id="how-it-works">
        <div className="section-header">
          <h2 className="section-title">How It Works</h2>
          <p className="section-subtitle">Three simple steps to placement success.</p>
        </div>
        <div className="steps">
          {[
            { step: '01', title: 'Create Your Profile', desc: 'Sign up and tell us about your background, target roles, and skill level.', icon: '👤' },
            { step: '02', title: 'Practice With AI',    desc: 'Use our AI-powered modules to practise coding, mock interviews, and resume review.', icon: '🤖' },
            { step: '03', title: 'Get Placed',          desc: 'Apply with confidence, track your progress, and land your dream job.', icon: '🏆' },
          ].map((item) => (
            <div key={item.step} className="step">
              <div className="step__number">{item.step}</div>
              <div className="step__icon">{item.icon}</div>
              <h3 className="step__title">{item.title}</h3>
              <p className="step__desc">{item.desc}</p>
            </div>
          ))}
        </div>
      </section>

      {/* ── CTA ── */}
      <section className="cta">
        <div className="cta__content">
          <h2 className="cta__title">Ready to Kickstart Your Career?</h2>
          <p className="cta__subtitle">Join thousands of students who are already preparing smarter.</p>
          <button className="btn btn--white btn--lg" onClick={handleGetStarted}>
            Create Free Account
          </button>
        </div>
      </section>

      {/* ── Footer ── */}
      <footer className="landing-footer">
        <div className="footer__brand">
          <span>🎯</span> CareerPilot AI
        </div>
        <p className="footer__copy">© {new Date().getFullYear()} CareerPilot AI. All rights reserved.</p>
      </footer>

    </div>
  );
}

export default LandingPage;
