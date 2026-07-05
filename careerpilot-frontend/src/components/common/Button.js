import React from 'react';
import LoadingSpinner from './LoadingSpinner';
import '../../styles/components.css';

/**
 * Reusable button component.
 * @param {string}  variant  - 'primary' | 'secondary' | 'outline' | 'ghost'
 * @param {string}  size     - 'sm' | 'md' | 'lg'
 * @param {boolean} loading  - Shows spinner and disables the button
 * @param {boolean} fullWidth
 */
function Button({
  children,
  variant = 'primary',
  size = 'md',
  loading = false,
  fullWidth = false,
  disabled = false,
  type = 'button',
  onClick,
  className = '',
}) {
  return (
    <button
      type={type}
      onClick={onClick}
      disabled={disabled || loading}
      className={`btn btn--${variant} btn--${size} ${fullWidth ? 'btn--full' : ''} ${className}`}
      aria-busy={loading}
    >
      {loading ? (
        <span className="btn-loading">
          <LoadingSpinner size="sm" color="#ffffff" />
          <span>Please wait...</span>
        </span>
      ) : (
        children
      )}
    </button>
  );
}

export default Button;
