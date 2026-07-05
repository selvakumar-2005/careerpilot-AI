import axiosInstance from './axiosInstance';

const resumeService = {
  upload: (file) => {
    const form = new FormData();
    form.append('file', file);
    return axiosInstance.post('/api/resumes', form, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
  },

  getMyResumes: () => axiosInstance.get('/api/resumes'),

  getById: (id) => axiosInstance.get(`/api/resumes/${id}`),

  // Returns a blob URL — caller must revoke it after use
  download: async (id, originalFileName) => {
    const response = await axiosInstance.get(`/api/resumes/${id}/download`, {
      responseType: 'blob',
    });
    const url  = window.URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));
    const link = document.createElement('a');
    link.href  = url;
    link.setAttribute('download', originalFileName);
    document.body.appendChild(link);
    link.click();
    link.remove();
    window.URL.revokeObjectURL(url);
  },

  delete: (id) => axiosInstance.delete(`/api/resumes/${id}`),
};

export default resumeService;
