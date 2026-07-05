import React, { useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import useForm from '../hooks/useForm';
import InputField from '../components/common/InputField';
import Button from '../components/common/Button';
import { ROUTES } from '../utils/constants';
import '../styles/auth.css';

const validate = (values) => ({
  email:    !values.email.trim()                     ? 'Email is required'
            : !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email) ? 'Enter a valid email'
            : '',
  password: !values.password ? 'Password is required' : '',
});

function LoginPage() {
  const { login, loading, isAuthenticated } = useAuth();
  const navigate  = useNavigate();
  const location  = useLocation();
  const from      = location.state?.from?.pathname || ROUTES.DASHBOARD;

  // If already logged in, skip to dashboard
  useEffect(() => {
    if (isAuthenticated()) navigate(ROUTES.DASHBOARD, { replace: true });
  }, [isAuthenticated, navigate]);

  const { values, handleChange, handleBlur, validateAll, getFieldError } = useForm(
    { email: '', password: '' },
    validate
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateAll()) return;
    const result = await login(values);
    if (result.success) navigate(from, { replace: true });
  };

  return (
    <div className="auth-page">
      <div className="auth-container">

        {/* Left panel */}
        <div className="auth-panel auth-panel--left">
          <div className="auth-brand">
            <span className="auth-brand__icon">🎯</span>
            <span className="auth-brand__name">CareerPilot <strong>AI</strong></span>
          </div>
          <h2 className="auth-panel__title">Welcome Back!</h2>
          <p className="auth-panel__subtitle">
            Log in to continue your placement preparation journey.
          </p>
          <ul className="auth-panel__perks">
            <li>✅ AI-powered resume feedback</li>
            <li>✅ Real interview practice</li>
            <li>✅ Personalised career roadmap</li>
            <li>✅ Skill gap analysis</li>
          </ul>
        </div>

        {/* Right panel — form */}
        <div className="auth-panel auth-panel--right">
          <div className="auth-form-wrapper">
            <h1 className="auth-form__title">Sign In</h1>
            <p className="auth-form__subtitle">
              Don't have an account?{' '}
              <Link to={ROUTES.REGISTER} className="auth-link">Create one free</Link>
            </p>

            <form onSubmit={handleSubmit} noValidate className="auth-form">
              <InputField
                label="Email Address"
                name="email"
                type="email"
                value={values.email}
                onChange={handleChange}
                onBlur={handleBlur}
                error={getFieldError('email')}
                placeholder="you@example.com"
                autoComplete="email"
                required
              />
              <InputField
                label="Password"
                name="password"
                type="password"
                value={values.password}
                onChange={handleChange}
                onBlur={handleBlur}
                error={getFieldError('password')}
                placeholder="Enter your password"
                autoComplete="current-password"
                required
              />

              <Button
                type="submit"
                variant="primary"
                size="lg"
                loading={loading}
                fullWidth
              >
                Sign In
              </Button>
            </form>

            <p className="auth-form__footer">
              <Link to={ROUTES.HOME} className="auth-link auth-link--muted">
                ← Back to Home
              </Link>
            </p>
          </div>
        </div>

      </div>
    </div>
  );
}

export default LoginPage;
