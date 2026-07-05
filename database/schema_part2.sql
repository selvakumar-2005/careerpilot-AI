-- ============================================================
-- CareerPilot AI — Part 2 Schema Additions
-- Run this AFTER schema.sql (Part 1)
-- Hibernate ddl-auto=update creates the tables automatically,
-- but this file documents the schema and can be used manually.
-- ============================================================

USE careerpilot;

-- ── resumes ──────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS resumes (
    id                 BIGINT        NOT NULL AUTO_INCREMENT,
    user_id            BIGINT        NOT NULL,
    file_name          VARCHAR(255)  NOT NULL,
    original_file_name VARCHAR(255)  NOT NULL,
    file_path          VARCHAR(500)  NOT NULL,
    file_size          BIGINT        NOT NULL,
    upload_date        DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    status             ENUM('UPLOADED','ACTIVE','DELETED') NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT pk_resumes PRIMARY KEY (id),
    CONSTRAINT fk_resume_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_resume_user_status (user_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ── coding_questions ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS coding_questions (
    id            BIGINT        NOT NULL AUTO_INCREMENT,
    title         VARCHAR(200)  NOT NULL,
    description   TEXT          NOT NULL,
    difficulty    ENUM('EASY','MEDIUM','HARD') NOT NULL,
    input_format  TEXT,
    output_format TEXT,
    constraints   TEXT,
    sample_input  TEXT,
    sample_output TEXT,
    topic         VARCHAR(100),
    CONSTRAINT pk_coding_questions PRIMARY KEY (id),
    INDEX idx_cq_difficulty (difficulty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ── coding_submissions ───────────────────────────────────────
CREATE TABLE IF NOT EXISTS coding_submissions (
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    user_id        BIGINT       NOT NULL,
    question_id    BIGINT       NOT NULL,
    submitted_code TEXT         NOT NULL,
    language       VARCHAR(30)  NOT NULL,
    status         ENUM('SUBMITTED','ACCEPTED','WRONG_ANSWER','PARTIAL') NOT NULL DEFAULT 'SUBMITTED',
    score          INT,
    attempts       INT          NOT NULL DEFAULT 1,
    submitted_at   DATETIME(6)  NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    CONSTRAINT pk_coding_submissions PRIMARY KEY (id),
    CONSTRAINT fk_sub_user     FOREIGN KEY (user_id)     REFERENCES users(id)            ON DELETE CASCADE,
    CONSTRAINT fk_sub_question FOREIGN KEY (question_id) REFERENCES coding_questions(id) ON DELETE CASCADE,
    INDEX idx_sub_user     (user_id),
    INDEX idx_sub_question (question_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ── companies ────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS companies (
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    company_name      VARCHAR(100)  NOT NULL,
    description       TEXT,
    required_skills   TEXT,
    coding_topics     TEXT,
    technical_topics  TEXT,
    aptitude_topics   TEXT,
    interview_rounds  TEXT,
    placement_package VARCHAR(100),
    CONSTRAINT pk_companies  PRIMARY KEY (id),
    CONSTRAINT uq_company_name UNIQUE (company_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ── Verify ───────────────────────────────────────────────────
-- SELECT table_name FROM information_schema.tables WHERE table_schema = 'careerpilot';
-- SELECT COUNT(*) FROM companies;
-- SELECT COUNT(*) FROM coding_questions;
