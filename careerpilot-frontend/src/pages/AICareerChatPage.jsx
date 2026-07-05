import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import '../styles/AIChatPage.css';

const AICareerChatPage = () => {
  const [messages, setMessages] = useState([]);
  const [inputMessage, setInputMessage] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const messagesEndRef = useRef(null);

  useEffect(() => {
    fetchChatHistory();
  }, []);

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  const fetchChatHistory = async () => {
    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.get(
        'http://localhost:8080/api/ai/chat/history',
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setMessages(res.data.data || []);
    } catch (err) {
      console.error('Error fetching chat history:', err);
    }
  };

  const handleSendMessage = async () => {
    if (!inputMessage.trim()) {
      setError('Please enter a message');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const token = localStorage.getItem('careerpilot_token');
      const res = await axios.post(
        'http://localhost:8080/api/ai/chat/message',
        { message: inputMessage },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      setMessages((prev) => [res.data.data, ...prev]);
      setInputMessage('');
    } catch (err) {
      setError(err.response?.data?.message || 'Error sending message');
    } finally {
      setLoading(false);
    }
  };

  const handleClearChat = async () => {
    if (!window.confirm('Are you sure you want to clear all chat history?')) {
      return;
    }

    try {
      const token = localStorage.getItem('careerpilot_token');
      await axios.delete(
        'http://localhost:8080/api/ai/chat/clear',
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setMessages([]);
    } catch (err) {
      setError('Error clearing chat history');
    }
  };

  const copyToClipboard = (text) => {
    navigator.clipboard.writeText(text);
  };

  return (
    <div className="chat-page-container">
      <div className="chat-header">
        <h1>💬 AI Career Chatbot</h1>
        <p>Ask anything about placement preparation, career guidance, and more!</p>
      </div>

      <div className="chat-container">
        <div className="messages-container">
          {messages.length === 0 ? (
            <div className="empty-state">
              <p>👋 Hi! I'm CareerPilot AI Assistant. How can I help you today?</p>
              <p className="subtitle">Ask me about career guidance, interview prep, resume tips, coding help, or anything else!</p>
            </div>
          ) : (
            <>
              {messages.map((msg, index) => (
                <div key={index} className="message-group">
                  <div className="user-message">
                    <div className="message-content">{msg.user_message}</div>
                  </div>
                  <div className="ai-message">
                    <div className="message-content">
                      {msg.response}
                      <button
                        className="copy-btn"
                        onClick={() => copyToClipboard(msg.response)}
                        title="Copy message"
                      >
                        📋
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </>
          )}
          <div ref={messagesEndRef} />
        </div>

        {error && <div className="error-message">{error}</div>}

        <div className="input-section">
          <div className="input-wrapper">
            <textarea
              value={inputMessage}
              onChange={(e) => setInputMessage(e.target.value)}
              onKeyPress={(e) => {
                if (e.key === 'Enter' && !e.shiftKey) {
                  e.preventDefault();
                  handleSendMessage();
                }
              }}
              placeholder="Type your message here... (Shift+Enter for new line)"
              rows={3}
              disabled={loading}
            />
            <button
              className="send-btn"
              onClick={handleSendMessage}
              disabled={loading}
            >
              {loading ? '⏳ Sending...' : '✈️ Send'}
            </button>
          </div>

          <button className="clear-btn" onClick={handleClearChat}>
            🗑️ Clear Chat
          </button>
        </div>
      </div>
    </div>
  );
};

export default AICareerChatPage;
