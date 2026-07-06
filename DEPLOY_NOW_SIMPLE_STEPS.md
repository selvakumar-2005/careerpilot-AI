# 🚀 Deploy CareerPilot AI - Simple Step by Step Guide

**Time: 15 minutes**  
**Cost: $0 (FREE)**  
**No credit card needed**

---

## ✅ BEFORE YOU START

Get 2 things ready:

### 1️⃣ Gemini API Key (Takes 2 minutes)

1. Go to: https://aistudio.google.com/apikey
2. Click the blue button "Create API key"
3. Click "Create" again if it asks
4. Copy the long text (your API key)
5. Save it somewhere (you'll need it in Step 5)

✅ **Done! Keep this key safe.**

---

### 2️⃣ GitHub Account (Should have already)

- You already pushed your code here: https://github.com/selvakumar-2005/careerpilot-AI
- Keep this URL handy

---

## 🎯 DEPLOYMENT STEPS (5 Simple Steps)

### **STEP 1: Go to Railway Website** (1 minute)

```
1. Open your web browser
2. Go to: https://railway.app
3. You'll see a big blue button "Start Project"
4. Click it
```

**Screenshot: You should see Railway homepage with "Start Project" button**

---

### **STEP 2: Sign Up with GitHub** (2 minutes)

```
1. Look for GitHub option to sign up
2. Click "Sign up with GitHub"
3. GitHub login page opens
4. Enter your GitHub username and password
5. Click "Sign in"
6. GitHub asks: "Authorize railway?"
7. Click "Authorize railway"
8. Back to Railway website - you're now logged in ✅
```

**Screenshot: You should see Railway dashboard (empty, no projects yet)**

---

### **STEP 3: Create New Project from GitHub** (2 minutes)

```
1. On Railway dashboard, click "Create a New Project" (or "New Project" button)
2. You'll see options:
   - ❌ Don't click "Blank Project"
   - ❌ Don't click "Create new empty project"
   - ✅ Click "Deploy from GitHub repo"
3. GitHub login pops up again (if needed)
4. Click "Authorize railway" (if asked)
```

**Screenshot: You should see a list of your GitHub repos**

---

### **STEP 4: Select Your CareerPilot Repository** (2 minutes)

```
1. You'll see a search box "Search repositories..."
2. Type: careerpilot-AI
3. Your repo appears: "selvakumar-2005/careerpilot-AI"
4. Click on it
5. Click "Deploy" button (big button, usually blue)
6. Railway starts building your app
```

**Screenshot: You'll see "Building..." messages and progress bars**

---

### **STEP 5: Set Environment Variables** (5 minutes)

While Railway is building, do this:

#### 5A. Find Backend Service

```
1. On Railway dashboard, you'll see your project
2. You'll see a box labeled "careerpilot-backend"
3. Click on it to open it
4. Look for a tab that says "Variables" or "Settings"
5. Click on "Variables"
```

#### 5B. Add Your API Keys

```
In the Variables section:

1. Add SPRING_PROFILES_ACTIVE
   Key: SPRING_PROFILES_ACTIVE
   Value: prod
   Click "Add"

2. Add Gemini API Key
   Key: GEMINI_API_KEY
   Value: [PASTE YOUR KEY FROM STEP 1]
   Click "Add"

3. Add Groq Key (copy exactly)
   Key: GROQ_API_KEY
   Value: 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
   Click "Add"

4. JWT Secret (copy exactly)
   Key: JWT_SECRET
   Value: Y2FyZWVycGlsb3RhaXNlY3JldGtleWZvcmp3dGF1dGhlbnRpY2F0aW9uMjAyNHZlcnkgc2VjdXJlIGtleQ==
   Click "Add"
```

**Screenshot: You should see 4 variables added**

---

### **STEP 6: Wait for Deployment** (3-5 minutes)

```
1. Go back to project view
2. Look at the service boxes:
   - careerpilot-backend
   - careerpilot-frontend
   - careerpilot-db (database)

3. Wait for all 3 to turn GREEN ✅
   (They'll show a green dot when ready)

4. This takes about 3-5 minutes
```

**Screenshot: All 3 services should show green status indicator**

---

## ✅ YOUR APP IS NOW LIVE!

### Get Your Live URLs

```
1. Click on "careerpilot-backend" service
2. Look for "Deployments" tab or "Settings"
3. Find the public domain URL (looks like):
   careerpilot-backend-prod.railway.app
   
4. Click on "careerpilot-frontend" service
5. Find the public domain URL (looks like):
   careerpilot-frontend-prod.railway.app
```

---

## 🧪 TEST YOUR APP (Quick Test)

### Test 1: Backend is Working

```
1. Copy your backend URL
2. Add /api/health to it
   Example: https://careerpilot-backend-prod.railway.app/api/health
3. Paste in browser
4. Should see: 200 OK or status response
```

### Test 2: Frontend is Working

```
1. Copy your frontend URL
2. Paste in browser
3. You should see CareerPilot login page
```

### Test 3: Can You Login?

```
1. Go to frontend URL
2. Try to register:
   Email: test@example.com
   Password: Test@123
3. Click "Register"
4. After registration, try to login
5. You should see Dashboard ✅
```

### Test 4: Can You Use AI?

```
1. On Dashboard, go to "Career Assistant"
2. Select "Software Development"
3. Type a question: "What skills do I need?"
4. Click "Get Guidance"
5. AI should respond with advice ✅
```

---

## 📱 SHARE WITH YOUR PROFESSOR

After deployment, share these URLs:

```
🌐 Frontend (Demo): https://careerpilot-frontend-prod.railway.app
📡 Backend (API): https://careerpilot-backend-prod.railway.app
💻 GitHub: https://github.com/selvakumar-2005/careerpilot-AI
```

---

## ⚠️ TROUBLESHOOTING

### Problem: Services won't turn green (still yellow/building)

**Solution:**
```
1. Wait 5-10 minutes (first deployment is slow)
2. Check Logs tab in Railway
3. Look for any error messages
4. If error about database, wait more (MySQL taking time)
```

### Problem: Backend health check fails (404 or error)

**Solution:**
```
1. Go to Backend service
2. Click "Logs" tab
3. Look for error messages
4. Common error: SPRING_PROFILES_ACTIVE not set
5. Fix: Add SPRING_PROFILES_ACTIVE=prod to Variables
```

### Problem: Frontend shows blank page

**Solution:**
```
1. Press F12 (open developer tools)
2. Go to "Console" tab
3. Look for red errors
4. Common error: REACT_APP_API_URL not set
5. Fix: Frontend service → Variables
     Add: REACT_APP_API_URL=https://[your-backend-url]
     Replace [your-backend-url] with your actual backend URL
```

### Problem: Can't login (401 error)

**Solution:**
```
1. Clear browser cookies
2. Clear browser cache
3. Refresh page
4. Try again
5. If still fails, backend service might not have JWT_SECRET
```

---

## 🎉 CONGRATULATIONS!

Your CareerPilot AI is now LIVE on the internet! 🚀

**What you have:**
- ✅ Live website people can visit
- ✅ Working AI features
- ✅ Database storing data
- ✅ Auto-deploy (future code changes auto-deploy)
- ✅ HTTPS/SSL (secure connection)

**Cost:** $0/month (FREE with Railway credits)

**Next time you change code:**
```
git add .
git commit -m "your message"
git push origin master

Railway will auto-deploy in 3-5 minutes!
```

---

## 📞 QUICK REFERENCE

| Step | Time | What To Do |
|------|------|-----------|
| 1. Get API key | 2 min | https://aistudio.google.com/apikey |
| 2. Go to Railway | 1 min | https://railway.app |
| 3. Sign up GitHub | 2 min | Click "Start Project" |
| 4. Deploy repo | 2 min | Select careerpilot-AI |
| 5. Add variables | 5 min | GEMINI_API_KEY, GROQ_API_KEY, JWT_SECRET, SPRING_PROFILES_ACTIVE |
| 6. Wait | 3-5 min | All 3 services turn GREEN |
| **TOTAL** | **15-20 min** | **YOUR APP IS LIVE** ✅ |

---

## ✨ YOU DID IT!

From clicking "Start Project" to having a live app: **15-20 minutes**

Congratulations! 🎉

Now you can:
- Show your professor the live demo
- Share the link with classmates
- Keep developing and pushing updates
- Everything auto-deploys!
