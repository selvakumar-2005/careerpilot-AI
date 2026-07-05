import React, { useState } from 'react';
import axios from 'axios';
import '../styles/AIPages.css';

const AICareerGuidancePage = () => {
  const [activeTab, setActiveTab] = useState('general');
  const [formData, setFormData] = useState({
    currentSkills: '',
    currentSemester: '',
    targetCompany: '',
    targetRole: '',
    currentCGPA: '',
  });
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState(null);
  const [error, setError] = useState('');

  const [personalizedRecommendations, setPersonalizedRecommendations] = useState(null);
  const [industryGuidance, setIndustryGuidance] = useState(null);
  const [learningRoadmap, setLearningRoadmap] = useState(null);
  const [certifications, setCertifications] = useState(null);

  const token = localStorage.getItem('careerpilot_token');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleGetGuidance = async () => {
    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/career/guidance',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setResponse(res.data.data);
      setActiveTab('general');
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting career guidance');
    } finally {
      setLoading(false);
    }
  };

  const handlePersonalizedRecommendations = async () => {
    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/career/personalized-recommendations',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setPersonalizedRecommendations(res.data);
      setActiveTab('personalized');
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting personalized recommendations');
    } finally {
      setLoading(false);
    }
  };

  const handleIndustryGuidance = async () => {
    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/career/industry-guidance',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setIndustryGuidance(res.data);
      setActiveTab('industry');
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting industry guidance');
    } finally {
      setLoading(false);
    }
  };

  const handleLearningRoadmap = async () => {
    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/career/learning-roadmap',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setLearningRoadmap(res.data);
      setActiveTab('roadmap');
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting learning roadmap');
    } finally {
      setLoading(false);
    }
  };

  const handleCertifications = async () => {
    setLoading(true);
    setError('');

    try {
      const res = await axios.post(
        'http://localhost:8080/api/ai/career/certifications',
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setCertifications(res.data);
      setActiveTab('certifications');
    } catch (err) {
      setError(err.response?.data?.message || 'Error getting certifications');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ai-page-container">
      <div className="ai-header">
        <h1>🚀 AI Career Guidance</h1>
        <p>Get personalized career roadmap and skill development plan</p>
      </div>

      <div className="ai-input-section">
        <div className="input-row">
          <div className="input-group">
            <label>Current Skills</label>
            <input
              type="text"
              name="currentSkills"
              value={formData.currentSkills}
              onChange={handleChange}
              placeholder="e.g., Java, Python, SQL, React"
            />
          </div>

          <div className="input-group">
            <label>Current Semester/Year</label>
            <input
              type="text"
              name="currentSemester"
              value={formData.currentSemester}
              onChange={handleChange}
              placeholder="e.g., 6th Sem, 2nd Year"
            />
          </div>
        </div>

        <div className="input-row">
          <div className="input-group">
            <label>Target Company</label>
            <input
              type="text"
              name="targetCompany"
              value={formData.targetCompany}
              onChange={handleChange}
              placeholder="e.g., Google, Microsoft, Amazon"
            />
          </div>

          <div className="input-group">
            <label>Target Role</label>
            <input
              type="text"
              name="targetRole"
              value={formData.targetRole}
              onChange={handleChange}
              placeholder="e.g., Software Engineer, Full Stack Developer"
            />
          </div>
        </div>

        <div className="input-row">
          <div className="input-group">
            <label>Current CGPA</label>
            <input
              type="number"
              name="currentCGPA"
              value={formData.currentCGPA}
              onChange={handleChange}
              placeholder="e.g., 8.5"
              step="0.1"
            />
          </div>
        </div>

        <div className="button-group">
          <button
            className="analyze-btn"
            onClick={handleGetGuidance}
            disabled={loading}
          >
            {loading ? 'Generating...' : 'Get Career Guidance'}
          </button>

          <button
            className="analyze-btn secondary"
            onClick={handlePersonalizedRecommendations}
            disabled={loading}
          >
            {loading ? 'Loading...' : 'Personalized Recommendations'}
          </button>

          <button
            className="analyze-btn secondary"
            onClick={handleIndustryGuidance}
            disabled={loading}
          >
            {loading ? 'Loading...' : 'Industry Guidance'}
          </button>

          <button
            className="analyze-btn secondary"
            onClick={handleLearningRoadmap}
            disabled={loading}
          >
            {loading ? 'Loading...' : 'Learning Roadmap'}
          </button>

          <button
            className="analyze-btn secondary"
            onClick={handleCertifications}
            disabled={loading}
          >
            {loading ? 'Loading...' : 'Certifications'}
          </button>
        </div>

        {error && <div className="error-message">{error}</div>}
      </div>

      <div className="tabs-container">
        {response && activeTab === 'general' && (
          <div className="ai-results-section">
            <div className="feedback-card">
              <h3>📊 Future Career Path</h3>
              <p>{response.future_career_path}</p>
            </div>

            <div className="feedback-card">
              <h3>⏱️ Timeline</h3>
              <p>{response.timeline}</p>
            </div>

            {response.suitable_job_roles && response.suitable_job_roles.length > 0 && (
              <div className="feedback-card">
                <h3>💼 Suitable Job Roles</h3>
                <ul>
                  {response.suitable_job_roles.map((role, i) => (
                    <li key={i}>{role}</li>
                  ))}
                </ul>
              </div>
            )}

            {response.skill_gap && (
              <div className="feedback-card">
                <h3>📚 Skill Gap Analysis</h3>
                <p>{response.skill_gap}</p>
              </div>
            )}

            {response.learning_roadmap && response.learning_roadmap.length > 0 && (
              <div className="feedback-card">
                <h3>🗺️ Learning Roadmap</h3>
                <ol>
                  {response.learning_roadmap.map((step, i) => (
                    <li key={i}>{step}</li>
                  ))}
                </ol>
              </div>
            )}

            {response.recommended_courses && response.recommended_courses.length > 0 && (
              <div className="feedback-card">
                <h3>🎓 Recommended Courses</h3>
                <ul>
                  {response.recommended_courses.map((course, i) => (
                    <li key={i}>{course}</li>
                  ))}
                </ul>
              </div>
            )}

            {response.project_ideas && response.project_ideas.length > 0 && (
              <div className="feedback-card">
                <h3>💡 Project Ideas</h3>
                <ul>
                  {response.project_ideas.map((project, i) => (
                    <li key={i}>{project}</li>
                  ))}
                </ul>
              </div>
            )}

            {response.certification_suggestions && response.certification_suggestions.length > 0 && (
              <div className="feedback-card">
                <h3>🏆 Certification Suggestions</h3>
                <ul>
                  {response.certification_suggestions.map((cert, i) => (
                    <li key={i}>{cert}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}

        {personalizedRecommendations && activeTab === 'personalized' && (
          <div className="ai-results-section">
            <div className="feedback-card">
              <h3>🎯 Personalized Career Recommendations</h3>
              {personalizedRecommendations.recommendations && personalizedRecommendations.recommendations.length > 0 && (
                <ul>
                  {personalizedRecommendations.recommendations.map((rec, i) => (
                    <li key={i}>{rec}</li>
                  ))}
                </ul>
              )}
              {personalizedRecommendations.recommendation_summary && (
                <p className="card-description">{personalizedRecommendations.recommendation_summary}</p>
              )}
            </div>

            {personalizedRecommendations.suitable_job_roles && personalizedRecommendations.suitable_job_roles.length > 0 && (
              <div className="feedback-card">
                <h3>💼 Suitable Job Roles</h3>
                <ul>
                  {personalizedRecommendations.suitable_job_roles.map((role, i) => (
                    <li key={i}>{role}</li>
                  ))}
                </ul>
              </div>
            )}

            {personalizedRecommendations.recommended_technologies && personalizedRecommendations.recommended_technologies.length > 0 && (
              <div className="feedback-card">
                <h3>🛠️ Recommended Technologies</h3>
                <ul>
                  {personalizedRecommendations.recommended_technologies.map((tech, i) => (
                    <li key={i}>{tech}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}

        {industryGuidance && activeTab === 'industry' && (
          <div className="ai-results-section">
            <div className="feedback-card">
              <h3>🏢 Industry-Specific Career Suggestions</h3>
              {industryGuidance.industry_suggestions && industryGuidance.industry_suggestions.length > 0 && (
                <ul>
                  {industryGuidance.industry_suggestions.map((suggestion, i) => (
                    <li key={i}>{suggestion}</li>
                  ))}
                </ul>
              )}
            </div>

            {industryGuidance.industry_trends && (
              <div className="feedback-card">
                <h3>📈 Industry Trends</h3>
                <p>{industryGuidance.industry_trends}</p>
              </div>
            )}

            {industryGuidance.market_opportunities && industryGuidance.market_opportunities.length > 0 && (
              <div className="feedback-card">
                <h3>🚀 Market Opportunities</h3>
                <ul>
                  {industryGuidance.market_opportunities.map((opp, i) => (
                    <li key={i}>{opp}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}

        {learningRoadmap && activeTab === 'roadmap' && (
          <div className="ai-results-section">
            <div className="feedback-card">
              <h3>🗺️ Personalized Learning Roadmap</h3>
              {learningRoadmap.roadmap_phases && learningRoadmap.roadmap_phases.length > 0 && (
                <div>
                  <h4>Learning Phases:</h4>
                  <ol>
                    {learningRoadmap.roadmap_phases.map((phase, i) => (
                      <li key={i}>{phase}</li>
                    ))}
                  </ol>
                </div>
              )}
            </div>

            {learningRoadmap.milestone_timeline && (
              <div className="feedback-card">
                <h3>📋 Milestone Timeline</h3>
                <p>{learningRoadmap.milestone_timeline}</p>
              </div>
            )}

            {learningRoadmap.learning_resources && learningRoadmap.learning_resources.length > 0 && (
              <div className="feedback-card">
                <h3>📚 Learning Resources</h3>
                <ul>
                  {learningRoadmap.learning_resources.map((resource, i) => (
                    <li key={i}>{resource}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}

        {certifications && activeTab === 'certifications' && (
          <div className="ai-results-section">
            <div className="feedback-card">
              <h3>🏆 Recommended Certifications</h3>
              {certifications.certifications && certifications.certifications.length > 0 && (
                <ul>
                  {certifications.certifications.map((cert, i) => (
                    <li key={i}>{cert}</li>
                  ))}
                </ul>
              )}
            </div>

            {certifications.certification_benefits && (
              <div className="feedback-card">
                <h3>✨ Certification Benefits</h3>
                <p>{certifications.certification_benefits}</p>
              </div>
            )}

            {certifications.preparation_timeline && (
              <div className="feedback-card">
                <h3>⏱️ Preparation Timeline</h3>
                <p>{certifications.preparation_timeline}</p>
              </div>
            )}

            {certifications.study_resources && certifications.study_resources.length > 0 && (
              <div className="feedback-card">
                <h3>📖 Study Resources</h3>
                <ul>
                  {certifications.study_resources.map((resource, i) => (
                    <li key={i}>{resource}</li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default AICareerGuidancePage;
