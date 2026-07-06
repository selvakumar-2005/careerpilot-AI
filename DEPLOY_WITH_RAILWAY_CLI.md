# Deploy CareerPilot AI Using Railway CLI

This guide is for users who prefer command-line deployment.

---

## Prerequisites

- Railway account (https://railway.app)
- Node.js installed (for Railway CLI)
- Git installed

---

## Installation

### Step 1: Install Railway CLI

```bash
npm install -g @railway/cli
```

Verify installation:
```bash
railway --version
```

Should output version number.

---

## Deployment Steps

### Step 2: Login to Railway

```bash
railway login
```

This opens a browser for authentication. Complete login, then return to terminal.

### Step 3: Navigate to Project

```bash
cd c:\Users\Selva\Desktop\projectjul03
```

### Step 4: Initialize Railway Project

```bash
railway init
```

Railway asks:
- "Create new project?" → Yes
- "Project name?" → `careerpilot-ai`
- "Environment to use?" → Leave default

### Step 5: Add Services

#### Add Backend Service

```bash
railway service add
```

Select:
- Template: "Spring Boot" (if available) or "Custom"
- Name: `careerpilot-backend`
- Directory: `careerpilot-backend`

#### Add Frontend Service

```bash
railway service add
```

Select:
- Template: "Node.js"
- Name: `careerpilot-frontend`
- Directory: `careerpilot-frontend`

#### Add MySQL Database

```bash
railway service add
```

Select:
- Template: "MySQL"
- Name: `careerpilot-db`

### Step 6: Set Environment Variables

#### For Backend

```bash
railway variables set SPRING_PROFILES_ACTIVE prod --service careerpilot-backend
railway variables set GEMINI_API_KEY "YOUR_ACTUAL_KEY_HERE" --service careerpilot-backend
railway variables set GROQ_API_KEY "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" --service careerpilot-backend
railway variables set JWT_SECRET "Y2FyZWVycGlsb3RhaXNlY3JldGtleWZvcmp3dGF1dGhlbnRpY2F0aW9uMjAyNHZlcnkgc2VjdXJlIGtleQ==" --service careerpilot-backend
railway variables set CORS_ALLOWED_ORIGINS "http://localhost:3000" --service careerpilot-backend
```

#### For Frontend

```bash
railway variables set REACT_APP_API_URL "https://careerpilot-backend-prod.railway.app" --service careerpilot-frontend
railway variables set REACT_APP_ENV production --service careerpilot-frontend
railway variables set NODE_ENV production --service careerpilot-frontend
```

(Replace `careerpilot-backend-prod` with your actual backend URL from Railway)

### Step 7: Deploy

```bash
railway up
```

Railway will:
1. Build backend JAR
2. Deploy backend service
3. Build frontend bundle
4. Deploy frontend service
5. Set up database connections

Deployment takes 5-10 minutes.

### Step 8: Get Service URLs

```bash
railway domains
```

Or visit Railway dashboard for URLs.

---

## Useful Railway CLI Commands

```bash
# View project status
railway status

# View logs for a service
railway logs --service careerpilot-backend
railway logs --service careerpilot-frontend

# View environment variables
railway variables --service careerpilot-backend

# Update a variable
railway variables set KEY "VALUE" --service careerpilot-backend

# Restart a service
railway redeploy --service careerpilot-backend

# View deployment history
railway deployments

# Open Railway dashboard
railway open

# Link local project to existing Railway project
railway link
```

---

## Troubleshooting

### CLI authentication fails

```bash
railway login --force
```

### Service won't deploy

Check logs:
```bash
railway logs --service careerpilot-backend
```

### Database not connecting

Verify MySQL service is running:
```bash
railway logs --service careerpilot-db
```

Check `DATABASE_URL` is set:
```bash
railway variables --service careerpilot-backend
```

---

## Next Steps

1. Verify deployment with `railway status`
2. Get URLs from `railway domains`
3. Test backend: `curl https://[backend-url]/api/health`
4. Test frontend: Visit `https://[frontend-url]`
5. Share URLs with project reviewers

---

For more info: https://docs.railway.app/reference/cli
