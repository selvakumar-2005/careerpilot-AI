# CareerPilot AI - Complete Railway Deployment Guide

**Status:** ✅ Backend and Frontend ready for deployment
**Platform:** Railway.app (free tier with $5 monthly credits)
**Cost:** ~$0-5/month

---

## 🚀 QUICK START (2-Step Deployment)

### Step 1: Sign Up on Railway with GitHub

```
1. Go to https://railway.app
2. Click "Start Project"
3. Sign up with GitHub (easiest method)
4. Authorize Railway to access your GitHub repos
```

### Step 2: Deploy From GitHub

```
1. Click "Create New Project"
2. Select "Deploy from GitHub repo"
3. Search & select: selvakumar-2005/careerpilot-AI
4. Railway auto-detects and deploys everything
5. Wait 5-10 minutes for deployment
```

Done! Your app is live.

---

## 📋 DETAILED STEP-BY-STEP GUIDE

### Phase 1: Railway Setup

#### Step 1a: Create Railway Account

1. Visit https://railway.app
2. Click "Start Project" button
3. Choose "GitHub" for sign up
4. GitHub login page appears
5. Click "Authorize railway"
6. You're logged into Railway

#### Step 1b: Create New Project

1. Railway dashboard → "Create a New Project" (top right)
2. Select "Deploy from GitHub repo"
3. Grant Railway permission to your GitHub repos (first time only)
4. Search: `careerpilot-AI`
5. Click on the repository

---

### Phase 2: Deploy Backend + Database

#### Step 2a: Railway Auto-Detection

When you select the repo, Railway will:
- Detect it's a monorepo
- Find `careerpilot-backend` (Spring Boot)
- Auto-select it for deployment

#### Step 2b: Confirm Backend Deployment

1. Click "Deploy" to start
2. Railway builds the backend JAR (~30 seconds)
3. Launches Spring Boot app (~1 minute)
4. You'll see "Build Successful" ✅

#### Step 2c: Add MySQL Database

1. In Railway project dashboard
2. Click "+ Add Service"
3. Select "MySQL"
4. Name it: `careerpilot-db`
5. Click "Deploy"

Railway automatically:
- Creates MySQL database
- Generates secure credentials
- Injects `DATABASE_URL` environment variable

#### Step 2d: Configure Backend Environment Variables

1. Backend service → Click on it
2. Go to "Variables" tab
3. Add these environment variables:

```
SPRING_PROFILES_ACTIVE=prod

GEMINI_API_KEY=YOUR_ACTUAL_GEMINI_API_KEY_HERE
(Get from https://aistudio.google.com/apikey)

GROQ_API_KEY=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

JWT_SECRET=Y2FyZWVycGlsb3RhaXNlY3JldGtleWZvcmp3dGF1dGhlbnRpY2F0aW9uMjAyNHZlcnkgc2VjdXJlIGtleQ==

CORS_ALLOWED_ORIGINS=http://localhost:3000
```

**Note:** `DATABASE_URL` is auto-injected by Railway from MySQL service.

#### Step 2e: Verify Backend is Running

1. Backend service → Click on service name
2. You should see green status indicator
3. Copy the public domain URL (e.g., `careerpilot-backend-prod.railway.app`)
4. Test it:
   ```bash
   curl https://careerpilot-backend-prod.railway.app/api/health
   ```
   Should return: `200 OK`

---

### Phase 3: Deploy Frontend

#### Step 3a: Add Frontend Service

1. Project dashboard → "+ Add Service"
2. Select "Deploy from GitHub repo"
3. Same repo `careerpilot-AI`
4. Railway asks which service to add
5. Select `careerpilot-frontend`
6. Click "Deploy"

#### Step 3b: Configure Frontend Environment Variables

1. Frontend service → "Variables"
2. Add:

```
REACT_APP_API_URL=https://careerpilot-backend-prod.railway.app
(Replace with your actual backend URL from Step 2e)

REACT_APP_ENV=production
NODE_ENV=production
```

#### Step 3c: Verify Frontend is Running

1. Frontend service → Copy public domain URL
2. Visit it in browser
3. You should see CareerPilot login page

---

## 🔐 Environment Variables Reference

### Backend Variables

| Variable | Value | Source |
|----------|-------|--------|
| `SPRING_PROFILES_ACTIVE` | `prod` | Set this |
| `DATABASE_URL` | `mysql://user:pass@host:port/db` | Auto-injected by Railway |
| `GEMINI_API_KEY` | Your key | https://aistudio.google.com/apikey |
| `GROQ_API_KEY` | Already set | Pre-configured fallback |
| `JWT_SECRET` | Base64 encoded | Use provided or generate new |
| `CORS_ALLOWED_ORIGINS` | Frontend URL | Set after frontend deployed |

### Frontend Variables

| Variable | Value | Source |
|----------|-------|--------|
| `REACT_APP_API_URL` | Backend URL from Railway | From backend service |
| `REACT_APP_ENV` | `production` | Set this |
| `NODE_ENV` | `production` | Set this |

---

## 📊 Expected Deployment Timeline

