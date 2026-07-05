import React, { useCallback, useEffect, useRef, useState } from 'react';
import toast from 'react-hot-toast';
import resumeService from '../services/resumeService';
import LoadingSpinner from '../components/common/LoadingSpinner';
import '../styles/resume.css';

const MAX_SIZE_MB = 5;
const MAX_BYTES   = MAX_SIZE_MB * 1024 * 1024;

function formatSize(bytes) {
  if (!bytes) return '0 B';
  if (bytes < 1024) return `${bytes} B`;
  if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
  return `${(bytes / (1024 * 1024)).toFixed(2)} MB`;
}

function ResumeCard({ resume, onDelete, onDownload }) {
  const [deleting, setDeleting] = useState(false);
  const [downloading, setDownloading] = useState(false);

  const handleDelete = async () => {
    if (!window.confirm('Delete this resume?')) return;
    setDeleting(true);
    try {
      await onDelete(resume.id);
      toast.success('Resume deleted');
    } catch {
      toast.error('Failed to delete');
    } finally {
      setDeleting(false);
    }
  };

  const handleDownload = async () => {
    setDownloading(true);
    try {
      await onDownload(resume.id, resume.originalFileName);
    } catch {
      toast.error('Download failed');
    } finally {
      setDownloading(false);
    }
  };

  return (
    <div className="resume-card">
      <div className="resume-card__icon">📄</div>
      <div className="resume-card__info">
        <p className="resume-card__name">{resume.originalFileName}</p>
        <p className="resume-card__meta">
          {formatSize(resume.fileSize)} &middot;{' '}
          {resume.uploadDate ? new Date(resume.uploadDate).toLocaleDateString('en-IN', {
            day: '2-digit', month: 'short', year: 'numeric',
          }) : '—'}
        </p>
        <span className={`resume-card__status status-badge status-badge--${resume.status?.toLowerCase()}`}>
          {resume.status}
        </span>
      </div>
      <div className="resume-card__actions">
        <button
          className="btn btn--outline btn--sm"
          onClick={handleDownload}
          disabled={downloading}
        >
          {downloading ? '...' : '⬇ Download'}
        </button>
        <button
          className="btn btn--ghost btn--sm btn--danger"
          onClick={handleDelete}
          disabled={deleting}
        >
          {deleting ? '...' : '🗑 Delete'}
        </button>
      </div>
    </div>
  );
}

function ResumePage() {
  const [resumes, setResumes]     = useState([]);
  const [loading, setLoading]     = useState(true);
  const [uploading, setUploading] = useState(false);
  const [dragOver, setDragOver]   = useState(false);
  const fileInputRef = useRef(null);

  const loadResumes = useCallback(() => {
    setLoading(true);
    resumeService.getMyResumes()
      .then((res) => setResumes(res.data.data || []))
      .catch(() => toast.error('Failed to load resumes'))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => { loadResumes(); }, [loadResumes]);

  const uploadFile = async (file) => {
    if (!file) return;
    if (file.type !== 'application/pdf') {
      toast.error('Only PDF files are allowed');
      return;
    }
    if (file.size > MAX_BYTES) {
      toast.error(`File must be under ${MAX_SIZE_MB} MB`);
      return;
    }
    setUploading(true);
    try {
      await resumeService.upload(file);
      toast.success('Resume uploaded successfully!');
      loadResumes();
    } catch (err) {
      toast.error(err.response?.data?.message || 'Upload failed');
    } finally {
      setUploading(false);
    }
  };

  const handleFileChange = (e) => uploadFile(e.target.files[0]);

  const handleDrop = (e) => {
    e.preventDefault();
    setDragOver(false);
    uploadFile(e.dataTransfer.files[0]);
  };

  const handleDelete = async (id) => {
    await resumeService.delete(id);
    setResumes((prev) => prev.filter((r) => r.id !== id));
  };

  const handleDownload = (id, name) => resumeService.download(id, name);

  return (
    <div className="resume-page">
      <div className="page-header">
        <h1 className="page-title">📄 Resume Management</h1>
        <p className="page-subtitle">Upload and manage your resumes. PDF only, max 5 MB.</p>
      </div>

      {/* Upload zone */}
      <div
        className={`upload-zone ${dragOver ? 'upload-zone--active' : ''} ${uploading ? 'upload-zone--loading' : ''}`}
        onDragOver={(e) => { e.preventDefault(); setDragOver(true); }}
        onDragLeave={() => setDragOver(false)}
        onDrop={handleDrop}
        onClick={() => !uploading && fileInputRef.current?.click()}
        role="button"
        tabIndex={0}
        aria-label="Upload resume area"
        onKeyDown={(e) => e.key === 'Enter' && fileInputRef.current?.click()}
      >
        <input
          ref={fileInputRef}
          type="file"
          accept="application/pdf"
          onChange={handleFileChange}
          style={{ display: 'none' }}
          aria-hidden="true"
        />
        {uploading ? (
          <LoadingSpinner size="md" text="Uploading..." />
        ) : (
          <>
            <div className="upload-zone__icon">☁️</div>
            <p className="upload-zone__title">
              {dragOver ? 'Drop your PDF here' : 'Drag & drop your PDF or click to browse'}
            </p>
            <p className="upload-zone__hint">PDF only · Maximum 5 MB</p>
            <button className="btn btn--primary btn--md" tabIndex={-1}>
              Choose File
            </button>
          </>
        )}
      </div>

      {/* Resume list */}
      <div className="resume-list-section">
        <h2 className="section-label">
          My Resumes{' '}
          <span className="section-label__count">{resumes.length}</span>
        </h2>

        {loading ? (
          <div className="center-spinner"><LoadingSpinner size="md" /></div>
        ) : resumes.length === 0 ? (
          <div className="empty-state">
            <span className="empty-state__icon">📭</span>
            <p>No resumes uploaded yet. Upload your first one above.</p>
          </div>
        ) : (
          <div className="resume-list">
            {resumes.map((r) => (
              <ResumeCard
                key={r.id}
                resume={r}
                onDelete={handleDelete}
                onDownload={handleDownload}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default ResumePage;
