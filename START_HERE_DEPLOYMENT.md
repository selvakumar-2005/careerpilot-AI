# 🚀 CareerPilot AI - START HERE: Complete Deployment Guide

**Status:** ✅ Everything is ready to deploy  
**Platform:** Railway.app (Perfect for final-year projects)  
**Cost:** ~$0-5/month (covered by free tier)  
**Deployment Time:** 10-15 minutes  

---

## 📌 What You'll Have After Deployment

```
✅ Live Backend API
   URL: https://careerpilot-backend-prod.railway.app
   
✅ Live Frontend (React)
   URL: https://careerpilot-frontend-prod.railway.app
   
✅ Live Database (MySQL)
   Auto-managed by Railway
   
✅ Auto-Deployment from GitHub
   Every push = automatic redeploy
```

---

## 🎯 3-Minute Quick Start

### If you're in a hurry:

1. **Go to:** https://railway.app
2. **Click:** "Start Project"
3. **Sign up** with GitHub
4. **Select:** "Deploy from GitHub repo"
5. **Search:** `selvakumar-2005/careerpilot-AI`
6. **Click:** "Deploy"
7. **Wait:** 10 minutes
8. **Done!** ✅

*That's it. Everything deploys automatically.*

---

## 📚 Detailed Deployment Steps (Choose Your Method)

### Method A: Web UI (Easiest, Recommended)

**Follow:** `RAILWAY_DEPLOYMENT_COMPLETE.md`

Steps:
1. Sign up on Railway with GitHub
2. Create new project from GitHub repo
3. Add environment variables
4. Deploy backend (automatic)
5. Add MySQL database (automatic)
6. Deploy frontend (automatic)
7. Verify everything works

**Time:** ~10 minutes | **Difficulty:** Very Easy

---

### Method B: CLI (For Experienced Users)

**Follow:** `DEPLOY_WITH_RAILWAY_CLI.md`

Steps:
1. Install Railway CLI: `npm install -g @railway/cli`
2. Login: `railway login`
3. Initialize: `railway init`
4. Add services: `railway service add`
5. Set variables: `railway variables set`
6. Deploy: `railway up`

**Time:** ~10 minutes | **Difficulty:** Medium

---

### Method C: Quick Reference Card

**Follow:** `QUICK_DEPLOY_REFERENCE.txt`

Quick checklist with all key information on one page.

**Time:** ~5 minutes | **Difficulty:** Easy

---

## 🔑 Critical Information You Need

### 1. Gemini API Key (REQUIRED)

```
Go to: https://aistudio.google.com/apikey
Click: "Create API key"
Copy the key
Paste into: Railway → Backend → Variables → GEMINI_API_KEY
```

**Why:** Primary AI provider for career guidance, resume analysis, etc.

### 2. Groq API Key (Already Set)

```
Already configured: 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
This is your free-tier fallback AI provider
```

### 3. GitHub Repository

```
Already pushed: https://github.com/selvakumar-2005/careerpilot-AI
Railway watches this repo and auto-deploys on every push
```

---

## ⚙️ Environment Variables Summary

### Backend Service Needs

```
SPRING_PROFILES_ACTIVE=prod
GEMINI_API_KEY=[from aistudio.google.com]
GROQ_API_KEY=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
JWT_SECRET=Y2FyZWVycGlsb3RhaXNlY3JldGtleWZvcmp3dGF1dGhlbnRpY2F0aW9uMjAyNHZlcnkgc2VjdXJlIGtleQ==
CORS_ALLOWED_ORIGINS=http://localhost:3000
DATABASE_URL=[auto-injected by Railway from MySQL]
```

### Frontend Service Needs

```
REACT_APP_API_URL=https://[your-backend-url].railway.app
REACT_APP_ENV=production
NODE_ENV=production
```

---

## 📋 Deployment Checklist

### Before You Start
- [ ] GitHub account connected
- [ ] Gemini API key obtained
- [ ] Read one of the deployment guides above

### During Deployment
- [ ] Create Railway account
- [ ] Connect GitHub repository
- [ ] Backend service deploys (green status)
- [ ] MySQL database deploys (green status)
- [ ] Frontend service deploys (green status)
- [ ] Set environment variables

### After Deployment
- [ ] Backend health check works (200 OK)
- [ ] Frontend page loads
- [ ] Can register new user
- [ ] Can login
- [ ] Can access dashboard
- [ ] AI features work

---

## 🧪 Testing Your Deployment (After Deployment)

**Follow:** `POST_DEPLOYMENT_INTEGRATION.md`

This guide walks you through 10 phases of testing:

1. ✅ Verify services are running
2. ✅ Test user registration
3. ✅ Test login
4. ✅ Test dashboard
5. ✅ Test AI features
6. ✅ Test resume upload
7. ✅ Check integration points
8. ✅ Test auto-deployment
9. ✅ Monitor performance
10. ✅ Failover testing (optional)

---

## 📊 What Each Service Does

```
┌─────────────────────────────────────────────┐
│         CareerPilot AI Architecture         │
├─────────────────────────────────────────────┤
│                                             │
│  🌐 Frontend (React)                        │
│  ├─ Runs on: Node.js + Serve               │
│  ├─ URL: https://careerpilot-frontend...   │
│  └─ Built bundle: 97KB (gzip)              │
│                                             │
│  📡 Backend (Spring Boot)                   │
│  ├─ Runs on: Java 21 JVM                   │
│  ├─ URL: https://careerpilot-backend...    │
│  ├─ Port: 8080                             │
│  └─ Features: Auth, AI, Resume, etc.       │
│                                             │
│  🗄️ Database (MySQL)                       │
│  ├─ Managed by: Railway                    │
│  ├─ Version: MySQL 8.0                     │
│  ├─ Auto-backups: Yes                      │
│  └─ Connected via: DATABASE_URL            │
│                                             │
│  🤖 AI Providers                            │
│  ├─ Primary: Gemini 2.0 Flash              │
│  └─ Fallback: Groq Llama 3.3               │
│     (Auto-switches on error)               │
│                                             │
└─────────────────────────────────────────────┘
```

