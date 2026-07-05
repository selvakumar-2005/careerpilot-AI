import React from 'react';
import '../../styles/components.css';

/**
 * Reusable loading spinner.
 * @param {string} size   - 'sm' | 'md' | 'lg'
 * @param {string} color  - CSS color value
 * @param {string} text   - Optional label below the spinner
 */
function LoadingSpinner({ size = 'md', color = '#2563EB', text = '' }) {
  return (
    <div className={`spinner-wrapper spinner-${size}`} role="status" aria-label="Loading">
      <div
        className="spinner"
        style={{ borderTopColor: color, borderRightColor: color }}
      />
      {text && <p className="spinner-text">{text}</p>}
    </div>
  );
}

export default LoadingSpinner;
