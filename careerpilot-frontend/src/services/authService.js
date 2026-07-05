import axiosInstance from './axiosInstance';

/**
 * Auth API calls — thin wrappers that return the full Axios response
 * so callers can access response.data directly.
 */
const authService = {
  register: (payload) =>
    axiosInstance.post('/api/auth/register', payload),

  login: (payload) =>
    axiosInstance.post('/api/auth/login', payload),

  logout: () =>
    axiosInstance.post('/api/auth/logout'),

  getCurrentUser: () =>
    axiosInstance.get('/api/users/me'),
};

export default authService;
