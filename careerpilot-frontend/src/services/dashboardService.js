import axiosInstance from './axiosInstance';

const dashboardService = {
  getStats: () => axiosInstance.get('/api/dashboard/stats'),
};

export default dashboardService;
