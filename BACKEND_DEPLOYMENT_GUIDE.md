# CareerPilot Backend - Railway Deployment Guide

## ✅ What's Been Done

- [x] Production configuration created (`application-prod.properties`)
- [x] Railway config file created (`railway.json`)
- [x] Procfile created for deployment
- [x] Production JAR built (`careerpilot-backend-1.0.0.jar`)
- [x] Code pushed to GitHub

---

## 🚀 Deployment Steps

### Step 1: Sign Up for Railway (Free)

1. Go to https://railway.app
2. Click "Start Project"
3. Sign up with GitHub (recommended - easier for auto-deploy)
4. Accept repository access

---

### Step 2: Create New Project on Railway

1. Click "Create a New Project"
2. Select "Deploy from GitHub repo"
3. Search for: `selvakumar-2005/careerpilot-AI`
4. Click "Deploy"

Railway will automatically detect this is a Spring Boot project.

---

### Step 3: Configure MySQL Database

1. In Railway dashboard, click **Add Service**
2. Select **MySQL**
3. Name it: `careerpilot-db`
4. Click **Deploy**

Railway will:
- Create managed MySQL database
- Generate credentials automatically
- Inject `DATABASE_URL` environment variable
- Configure connection pooling

---

### Step 4: Set Environment Variables

In Railway dashboard, go to your backend service → **Variables**

Add these (most are already in `application-prod.properties` with defaults):

```
SPRING_PROFILES_ACTIVE=prod

# Gemini API (REQUIRED - Get from https://aistudio.google.com/apikey)
GEMINI_API_KEY=YOUR_ACTUAL_GEMINI_KEY_HERE

# Groq API (Already set with free tier key)
GROQ_API_KEY=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

# JWT Secret (Change in production for security)
JWT_SECRET=Y2FyZWVycGlsb3RhaXNlY3JldGtleWZvcmp3dGF1dGhlbnRpY2F0aW9uMjAyNHZlcnkgc2VjdXJlIGtleQ==

# Database (AUTO-INJECTED by Railway from MySQL service)
# DATABASE_URL=mysql://user:pass@host:port/database
# (You don't need to set this - Railway does it)

# CORS (Add frontend URL when deployed)
CORS_ALLOWED_ORIGINS=http://localhost:3000,https://careerpilot-frontend.railway.app
```

---

### Step 5: Configure Railway to Use Production Configs

Railway automatically detects Spring Boot apps. To ensure it uses `application-prod.properties`:

1. Railway dashboard → Backend service
2. Click **Settings**
3. Under **Build**, verify:
   - Builder: `NIXPACKS` (auto-selected)
   - Build command: (leave empty - Maven auto-detected)

---

### Step 6: Manual Trigger or Wait for Deploy

**Option A: Automatic (Recommended)**
- Railway watches your GitHub repo
- Every push to `master` auto-deploys
- Takes ~3-5 minutes

**Option B: Manual Trigger**
1. Go to backend service
2. Click **Deployments**
3. Click **Deploy** (top right)

---

### Step 7: Verify Deployment

1. Check Railway dashboard for green status
2. Copy the backend public URL (format: `https://careerpilot-backend-prod.railway.app`)
3. Test endpoint:
   ```bash
   curl https://careerpilot-backend-prod.railway.app/api/health
   ```
   Expected: `200 OK` response

---

## 📊 What Railway Provides

| Service | Details |
|---------|---------|
| **Backend** | Spring Boot Java app on Ubuntu container |
| **Database** | MySQL 8.0 with automatic backups |
| **Networking** | Public HTTPS URL with SSL |
| **Monitoring** | Logs, metrics, deployment history |
| **Storage** | Ephemeral filesystem (file uploads lost on redeploy) |

---

## 💰 Estimated Monthly Cost

| Service | Cost |
|---------|------|
| Backend compute | $5 (free tier with $5 credits) |
| MySQL database | Free (included) |
| HTTPS/SSL | Free (included) |
| **Total** | **~$0-5/month** |

Railway gives $5 free credits monthly - likely covers this project entirely.

---

## 🔐 Security Notes

**⚠️ BEFORE PRODUCTION:**

1. **Change JWT Secret:**
   - Generate new one: `openssl rand -base64 64`
   - Replace in RAILWAY variables

2. **Secure Gemini API Key:**
   - Never commit to GitHub
   - Only set in Railway dashboard
   - Rotate if exposed

3. **Database Password:**
   - Railway auto-generates secure password
   - Never change it in Railway (auto-managed)

4. **CORS Configuration:**
   - Update `CORS_ALLOWED_ORIGINS` with actual frontend URL
   - Never use `*` in production

---

## 🐛 Troubleshooting

### Issue: Build fails
- Check Railway build logs: Dashboard → Deployments → View logs
- Ensure Java 21 compatible code
- Check pom.xml for errors

### Issue: Database connection fails
- Verify MySQL service is running (green status in Railway)
- Check `DATABASE_URL` in Variables
- Ensure `application-prod.properties` is being used

### Issue: Gemini API errors
- Verify API key is valid at https://aistudio.google.com/apikey
- Check rate limits: https://ai.google.dev/pricing
- Groq fallback should activate (check logs)

### Issue: CORS errors from frontend
- Add frontend URL to `CORS_ALLOWED_ORIGINS`
- Restart backend service after changing variables

---

## 📝 Post-Deployment Steps

1. **Get Backend URL:**
   ```
   https://careerpilot-backend-prod.railway.app
   ```

2. **Copy to frontend env:**
   - Update `.env` in `careerpilot-frontend`
   - Set: `REACT_APP_API_URL=https://careerpilot-backend-prod.railway.app`

3. **Deploy frontend** (separate Railway service)

---

## 🚀 Next Steps

1. Continue to **FRONTEND_DEPLOYMENT_GUIDE.md**
2. Deploy React app to Railway
3. Connect frontend to backend
4. Test end-to-end system

---

## ✅ Deployment Checklist

- [ ] Railway account created
- [ ] GitHub repo connected
- [ ] Backend service deployed
- [ ] MySQL database created
- [ ] Environment variables configured
- [ ] Backend returns `200 OK` on health check
- [ ] Logs show no errors
- [ ] Frontend configured with backend URL
- [ ] Frontend deployed to Railway
- [ ] End-to-end test passed

---

## 📞 Support

For Railway issues: https://docs.railway.app
For Spring Boot issues: https://spring.io/projects/spring-boot
