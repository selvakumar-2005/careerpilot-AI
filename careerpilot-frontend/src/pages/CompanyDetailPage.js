import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import companyService from '../services/companyService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import '../styles/company.css';

function InfoSection({ icon, title, content }) {
  if (!content) return null;
  return (
    <div className="info-section">
      <h3 className="info-section__title">{icon} {title}</h3>
      <p className="info-section__content">{content}</p>
    </div>
  );
}

function CompanyDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    companyService.getById(id)
      .then((res) => setCompany(res.data.data))
      .catch(() => { toast.error('Company not found'); navigate('/companies'); })
      .finally(() => setLoading(false));
  }, [id, navigate]);

  if (loading) return <div className="center-spinner"><LoadingSpinner size="lg" /></div>;
  if (!company) return null;

  return (
    <div className="company-detail-page">
      <button className="back-btn" onClick={() => navigate('/companies')}>
        ← Back to Companies
      </button>

      <div className="company-hero">
        <div className="company-hero__logo">🏢</div>
        <div className="company-hero__info">
          <h1 className="company-hero__name">{company.companyName}</h1>
          <p className="company-hero__pkg">📦 Package: <strong>{company.placementPackage}</strong></p>
          <p className="company-hero__desc">{company.description}</p>
        </div>
      </div>

      <div className="company-detail-grid">
        <InfoSection icon="🛠"  title="Required Skills"      content={company.requiredSkills} />
        <InfoSection icon="💻"  title="Coding Topics"        content={company.codingTopics} />
        <InfoSection icon="📚"  title="Technical Topics"     content={company.technicalTopics} />
        <InfoSection icon="🧠"  title="Aptitude Topics"      content={company.aptitudeTopics} />
        <InfoSection icon="🎯"  title="Interview Rounds"     content={company.interviewRounds} />
      </div>
    </div>
  );
}

export default CompanyDetailPage;
