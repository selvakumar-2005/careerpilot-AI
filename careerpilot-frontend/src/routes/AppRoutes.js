import React from 'react';
import { Routes, Route } from 'react-router-dom';

import { ROUTES } from '../utils/constants';
import ProtectedRoute from '../components/common/ProtectedRoute';
import DashboardLayout from '../layouts/DashboardLayout';

// Public pages
import LandingPage    from '../pages/LandingPage';
import LoginPage      from '../pages/LoginPage';
import RegisterPage   from '../pages/RegisterPage';
import NotFoundPage   from '../pages/NotFoundPage';

// Part 1 dashboard
import DashboardPage  from '../pages/DashboardPage';

// Part 2 — fully implemented pages
import ResumePage         from '../pages/ResumePage';
import CodingListPage     from '../pages/CodingListPage';
import CodingDetailPage   from '../pages/CodingDetailPage';
import SubmissionsPage    from '../pages/SubmissionsPage';
import CompanyListPage    from '../pages/CompanyListPage';
import CompanyDetailPage  from '../pages/CompanyDetailPage';

// Part 3 — placeholders
import InterviewPage  from '../pages/InterviewPage';
import CareerPage     from '../pages/CareerPage';
import SkillGapPage   from '../pages/SkillGapPage';

// Part 3.1 — AI Foundation
import AITestPage     from '../pages/AITestPage';

// Part 3.2 — AI Modules
import AIResumeAnalyzerPage from '../pages/AIResumeAnalyzerPage';
import AICodingMentorPage from '../pages/AICodingMentorPage';
import AICareerGuidancePage from '../pages/AICareerGuidancePage';
import AIInterviewGeneratorPage from '../pages/AIInterviewGeneratorPage';
import AICompanyPreparationPage from '../pages/AICompanyPreparationPage';
import AICareerChatPage from '../pages/AICareerChatPage';
import AIMockInterviewPage from '../pages/AIMockInterviewPage';
import AISkillGapAnalysisPage from '../pages/AISkillGapAnalysisPage';

function AppRoutes() {
  return (
    <Routes>
      {/* Public */}
      <Route path={ROUTES.HOME}     element={<LandingPage />} />
      <Route path={ROUTES.LOGIN}    element={<LoginPage />} />
      <Route path={ROUTES.REGISTER} element={<RegisterPage />} />

      {/* Protected — all share DashboardLayout */}
      <Route
        element={
          <ProtectedRoute>
            <DashboardLayout />
          </ProtectedRoute>
        }
      >
        <Route path={ROUTES.DASHBOARD}      element={<DashboardPage />} />
        <Route path={ROUTES.RESUME}         element={<ResumePage />} />
        <Route path={ROUTES.CODING}         element={<CodingListPage />} />
        <Route path="/coding/:id"           element={<CodingDetailPage />} />
        <Route path={ROUTES.SUBMISSIONS}    element={<SubmissionsPage />} />
        <Route path={ROUTES.COMPANIES}      element={<CompanyListPage />} />
        <Route path="/companies/:id"        element={<CompanyDetailPage />} />
        <Route path={ROUTES.INTERVIEW}      element={<InterviewPage />} />
        <Route path={ROUTES.CAREER}         element={<CareerPage />} />
        <Route path={ROUTES.SKILL_GAP}      element={<SkillGapPage />} />
        {/* Part 3.1 — AI Foundation */}
        <Route path={ROUTES.AI_TEST}        element={<AITestPage />} />
        {/* Part 3.2 — AI Modules */}
        <Route path="/ai/resume"            element={<AIResumeAnalyzerPage />} />
        <Route path="/ai/coding"            element={<AICodingMentorPage />} />
        <Route path="/ai/career"            element={<AICareerGuidancePage />} />
        <Route path="/ai/interview"         element={<AIInterviewGeneratorPage />} />
        <Route path="/ai/company"           element={<AICompanyPreparationPage />} />
        <Route path="/ai/chat"              element={<AICareerChatPage />} />
        <Route path="/ai/mock-interview"    element={<AIMockInterviewPage />} />
        <Route path="/ai/skill-gap"         element={<AISkillGapAnalysisPage />} />
      </Route>

      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
}

export default AppRoutes;