| Phase | Time | Status |
|-------|------|--------|
| GitHub signup | 2 min | ✅ One-time |
| Backend build | 2 min | ⏳ First time |
| Database setup | 1 min | ✅ Automatic |
| Backend running | 1 min | ✅ Automatic |
| Frontend build | 3 min | ⏳ First time |
| Frontend running | 1 min | ✅ Automatic |
| **Total** | **~10 min** | ✅ **Live** |

---

## 🔄 Auto-Deployment from GitHub

After initial setup, every push to GitHub automatically triggers deployment:

```bash
# Make code changes locally
git add .
git commit -m "feature: new changes"
git push origin master

# Railway automatically:
# 1. Detects the push
# 2. Rebuilds backend & frontend
# 3. Deploys to production
# (3-5 minutes)
```

No manual steps needed after initial setup!

---

## ✅ Deployment Verification Checklist

After deployment completes:

- [ ] Backend service shows green status
- [ ] MySQL database shows green status
- [ ] Frontend service shows green status
- [ ] Backend health check returns 200 OK
- [ ] Frontend URL loads login page
- [ ] Can register new user
- [ ] Can login with registered user
- [ ] Can access dashboard
- [ ] Can use AI features (Career Guidance)
- [ ] API calls show in Network tab

---

## 🐛 Troubleshooting

### Backend won't start

**Symptom:** Red status on backend service

**Solutions:**
1. Check logs: Service → "Logs" tab
2. Verify all environment variables are set
3. Check `SPRING_PROFILES_ACTIVE=prod` is set
4. Ensure `application-prod.properties` exists in code

**Common error:** Database connection failed
- Solution: Verify MySQL service exists and is running

### Frontend won't load

**Symptom:** Can't access frontend URL or blank page

**Solutions:**
1. Check logs: Service → "Logs" tab
2. Verify `REACT_APP_API_URL` is set correctly
3. Check backend URL is accessible from browser
4. Clear browser cache (F12 → Application → Clear Storage)

### Can't login / API 401 errors

**Symptom:** Login fails, see 401 in Network tab

**Solutions:**
1. Check backend logs for authentication errors
2. Verify JWT secret matches between services
3. Try registering new account
4. Clear browser cookies and cache

### CORS errors / API calls blocked

**Symptom:** Browser console shows CORS error

**Solutions:**
1. Backend service → Variables
2. Update `CORS_ALLOWED_ORIGINS` to include frontend URL
3. Example: `https://careerpilot-frontend-prod.railway.app`
4. Restart backend service

---

## 📈 Monitoring Deployment

### Check Deployment Status

1. Railway dashboard → Your project
2. Each service shows status indicator
   - 🟢 Green = Running
   - 🟡 Yellow = Building
   - 🔴 Red = Failed

### View Logs

1. Service → "Logs" tab
2. Real-time logs as app runs
3. Shows errors, startup messages, requests

### Monitor Resource Usage

1. Service → "Metrics" tab
2. View CPU, Memory, Network usage
3. If consistently high, may need optimization

---

## 💰 Cost Breakdown

### Free Tier Allowance

- $5 free credits per month
- Resets monthly

### Estimated Usage

| Service | Cost | Notes |
|---------|------|-------|
| Backend compute | $0.65/hour active | ~$5/month for 24/7 |
| Frontend compute | $0.65/hour active | ~$5/month for 24/7 |
| MySQL database | $5/month | Included in tier |
| **Total** | **$15/month** | Covered by $5 credits + paid |

**For a final-year project:** Likely free during development/testing phases.

---

## 🔐 Security Best Practices

### Before Going Live

1. **Change JWT Secret**
   ```bash
   openssl rand -base64 64
   ```
   Set this in Backend Variables

2. **Secure Gemini Key**
   - Never commit to GitHub
   - Only set in Railway Variables
   - Rotate if exposed

3. **Database Password**
   - Railway auto-generates
   - Don't change manually

4. **CORS Configuration**
   - Never use `*` in production
   - Specify exact frontend URL

5. **HTTPS**
   - Railway auto-enables
   - SSL certificate auto-renewed

---

## 📞 Getting Help

### Railway Docs
- https://docs.railway.app
- https://railway.app/support

### Spring Boot Docs
- https://spring.io/projects/spring-boot

### React Docs
- https://react.dev

### API Documentation
- Backend: `/swagger-ui.html` (if Swagger configured)
- Frontend: See BACKEND_DEPLOYMENT_GUIDE.md

---

## 🎯 Next Steps After Deployment

1. **Get live URLs:**
   - Backend: `https://careerpilot-backend-prod.railway.app`
   - Frontend: `https://careerpilot-frontend-prod.railway.app`

2. **Update documentation** with live URLs

3. **Share with professors:**
   - Frontend URL (for demo)
   - GitHub link
   - API documentation

4. **Monitor deployment:**
   - Check logs daily
   - Monitor costs on Railway dashboard

5. **Iterate:**
   - Make changes locally
   - Push to GitHub
   - Auto-deploys to production

---

## ✨ You Did It! 🎉

Your CareerPilot AI platform is now live on the internet.

**Share these URLs with your project reviewer:**

```
📱 Frontend: https://careerpilot-frontend-prod.railway.app
🔗 Backend API: https://careerpilot-backend-prod.railway.app
💻 GitHub: https://github.com/selvakumar-2005/careerpilot-AI
```

**Happy deploying!** 🚀
