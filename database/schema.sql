-- ============================================================
-- CareerPilot AI — MySQL Database Schema
-- Version : 1.0.0  (Part 1)
-- Engine  : MySQL 8.0+
-- Charset : utf8mb4 (full Unicode + emoji support)
-- ============================================================

-- ── Step 1: Create and select the database ──────────────────
CREATE DATABASE IF NOT EXISTS careerpilot
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE careerpilot;

-- ── Step 2: Create dedicated application user ───────────────
-- Run this block once as a MySQL root / admin user.
-- The user is granted only the permissions the app actually needs.
CREATE USER IF NOT EXISTS 'careerpilot_user'@'localhost'
  IDENTIFIED BY 'CareerPilot@2024';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP
  ON careerpilot.*
  TO 'careerpilot_user'@'localhost';

FLUSH PRIVILEGES;

-- ── Step 3: users table ─────────────────────────────────────
CREATE TABLE IF NOT EXISTS users (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    full_name   VARCHAR(100)    NOT NULL,
    email       VARCHAR(150)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,   -- BCrypt hash (60 chars, padded to 255 for safety)
    role        ENUM('STUDENT', 'ADMIN')
                                NOT NULL
                                DEFAULT 'STUDENT',
    created_at  DATETIME(6)     NOT NULL    DEFAULT CURRENT_TIMESTAMP(6),
    updated_at  DATETIME(6)                 DEFAULT CURRENT_TIMESTAMP(6)
                                            ON UPDATE CURRENT_TIMESTAMP(6),

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci
  COMMENT='Registered platform users — passwords stored as BCrypt hashes';

-- ── Indexes ─────────────────────────────────────────────────
-- email is already covered by the UNIQUE constraint index.
-- Additional index on role for future admin queries.
CREATE INDEX idx_users_role ON users (role);

-- ── Step 4: Seed data (development only) ────────────────────
-- Password for both accounts below is: Admin@1234
-- BCrypt hash generated with strength 12.
-- REMOVE or replace before deploying to production.

INSERT IGNORE INTO users (full_name, email, password, role) VALUES
(
  'Admin User',
  'admin@careerpilot.com',
  '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj2l1HxN8Ogi',
  'ADMIN'
),
(
  'Test Student',
  'student@careerpilot.com',
  '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewdBPj2l1HxN8Ogi',
  'STUDENT'
);

-- ── Verification queries (run manually to confirm) ──────────
-- SELECT id, full_name, email, role, created_at FROM users;
-- SELECT COUNT(*) AS total_users FROM users;
