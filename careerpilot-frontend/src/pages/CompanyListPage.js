import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import toast from 'react-hot-toast';
import companyService from '../services/companyService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import '../styles/company.css';

const COMPANY_ICONS = {
  Wipro: '🔵', Infosys: '🟣', TCS: '🔷', Zoho: '🟠',
  Cognizant: '🟡', Capgemini: '🔴', Accenture: '💜',
};

function CompanyCard({ company }) {
  const navigate = useNavigate();
  const icon = COMPANY_ICONS[company.companyName] || '🏢';

  return (
    <div
      className="company-card"
      onClick={() => navigate(`/companies/${company.id}`)}
      role="button"
      tabIndex={0}
      onKeyDown={(e) => e.key === 'Enter' && navigate(`/companies/${company.id}`)}
    >
      <div className="company-card__logo">{icon}</div>
      <div className="company-card__body">
        <h3 className="company-card__name">{company.companyName}</h3>
        <p className="company-card__pkg">📦 {company.placementPackage}</p>
        <p className="company-card__desc">
          {company.description?.slice(0, 80)}...
        </p>
      </div>
      <div className="company-card__footer">
        <span className="company-card__action">View Details →</span>
      </div>
    </div>
  );
}

function CompanyListPage() {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading]     = useState(true);
  const [search, setSearch]       = useState('');

  useEffect(() => {
    companyService.getAll()
      .then((res) => setCompanies(res.data.data || []))
      .catch(() => toast.error('Failed to load companies'))
      .finally(() => setLoading(false));
  }, []);

  const filtered = companies.filter((c) =>
    c.companyName.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="company-page">
      <div className="page-header">
        <h1 className="page-title">🏢 Company Preparation</h1>
        <p className="page-subtitle">
          Explore company-specific requirements, topics, and interview processes.
        </p>
      </div>

      <div className="company-filters">
        <input
          type="text"
          className="search-input"
          placeholder="Search companies..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          aria-label="Search companies"
        />
      </div>

      {loading ? (
        <div className="center-spinner"><LoadingSpinner size="md" /></div>
      ) : filtered.length === 0 ? (
        <div className="empty-state">
          <span className="empty-state__icon">🔍</span>
          <p>No companies found.</p>
        </div>
      ) : (
        <div className="company-grid">
          {filtered.map((c) => (
            <CompanyCard key={c.id} company={c} />
          ))}
        </div>
      )}
    </div>
  );
}

export default CompanyListPage;