---

## 🚨 Deployment Issues? Read This

### Common Problems & Solutions

**Backend won't start**
- Check: Logs → look for errors
- Fix: Verify SPRING_PROFILES_ACTIVE=prod
- Verify: MySQL service is running

**Frontend shows blank page**
- Check: REACT_APP_API_URL is correct
- Fix: Clear browser cache (F12 → Application → Clear)
- Verify: Backend is accessible

**API 401 errors**
- Cause: JWT token invalid
- Fix: Logout and login again
- Check: JWT_SECRET is same in backend

**CORS errors**
- Cause: Frontend URL not in CORS list
- Fix: Add to CORS_ALLOWED_ORIGINS
- Restart: Backend service

**Gemini API errors**
- Cause: Invalid API key
- Check: https://aistudio.google.com/apikey
- Verify: Key is valid and not exceeded quota
- Fallback: Groq automatically takes over (check logs)

---

## 📞 Need Help?

### Step-by-Step Guides

| Issue | Guide |
|-------|-------|
| How to deploy? | `RAILWAY_DEPLOYMENT_COMPLETE.md` |
| Using CLI? | `DEPLOY_WITH_RAILWAY_CLI.md` |
| Need quick ref? | `QUICK_DEPLOY_REFERENCE.txt` |
| Testing after deploy? | `POST_DEPLOYMENT_INTEGRATION.md` |
| Backend specific? | `BACKEND_DEPLOYMENT_GUIDE.md` |
| Frontend specific? | `FRONTEND_DEPLOYMENT_GUIDE.md` |

### External Resources

- Railway Docs: https://docs.railway.app
- Spring Boot: https://spring.io/projects/spring-boot
- React: https://react.dev
- Gemini API: https://ai.google.dev

---

## 🎯 Success Looks Like This

After successful deployment:

```
✅ Backend API Status: https://careerpilot-backend-prod.railway.app/api/health
   Response: 200 OK

✅ Frontend URL: https://careerpilot-frontend-prod.railway.app
   Shows: Login page

✅ Features Work:
   - Register new user
   - Login
   - Dashboard loads
   - Career Guidance responds
   - Resume Analysis works
   - No console errors

✅ GitHub Auto-Deploy:
   - Push code
   - Railway auto-deploys
   - Changes live in 3-5 minutes
```

---

## 📱 Share These Links With Reviewers

After deployment, share:

```
🎓 Project Submission

Frontend Demo:
https://careerpilot-frontend-prod.railway.app

Backend API:
https://careerpilot-backend-prod.railway.app

GitHub Repository:
https://github.com/selvakumar-2005/careerpilot-AI

Documentation:
- RAILWAY_DEPLOYMENT_COMPLETE.md (How it was deployed)
- POST_DEPLOYMENT_INTEGRATION.md (How to test it)
- PROJECT_DOCUMENTATION.txt (Full project docs)
```

---

## 🚀 Next Steps

### Step 1: Choose Deployment Method
- Read one of: RAILWAY_DEPLOYMENT_COMPLETE.md (recommended) or DEPLOY_WITH_RAILWAY_CLI.md

### Step 2: Execute Deployment
- Follow the guide for your chosen method
- Should take ~10 minutes

### Step 3: Test Everything
- Follow: POST_DEPLOYMENT_INTEGRATION.md
- Verify all features work
- Should take ~30 minutes

### Step 4: Monitor
- Check Railway dashboard daily
- Review logs for errors
- Monitor costs

### Step 5: Share
- Give reviewers the live URLs
- Share GitHub link
- Prepare for demo/presentation

---

## 💡 Pro Tips

### Tip 1: Save Your Railway URLs
After deployment, save these URLs somewhere safe:

```
Backend:  https://careerpilot-backend-prod.railway.app
Frontend: https://careerpilot-frontend-prod.railway.app
```

### Tip 2: Monitor Logs
Railway dashboard → Each service → Logs tab
Check logs regularly for issues.

### Tip 3: Auto-Deploy Works
Every `git push` automatically redeploys your app. No manual steps needed!

```bash
git add .
git commit -m "New feature"
git push origin master
# Railway auto-deploys in 3-5 minutes
```

### Tip 4: Free Tier Covers You
$5 monthly credits typically covers this entire project.
Check Railway billing page to monitor costs.

---

## ✨ Final Checklist Before Submitting

- [ ] Backend deployed and running
- [ ] Frontend deployed and running  
- [ ] Database connected
- [ ] All features tested and working
- [ ] Live URLs documented
- [ ] GitHub repository has all code
- [ ] Deployment documentation in repo
- [ ] Screenshots captured for report
- [ ] Ready to demo to professors

---

## 🎉 You're Ready!

Everything is set up and ready to deploy. Choose your method above and get started!

**Questions?** Read the specific guide for your deployment method.

**Ready?** Let's deploy! 🚀

---

**Last Updated:** July 6, 2026  
**Status:** ✅ Ready for Production Deployment  
**Next Step:** Choose deployment method and follow the guide
