import axiosInstance from './axiosInstance';

const codingService = {
  getQuestions: (difficulty, search) => {
    const params = {};
    if (difficulty) params.difficulty = difficulty;
    if (search)     params.search     = search;
    return axiosInstance.get('/api/coding/questions', { params });
  },

  getQuestionById: (id) => axiosInstance.get(`/api/coding/questions/${id}`),

  submit: (payload) => axiosInstance.post('/api/coding/submit', payload),

  getMySubmissions: () => axiosInstance.get('/api/coding/submissions'),

  getSubmissionsByQuestion: (questionId) =>
    axiosInstance.get(`/api/coding/questions/${questionId}/submissions`),
};

export default codingService;
