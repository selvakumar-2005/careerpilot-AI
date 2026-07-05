import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import useForm from '../hooks/useForm';
import InputField from '../components/common/InputField';
import Button from '../components/common/Button';
import { ROUTES } from '../utils/constants';
import '../styles/auth.css';

const validate = (values) => ({
  fullName: !values.fullName.trim()
              ? 'Full name is required'
              : values.fullName.trim().length < 2
              ? 'Full name must be at least 2 characters'
              : '',

  email: !values.email.trim()
           ? 'Email is required'
           : !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(values.email)
           ? 'Enter a valid email address'
           : '',

  password: !values.password
              ? 'Password is required'
              : values.password.length < 8
              ? 'Minimum 8 characters'
              : !/[A-Z]/.test(values.password)
              ? 'Add at least one uppercase letter'
              : !/[a-z]/.test(values.password)
              ? 'Add at least one lowercase letter'
              : !/\d/.test(values.password)
              ? 'Add at least one number'
              : !/[@$!%*?&_#^()+=\-]/.test(values.password)
              ? 'Add at least one special character (@$!%*?&...)'
              : '',

  confirmPassword: !values.confirmPassword
                     ? 'Please confirm your password'
                     : values.password !== values.confirmPassword
                     ? 'Passwords do not match'
                     : '',
});

/** Visual indicator showing which password rules are satisfied */
function PasswordStrength({ password }) {
  const rules = [
    { label: '8+ characters',       met: password.length >= 8 },
    { label: 'Uppercase letter',     met: /[A-Z]/.test(password) },
    { label: 'Lowercase letter',     met: /[a-z]/.test(password) },
    { label: 'Number',               met: /\d/.test(password) },
    { label: 'Special character',    met: /[@$!%*?&_#^()+=\-]/.test(password) },
  ];

  if (!password) return null;

  const metCount = rules.filter((r) => r.met).length;
  const strengthLabel = ['', 'Weak', 'Fair', 'Good', 'Strong', 'Very Strong'][metCount];
  const strengthColor = ['', '#EF4444', '#F59E0B', '#3B82F6', '#22C55E', '#16A34A'][metCount];

  return (
    <div className="password-strength">
      <div className="strength-bar">
        {rules.map((_, i) => (
          <div
            key={i}
            className="strength-segment"
            style={{ background: i < metCount ? strengthColor : '#E2E8F0' }}
          />
        ))}
      </div>
      <span className="strength-label" style={{ color: strengthColor }}>
        {strengthLabel}
      </span>
      <ul className="strength-rules">
        {rules.map((rule) => (
          <li key={rule.label} className={rule.met ? 'rule--met' : 'rule--unmet'}>
            {rule.met ? '✓' : '○'} {rule.label}
          </li>
        ))}
      </ul>
    </div>
  );
}

function RegisterPage() {
  const { register, loading, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (isAuthenticated()) navigate(ROUTES.DASHBOARD, { replace: true });
  }, [isAuthenticated, navigate]);

  const { values, handleChange, handleBlur, validateAll, getFieldError } = useForm(
    { fullName: '', email: '', password: '', confirmPassword: '' },
    validate
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateAll()) return;
    const result = await register(values);
    if (result.success) navigate(ROUTES.DASHBOARD, { replace: true });
  };

  return (
    <div className="auth-page">
      <div className="auth-container auth-container--wide">

        {/* Left panel */}
        <div className="auth-panel auth-panel--left">
          <div className="auth-brand">
            <span className="auth-brand__icon">🎯</span>
            <span className="auth-brand__name">CareerPilot <strong>AI</strong></span>
          </div>
          <h2 className="auth-panel__title">Start Your Journey</h2>
          <p className="auth-panel__subtitle">
            Join thousands of students preparing smarter with AI.
          </p>
          <ul className="auth-panel__perks">
            <li>🆓 Completely free to start</li>
            <li>🤖 AI-powered feedback</li>
            <li>📈 Track your progress</li>
            <li>🎯 Role-based preparation</li>
          </ul>
        </div>

        {/* Right panel — form */}
        <div className="auth-panel auth-panel--right">
          <div className="auth-form-wrapper">
            <h1 className="auth-form__title">Create Account</h1>
            <p className="auth-form__subtitle">
              Already have an account?{' '}
              <Link to={ROUTES.LOGIN} className="auth-link">Sign in</Link>
            </p>

            <form onSubmit={handleSubmit} noValidate className="auth-form">
              <InputField
                label="Full Name"
                name="fullName"
                type="text"
                value={values.fullName}
                onChange={handleChange}
                onBlur={handleBlur}
                error={getFieldError('fullName')}
                placeholder="John Doe"
                autoComplete="name"
                required
              />
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
                placeholder="Create a strong password"
                autoComplete="new-password"
                required
              />

              {/* Live password strength meter */}
              <PasswordStrength password={values.password} />

              <InputField
                label="Confirm Password"
                name="confirmPassword"
                type="password"
                value={values.confirmPassword}
                onChange={handleChange}
                onBlur={handleBlur}
                error={getFieldError('confirmPassword')}
                placeholder="Repeat your password"
                autoComplete="new-password"
                required
              />

              <Button
                type="submit"
                variant="primary"
                size="lg"
                loading={loading}
                fullWidth
              >
                Create Account
              </Button>

              <p className="auth-terms">
                By creating an account you agree to our{' '}
                <span className="auth-link">Terms of Service</span> and{' '}
                <span className="auth-link">Privacy Policy</span>.
              </p>
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

export default RegisterPage;
