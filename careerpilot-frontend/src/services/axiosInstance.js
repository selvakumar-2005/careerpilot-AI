import axios from 'axios';
import { storage } from '../utils/storage';
import { ROUTES } from '../utils/constants';

/**
 * Reusable Axios instance.
 *
 * - Automatically attaches the JWT Bearer token to every request.
 * - Intercepts 401 responses: clears storage and redirects to /login.
 */
const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_URL || '',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor — attach token
axiosInstance.interceptors.request.use(
  (config) => {
    const token = storage.getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor — handle 401 globally
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      storage.clear();
      // Hard redirect so the AuthContext re-initialises cleanly
      window.location.href = ROUTES.LOGIN;
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
