import React, { createContext, useCallback, useContext, useEffect, useState } from 'react';
import toast from 'react-hot-toast';
import authService from '../services/authService';
import { storage } from '../utils/storage';

const AuthContext = createContext(null);

/**
 * AuthProvider wraps the app and exposes auth state + actions
 * to every component via the useAuth() hook.
 */
export function AuthProvider({ children }) {
  const [user, setUser]       = useState(storage.getUser());
  const [loading, setLoading] = useState(false);
  // Tracks whether we've finished checking the stored token on mount
  const [initialising, setInitialising] = useState(true);

  // On mount: if a token exists, verify it by fetching /api/users/me
  useEffect(() => {
    const verifyToken = async () => {
      const token = storage.getToken();
      if (!token) {
        setInitialising(false);
        return;
      }
      try {
        const response = await authService.getCurrentUser();
        setUser(response.data.data);
        storage.setUser(response.data.data);
      } catch {
        // Token is expired or invalid — clear everything
        storage.clear();
        setUser(null);
      } finally {
        setInitialising(false);
      }
    };
    verifyToken();
  }, []);

  const login = useCallback(async (credentials) => {
    setLoading(true);
    try {
      const response = await authService.login(credentials);
      const { token, user: userData } = response.data.data;
      storage.setToken(token);
      storage.setUser(userData);
      setUser(userData);
      toast.success(`Welcome back, ${userData.fullName}!`);
      return { success: true };
    } catch (error) {
      const message =
        error.response?.data?.message || 'Login failed. Please try again.';
      toast.error(message);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  }, []);

  const register = useCallback(async (payload) => {
    setLoading(true);
    try {
      const response = await authService.register(payload);
      const { token, user: userData } = response.data.data;
      storage.setToken(token);
      storage.setUser(userData);
      setUser(userData);
      toast.success('Account created successfully! Welcome to CareerPilot AI.');
      return { success: true };
    } catch (error) {
      const serverErrors = error.response?.data?.data; // validation map
      const message =
        error.response?.data?.message || 'Registration failed. Please try again.';

      if (serverErrors && typeof serverErrors === 'object') {
        // Show first validation error as toast
        const firstError = Object.values(serverErrors)[0];
        toast.error(firstError);
        return { success: false, message: firstError, fieldErrors: serverErrors };
      }

      toast.error(message);
      return { success: false, message };
    } finally {
      setLoading(false);
    }
  }, []);

  const logout = useCallback(async () => {
    try {
      await authService.logout();
    } catch {
      // Ignore — server-side is stateless; token removal is enough
    } finally {
      storage.clear();
      setUser(null);
      toast.success('Logged out successfully.');
    }
  }, []);

  const isAuthenticated = useCallback(() => {
    return !!storage.getToken() && !!user;
  }, [user]);

  const currentUser = useCallback(() => user, [user]);

  const value = {
    user,
    loading,
    initialising,
    login,
    logout,
    register,
    isAuthenticated,
    currentUser,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

/**
 * useAuth — consume auth context in any component.
 * Throws if used outside <AuthProvider>.
 */
export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}

export default AuthContext;
