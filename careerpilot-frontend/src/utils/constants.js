export const API_BASE_URL = process.env.REACT_APP_API_URL || '';
export const TOKEN_KEY    = 'careerpilot_token';
export const USER_KEY     = 'careerpilot_user';

export const ROUTES = {
  HOME:           '/',
  LOGIN:          '/login',
  REGISTER:       '/register',
  DASHBOARD:      '/dashboard',
  // Part 2 — live pages
  RESUME:         '/resume',
  CODING:         '/coding',
  CODING_DETAIL:  '/coding/:id',
  SUBMISSIONS:    '/submissions',
  COMPANIES:      '/companies',
  COMPANY_DETAIL: '/companies/:id',
  // Part 3 — placeholders
  INTERVIEW:      '/interview',
  CAREER:         '/career',
  SKILL_GAP:      '/skill-gap',
  // Part 3.1 — AI Foundation
  AI_TEST:        '/ai/test',
  // Part 3.2 — AI Modules (NEW)
  AI_MOCK_INTERVIEW: '/ai/mock-interview',
  AI_CAREER_GUIDANCE: '/ai/career',
  AI_SKILL_GAP: '/ai/skill-gap',
};

export const FEATURE_CARDS = [
  {
    id: 'resume',
    title: 'Resume Management',
    description: 'Upload your resume and manage all your resume versions.',
    icon: '📄',
    path: ROUTES.RESUME,
    color: '#2563EB',
    available: true,
  },
  {
    id: 'coding',
    title: 'Coding Practice',
    description: 'Solve curated interview problems across Easy, Medium and Hard levels.',
    icon: '💻',
    path: ROUTES.CODING,
    color: '#7C3AED',
    available: true,
  },
  {
    id: 'companies',
    title: 'Company Preparation',
    description: 'Explore company-specific interview requirements and preparation guides.',
    icon: '🏢',
    path: ROUTES.COMPANIES,
    color: '#0891B2',
    available: true,
  },
  {
    id: 'interview',
    title: 'Mock Interview',
    description: 'Practice with an AI interviewer and receive instant feedback.',
    icon: '🎤',
    path: ROUTES.AI_MOCK_INTERVIEW,
    color: '#DB2777',
    available: true,
  },
  {
    id: 'career',
    title: 'Career Guidance',
    description: 'Get personalised career roadmaps and industry insights.',
    icon: '🧭',
    path: ROUTES.AI_CAREER_GUIDANCE,
    color: '#059669',
    available: true,
  },
  {
    id: 'skill-gap',
    title: 'Skill Gap Analysis',
    description: 'Identify missing skills and get a targeted learning plan.',
    icon: '📊',
    path: ROUTES.AI_SKILL_GAP,
    color: '#D97706',
    available: true,
  },
];

export const DIFFICULTY_COLORS = {
  EASY:   { bg: '#DCFCE7', text: '#166534' },
  MEDIUM: { bg: '#FEF9C3', text: '#854D0E' },
  HARD:   { bg: '#FEE2E2', text: '#991B1B' },
};

export const LANGUAGES = ['Java', 'Python', 'C', 'C++', 'JavaScript', 'Go', 'Kotlin'];
