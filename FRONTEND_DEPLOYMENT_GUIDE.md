# CareerPilot Frontend - Railway Deployment Guide

## ✅ What's Been Done

- [x] Environment configuration files created (`.env.development`, `.env.production`)
- [x] Frontend code updated to use `process.env.REACT_APP_API_URL`
- [x] Railway config file created (`railway.json`)
- [x] Procfile created for deployment
- [x] Production build bundled (`npm run build`)
- [x] Build folder ready for deployment (~97KB after gzip)

---

## 📦 Production Build Details

```
File sizes after gzip:
  97.27 kB  build/static/js/main.3848b2e6.js
  9.56 kB   build/static/css/main.1d210977.css
```

The build is optimized and ready for production deployment.

---

## 🚀 Deployment Steps

### Step 1: Sign Up for Railway (If Not Done)

1. Go to https://railway.app
2. Click "Start Project"
3. Sign up with GitHub (recommended)

---

### Step 2: Create Frontend Service on Railway

**Option A: From GitHub (Recommended)**

1. Railway dashboard → Click "Create a New Project"
2. Select "Deploy from GitHub repo"
3. Search for: `selvakumar-2005/careerpilot-AI`
4. Click "Deploy"
5. Select the `careerpilot-frontend` folder (if prompted)

**Option B: From CLI**

```bash
npm install -g railway
cd careerpilot-frontend
railway init
railway up
```

---

### Step 3: Configure Environment Variables

In Railway dashboard, go to Frontend service → **Variables**

Add:

```
REACT_APP_API_URL=https://careerpilot-backend-prod.railway.app
REACT_APP_ENV=production
NODE_ENV=production
```

Replace `careerpilot-backend-prod` with your actual backend URL from Railway.

---

### Step 4: Configure Build and Start Commands

Railway will auto-detect React app. Verify:

1. Frontend service → **Settings** → **Build**
2. Build command: Leave empty (auto-detected)
3. Start command: Should be `npm start` or `npm run serve`

If needed, set custom start command:
```
npm run build && npx serve -s build -l 3000
```

---

### Step 5: Deploy

**Option A: Automatic (Recommended)**
- Railway watches your GitHub repo
- Every push to `master` auto-deploys
- Takes ~3-5 minutes

**Option B: Manual Trigger**
1. Frontend service → **Deployments**
2. Click **Deploy** (top right)

---

### Step 6: Verify Deployment

1. Check Railway dashboard for green status
2. Copy the frontend public URL (format: `https://careerpilot-frontend-prod.railway.app`)
3. Visit the URL in browser
4. Should see CareerPilot login page

---

## 🔗 Connecting Frontend to Backend

### Get Backend URL

From Railway dashboard:
- Backend service → Copy public domain URL
- Format: `https://careerpilot-backend-prod.railway.app`

### Update Frontend Environment

The frontend already loads the backend URL from environment variables:

```javascript
// From src/services/axiosInstance.js
baseURL: process.env.REACT_APP_API_URL || ''
```

This is automatically set via Railway Variables.

---

## 🧪 Testing Frontend Connectivity

After deployment:

1. Open frontend URL in browser
2. Try to register a new user
3. Check browser Console (F12) for errors
4. Network tab should show successful requests to backend API

---

## 📊 What Railway Provides

| Service | Details |
|---------|---------|
| **Frontend** | Node.js server running React on Ubuntu container |
| **Build** | Automatic npm install and npm run build |
| **Serve** | Static files served via Node.js server |
| **HTTPS/SSL** | Public URL with automatic SSL certificate |
| **Storage** | Ephemeral filesystem (artifacts persist, code doesn't) |

---

## 💰 Estimated Monthly Cost

| Service | Cost |
|---------|------|
| Frontend compute | $5 (free tier with $5 credits) |
| Total | **~$0-2/month** |

Combined with backend: **~$0-5/month total**

---

## 🐛 Troubleshooting

### Issue: Build fails
- Check Railway build logs: Dashboard → Deployments → View logs
- Common cause: Missing dependencies
- Solution: `npm install` locally, commit `package-lock.json`

### Issue: Frontend loads but shows blank page
- Check browser console for errors (F12)
- Verify `REACT_APP_API_URL` is set in Railway Variables
- Check backend is running and accessible

### Issue: API calls fail with 401/403
- Login likely expired
- Clear browser storage: F12 → Application → Clear All
- Try login again
- Check backend JWT secret matches

### Issue: CORS errors from backend
- Backend service → Variables → Update `CORS_ALLOWED_ORIGINS`
- Add frontend URL: `https://careerpilot-frontend-prod.railway.app`
- Restart backend service

### Issue: Slow page load
- Frontend bundle is ~97KB (gzip)
- First load may take 3-5 seconds on slow connections
- Subsequent loads cached by browser
- Check browser Network tab for slow resources

---

## 🚀 Production Optimization Tips

### Enable Caching

Add to your web server (if using custom server):
```
Cache-Control: max-age=31536000
```

### Monitor Performance

1. Railway dashboard → Frontend → Metrics
2. Check CPU, Memory, Network usage
3. If metrics spike, may need to optimize code

### Update After Deployment

To deploy new changes:

1. Make changes locally
2. Commit and push to GitHub
3. Railway auto-deploys (3-5 minutes)

---

## ✅ Frontend Deployment Checklist

- [ ] Frontend service created on Railway
- [ ] Build successful (check logs)
- [ ] Environment variables configured
- [ ] Frontend public URL accessible
- [ ] Backend URL set in `REACT_APP_API_URL`
- [ ] Can register and login successfully
- [ ] AI features work (no console errors)
- [ ] Network requests go to correct backend
- [ ] HTTPS connection established

---

## 📞 Support

For Railway issues: https://docs.railway.app
For React issues: https://react.dev
For axios issues: https://axios-http.com
