# CareerPilot AI — API Documentation (Part 1)

**Base URL:** `http://localhost:8080`
**Content-Type:** `application/json`
**Authentication:** Bearer JWT in `Authorization` header

---

## Response Envelope

Every endpoint returns this structure:

```json
{
  "success":   true | false,
  "message":   "Human readable message",
  "data":      { ... },
  "timestamp": "2024-07-04T10:30:00"
}
```

`data` is omitted when there is no payload. On validation failure, `data` is a map of field → error message.

---

## Auth Endpoints

### POST /api/auth/register

Register a new student account.

**Request Body:**
```json
{
  "fullName":        "Jane Doe",
  "email":           "jane@example.com",
  "password":        "Secret@123",
  "confirmPassword": "Secret@123"
}
```

**Validation Rules:**
| Field           | Rule |
|-----------------|------|
| fullName        | Required, 2–100 chars |
| email           | Required, valid format, must be unique |
| password        | Min 8 chars, uppercase, lowercase, digit, special char |
| confirmPassword | Must match password |

**Success Response — 201 Created:**
```json
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "token":     "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "user": {
      "id":        1,
      "fullName":  "Jane Doe",
      "email":     "jane@example.com",
      "role":      "STUDENT",
      "createdAt": "2024-07-04T10:00:00"
    }
  },
  "timestamp": "2024-07-04T10:00:00"
}
```

**Error — 400 Validation Failed:**
```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "email":    "Please provide a valid email address",
    "password": "Password must be at least 8 characters..."
  },
  "timestamp": "2024-07-04T10:00:00"
}
```

**Error — 409 Email Exists:**
```json
{
  "success": false,
  "message": "An account with email 'jane@example.com' already exists",
  "timestamp": "2024-07-04T10:00:00"
}
```

---

### POST /api/auth/login

Authenticate and receive a JWT.

**Request Body:**
```json
{
  "email":    "jane@example.com",
  "password": "Secret@123"
}
```

**Success Response — 200 OK:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token":     "eyJhbGciOiJIUzI1NiJ9...",
    "tokenType": "Bearer",
    "user": {
      "id":        1,
      "fullName":  "Jane Doe",
      "email":     "jane@example.com",
      "role":      "STUDENT",
      "createdAt": "2024-07-04T10:00:00"
    }
  },
  "timestamp": "2024-07-04T10:00:00"
}
```

**Error — 401 Bad Credentials:**
```json
{
  "success": false,
  "message": "Invalid email or password",
  "timestamp": "2024-07-04T10:00:00"
}
```

---

### POST /api/auth/logout

Stateless logout. The server has no session to invalidate — the client must delete the JWT from storage.

**Success Response — 200 OK:**
```json
{
  "success": true,
  "message": "Logged out successfully",
  "timestamp": "2024-07-04T10:00:00"
}
```

---

## User Endpoints

All user endpoints require a valid JWT.

**Header required:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

### GET /api/users/me

Returns the profile of the currently authenticated user.

**Success Response — 200 OK:**
```json
{
  "success": true,
  "message": "User profile retrieved",
  "data": {
    "id":        1,
    "fullName":  "Jane Doe",
    "email":     "jane@example.com",
    "role":      "STUDENT",
    "createdAt": "2024-07-04T10:00:00"
  },
  "timestamp": "2024-07-04T10:00:00"
}
```

**Error — 401 No Token:**
```json
{
  "success": false,
  "message": "Access denied. Please log in to continue.",
  "timestamp": "2024-07-04T10:00:00"
}
```

---

### GET /api/users/{id}

Returns a user by ID.

**Path Parameter:** `id` — Long

**Success Response — 200 OK:**
```json
{
  "success": true,
  "message": "User retrieved",
  "data": {
    "id":        1,
    "fullName":  "Jane Doe",
    "email":     "jane@example.com",
    "role":      "STUDENT",
    "createdAt": "2024-07-04T10:00:00"
  },
  "timestamp": "2024-07-04T10:00:00"
}
```

**Error — 404 Not Found:**
```json
{
  "success": false,
  "message": "User not found with id: '99'",
  "timestamp": "2024-07-04T10:00:00"
}
```

---

## JWT Details

| Property | Value |
|----------|-------|
| Algorithm | HS256 (HMAC-SHA256) |
| Expiry | 24 hours (86400000 ms) |
| Header | `Authorization: Bearer <token>` |
| Claims | subject (email), userId, role, iat, exp |

**Token flow:**
1. Client calls `/api/auth/login` or `/api/auth/register`
2. Server returns JWT in response body
3. Client stores JWT in `localStorage`
4. Client sends `Authorization: Bearer <token>` on every subsequent request
5. `JwtAuthenticationFilter` validates and sets `SecurityContext`
6. On logout, client removes token from `localStorage`

---

## HTTP Status Code Reference

| Code | Meaning |
|------|---------|
| 200 | Success |
| 201 | Created (registration) |
| 400 | Bad request / validation error |
| 401 | Unauthenticated |
| 403 | Forbidden (wrong role) |
| 404 | Resource not found |
| 409 | Conflict (duplicate email) |
| 500 | Internal server error |
