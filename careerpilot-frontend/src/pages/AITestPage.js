import React, { useState } from 'react';
import aiService from '../services/aiService';
import '../styles/ai.css';

const EXAMPLE_PROMPTS = [
  'What are the most important skills for a Java backend developer?',
  'Give me 5 tips to improve my resume for a fresher.',
  'Explain the difference between SQL and NoSQL databases.',
  'What is system design and why is it important for placements?',
];

function AITestPage() {
  const [prompt, setPrompt]       = useState('');
  const [response, setResponse]   = useState('');
  const [loading, setLoading]     = useState(false);
  const [error, setError]         = useState('');
  const [meta, setMeta]           = useState(null);     // { model, processingTimeMs }
  const [charCount, setCharCount] = useState(0);

  const MAX_CHARS = 10000;

  const handlePromptChange = (e) => {
    const val = e.target.value;
    if (val.length <= MAX_CHARS) {
      setPrompt(val);
      setCharCount(val.length);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!prompt.trim()) { setError('Please enter a prompt.'); return; }

    setLoading(true);
    setError('');
    setResponse('');
    setMeta(null);

    try {
      const res  = await aiService.test(prompt.trim());
      const data = res.data.data;               // AIResponse DTO
      setResponse(data.text);
      setMeta({ model: data.model, processingTimeMs: data.processingTimeMs });
    } catch (err) {
      const msg = err.response?.data?.message
               || err.response?.data?.error
               || 'Failed to get a response from the AI. Please check your API key and try again.';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  const handleClear = () => {
    setPrompt('');
    setResponse('');
    setError('');
    setMeta(null);
    setCharCount(0);
  };

  const handleExample = (text) => {
    setPrompt(text);
    setCharCount(text.length);
    setError('');
  };

  return (
    <div className="ai-page">

      {/* Header */}
      <div className="ai-page__header">
        <div className="ai-page__title-row">
          <span className="ai-page__icon">🤖</span>
          <div>
            <h1 className="ai-page__title">AI Test Console</h1>
            <p className="ai-page__subtitle">
              Send any prompt to Gemini and see the response. This is the foundation
              for all AI features in CareerPilot.
            </p>
          </div>
        </div>

        {/* Status badge */}
        <div className="ai-status-badge">
          <span className="ai-status-dot" />
          Gemini AI Connected
        </div>
      </div>

      {/* Example prompts */}
      <div className="ai-examples">
        <p className="ai-examples__label">Try an example:</p>
        <div className="ai-examples__list">
          {EXAMPLE_PROMPTS.map((ex) => (
            <button
              key={ex}
              className="ai-example-chip"
              onClick={() => handleExample(ex)}
              type="button"
            >
              {ex}
            </button>
          ))}
        </div>
      </div>

      {/* Main form */}
      <form className="ai-form" onSubmit={handleSubmit}>
        <div className="ai-form__textarea-wrap">
          <label htmlFor="ai-prompt" className="ai-form__label">
            Your Prompt
          </label>
          <textarea
            id="ai-prompt"
            className="ai-form__textarea"
            value={prompt}
            onChange={handlePromptChange}
            placeholder="Type anything — ask a question, describe a scenario, or paste text for analysis..."
            rows={7}
            disabled={loading}
            aria-label="AI prompt input"
          />
          <span className={`ai-char-count ${charCount > MAX_CHARS * 0.9 ? 'ai-char-count--warn' : ''}`}>
            {charCount.toLocaleString()} / {MAX_CHARS.toLocaleString()}
          </span>
        </div>

        <div className="ai-form__actions">
          <button
            type="submit"
            className="btn btn--primary btn--lg"
            disabled={loading || !prompt.trim()}
          >
            {loading ? (
              <span className="ai-thinking">
                <span className="ai-thinking__dots">
                  <span /><span /><span />
                </span>
                Asking Gemini...
              </span>
            ) : (
              <>✨ Ask Gemini</>
            )}
          </button>

          {(prompt || response) && (
            <button
              type="button"
              className="btn btn--ghost btn--lg"
              onClick={handleClear}
              disabled={loading}
            >
              🗑 Clear
            </button>
          )}
        </div>
      </form>

      {/* Error state */}
      {error && (
        <div className="ai-error" role="alert">
          <span className="ai-error__icon">⚠️</span>
          <div>
            <strong>Error</strong>
            <p>{error}</p>
          </div>
        </div>
      )}

      {/* Response */}
      {response && (
        <div className="ai-response">
          <div className="ai-response__header">
            <div className="ai-response__title-row">
              <span>🤖</span>
              <h2 className="ai-response__title">Gemini Response</h2>
            </div>
            {meta && (
              <div className="ai-response__meta">
                <span className="ai-meta-chip">Model: {meta.model}</span>
                <span className="ai-meta-chip">
                  ⚡ {meta.processingTimeMs >= 1000
                    ? `${(meta.processingTimeMs / 1000).toFixed(1)}s`
                    : `${meta.processingTimeMs}ms`}
                </span>
              </div>
            )}
          </div>

          <div className="ai-response__body">
            <ResponseText text={response} />
          </div>

          <button
            className="ai-copy-btn"
            type="button"
            onClick={() => navigator.clipboard.writeText(response)}
          >
            📋 Copy Response
          </button>
        </div>
      )}
    </div>
  );
}

/**
 * Renders the AI response text with basic markdown-like formatting:
 * - Lines starting with "1." through "99." or "- " become list items
 * - Lines starting with "**" or "#" become headings
 * - Everything else is a paragraph
 */
function ResponseText({ text }) {
  const lines = text.split('\n');

  return (
    <div className="ai-response__content">
      {lines.map((line, i) => {
        const trimmed = line.trim();
        if (!trimmed) return <div key={i} className="ai-line-break" />;

        // Heading (##, ###, bold-only line)
        if (/^#{1,3}\s/.test(trimmed)) {
          return (
            <h3 key={i} className="ai-heading">
              {trimmed.replace(/^#{1,3}\s/, '')}
            </h3>
          );
        }

        // Numbered section heading like "1. Professional Summary"
        if (/^\d+\.\s/.test(trimmed) && trimmed.length < 60) {
          return (
            <h4 key={i} className="ai-section-heading">
              {trimmed}
            </h4>
          );
        }

        // Bullet point
        if (/^[-•*]\s/.test(trimmed)) {
          return (
            <div key={i} className="ai-bullet">
              <span className="ai-bullet__dot">•</span>
              <span>{trimmed.replace(/^[-•*]\s/, '')}</span>
            </div>
          );
        }

        // Regular paragraph
        return <p key={i} className="ai-paragraph">{trimmed}</p>;
      })}
    </div>
  );
}

export default AITestPage;
