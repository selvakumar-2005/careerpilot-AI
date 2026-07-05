import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { ROUTES } from '../../utils/constants';
import LoadingSpinner from './LoadingSpinner';

/**
 * Wraps any route that requires authentication.
 * - While the token is being verified on mount, shows a full-page spinner.
 * - If unauthenticated, redirects to /login, preserving the intended path in state.
 * - If authenticated, renders the child component.
 */
function ProtectedRoute({ children }) {
  const { isAuthenticated, initialising } = useAuth();
  const location = useLocation();

  if (initialising) {
    return (
      <div className="full-page-center">
        <LoadingSpinner size="lg" text="Loading CareerPilot AI..." />
      </div>
    );
  }

  if (!isAuthenticated()) {
    return (
      <Navigate
        to={ROUTES.LOGIN}
        state={{ from: location }}
        replace
      />
    );
  }

  return children;
}

export default ProtectedRoute;
