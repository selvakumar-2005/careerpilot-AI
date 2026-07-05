import axiosInstance from './axiosInstance';

const companyService = {
  getAll:    ()         => axiosInstance.get('/api/companies'),
  getById:   (id)       => axiosInstance.get(`/api/companies/${id}`),
  create:    (payload)  => axiosInstance.post('/api/companies', payload),
  update:    (id, data) => axiosInstance.put(`/api/companies/${id}`, data),
  delete:    (id)       => axiosInstance.delete(`/api/companies/${id}`),
};

export default companyService;
