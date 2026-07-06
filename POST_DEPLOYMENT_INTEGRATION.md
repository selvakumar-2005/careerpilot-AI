# Post-Deployment Integration & Testing Guide

After your backend, database, and frontend are deployed to Railway, follow this guide to verify everything works.

---

## Phase 1: Verify Each Service is Running (5 minutes)

### Backend Service

1. Go to Railway dashboard → Your project
2. Click on `careerpilot-backend` service
3. Check status indicator: should be 🟢 Green
4. In "Deployments" tab, most recent build should show ✅ Success

**Test backend health:**

```bash
curl https://[your-backend-url].railway.app/api/health
```

Expected response: `200 OK` with status information

### Database Service

1. Click on `careerpilot-db` service
2. Check status: should be 🟢 Green
3. Look for connection info in "Variables" tab
4. Should show `DATABASE_URL` automatically injected

### Frontend Service

1. Click on `careerpilot-frontend` service
2. Check status: should be 🟢 Green
3. In "Deployments" tab, most recent build should show ✅ Success

**Test frontend:**

```bash
Visit: https://[your-frontend-url].railway.app
```

You should see the CareerPilot login page.

---

## Phase 2: Test User Registration & Authentication (5 minutes)

### Register a New User

1. Open frontend URL in browser
2. Click "Register" button
3. Fill in:
   - Email: `test@careerpilot.com`
   - Password: `Test@12345`
   - Confirm password: `Test@12345`
4. Click "Register"

**Expected:** Success page or redirect to login

**If error:** Check backend logs for error details

### Login

1. Enter credentials:
   - Email: `test@careerpilot.com`
   - Password: `Test@12345`
2. Click "Login"

**Expected:** Redirects to Dashboard

**If 401 error:**
- Check JWT secret is same in backend
- Clear browser cookies and try again

---

## Phase 3: Test Dashboard Features (5 minutes)

### Dashboard Access

1. After successful login, you should see Dashboard
2. Verify you can see:
   - Learning Resources section
   - Technology Roadmaps
   - DSA Resources
   - Navigation menu

### Navigation

Click each menu item:
- [ ] Dashboard
- [ ] Career Assistant
- [ ] Resume Analyzer
- [ ] Coding Practice
- [ ] Company Prep
- [ ] Interviews
- [ ] Mock Interviews
- [ ] Logout

All should load without errors.

---

## Phase 4: Test AI Features (10 minutes)

### Test Career Guidance

1. Go to "Career Assistant"
2. Select career path (e.g., "Software Development")
3. Ask a question: "What skills do I need?"
4. Click "Get Guidance"

**Expected:**
- Response from Gemini AI (or Groq fallback)
- Should show AI-generated guidance text
- No 500 errors

**If API error:**
- Check Gemini API key is valid
- Check rate limits aren't exceeded
- Verify fallback to Groq is working (check logs)

### Check Logs for Provider Switching

Backend logs should show:
```
✅ Gemini API succeeded
OR
✅ Groq API succeeded (FALLBACK)
```

This proves the failover system is working.

---

## Phase 5: Test Resume Upload (5 minutes)

### Upload Resume

1. Go to "Resume Analyzer"
2. Click "Upload Resume"
3. Select a PDF or DOCX file from your computer
4. Click "Analyze"

**Expected:**
- File uploads successfully
- AI analyzes the resume
- Shows ATS score, skills, strengths, weaknesses, recommendations

**If error:**
- Check file is PDF or DOCX
- Verify file size < 5MB
- Check backend file upload configuration

---

## Phase 6: Check Integration Points (10 minutes)

### Frontend → Backend Communication

Open browser DevTools (F12):

1. Go to "Network" tab
2. Perform an action (e.g., login, get career guidance)
3. Verify requests go to backend URL
4. Check responses are 200 OK (not 5xx errors)

**Expected URLs:**
```
https://[backend-url].railway.app/api/auth/register
https://[backend-url].railway.app/api/auth/login
https://[backend-url].railway.app/api/career/guidance
https://[backend-url].railway.app/api/resume/analyze
```

### Environment Variables

Verify in Railway dashboard:

**Backend Variables:**
- [ ] SPRING_PROFILES_ACTIVE = prod
- [ ] GEMINI_API_KEY = [set]
- [ ] GROQ_API_KEY = [set]
- [ ] JWT_SECRET = [set]
- [ ] CORS_ALLOWED_ORIGINS = [frontend URL]
- [ ] DATABASE_URL = [auto-set]

**Frontend Variables:**
- [ ] REACT_APP_API_URL = [backend URL]
- [ ] REACT_APP_ENV = production
- [ ] NODE_ENV = production

---

## Phase 7: Test Auto-Deployment (5 minutes)

This proves CI/CD pipeline works:

1. Make a small change to frontend (e.g., update title in App.js)
2. Commit and push:
   ```bash
   cd careerpilot-frontend
   git add -A
   git commit -m "test: verify auto-deployment"
   git push origin master
   ```
3. Go to Railway dashboard → Frontend service → Deployments
4. Wait for new deployment (should start within 30 seconds)
5. After deployment completes, refresh browser
6. Verify your change appears

---

## Phase 8: Performance & Monitoring (5 minutes)

### Check Response Times

In Browser DevTools → Network tab:

- Page load time: Should be < 3 seconds
- API calls: Should be < 1 second each
- Image/JS loads: Should be < 500ms each

### View Resource Usage

Railway dashboard:
1. Each service → "Metrics" tab
2. View CPU, Memory, Network graphs
3. Should show stable, low usage during testing

### Check Service Logs

1. Each service → "Logs" tab
2. Look for any ERROR or WARNING messages
3. Should see successful requests logged

---

## Phase 9: Failover Testing (Optional, 5 minutes)

To verify Gemini → Groq failover works:

### Method 1: Disable Gemini Temporarily

1. Backend service → Variables
2. Temporarily set: `GEMINI_API_KEY=invalid`
3. Restart backend: Service → "Redeploy"
4. Try Career Guidance again
5. Should use Groq instead (check logs)
6. Restore real Gemini key

### Method 2: Monitor Logs

Check real-time switching:
1. Backend service → Logs
2. Trigger career guidance request
3. Watch logs - should show:
   ```
   ✅ Gemini API succeeded
   OR
   ⚠️ Gemini failed, trying Groq
   ✅ Groq API succeeded (FALLBACK)
   ```

---

## Phase 10: Load Testing (Optional, 10 minutes)

### Simple Load Test

Install Apache Bench:
```bash
# Mac/Linux
brew install httpd
# Windows: Use Postman or install GNU httpd

# Run test
ab -n 100 -c 10 https://[backend-url].railway.app/api/health
```

This sends 100 requests with 10 concurrent connections.

**Expected:**
- All requests succeed
- Average response time < 500ms
- No failed requests

---

## 📋 Final Verification Checklist

- [ ] Backend service is running (green status)
- [ ] Database service is running (green status)
- [ ] Frontend service is running (green status)
- [ ] Backend health check returns 200 OK
- [ ] Frontend homepage loads
- [ ] Can register new user
- [ ] Can login with registered credentials
- [ ] Dashboard displays correctly
- [ ] Career Guidance API works
- [ ] Resume upload/analysis works
- [ ] Network requests show in DevTools
- [ ] Environment variables all set
- [ ] Auto-deployment triggered successfully
- [ ] Logs show no ERROR messages
- [ ] Response times are acceptable

---

## 📸 Screenshots to Capture

For your project report, capture:

1. Railway dashboard showing all 3 services green
2. Frontend login page
3. Dashboard after login
4. Career Guidance response from AI
5. Resume Analysis results
6. Browser Network tab showing API calls
7. Backend logs showing successful requests

---

## 📞 Troubleshooting Integration Issues

### Issue: Frontend 404 on API calls

**Cause:** Wrong backend URL

**Fix:**
1. Frontend service → Variables
2. Verify `REACT_APP_API_URL` is correct
3. Check URL matches backend service URL from Railway
4. Restart frontend service

### Issue: Backend returns 500 errors

**Cause:** Database connection or configuration issue

**Fix:**
1. Check backend logs for error details
2. Verify `SPRING_PROFILES_ACTIVE=prod`
3. Check `DATABASE_URL` is set
4. Verify MySQL service is running

### Issue: Slow API responses

**Cause:** Cold start or resource constraints

**Fix:**
1. First request always slower (Java startup)
2. Check Railway Metrics for resource usage
3. If consistently slow, may need upgrade
4. Enable caching if applicable

### Issue: 401 errors on every request

**Cause:** JWT token invalid or expired

**Fix:**
1. Clear browser cookies
2. Logout and login again
3. Check JWT_SECRET hasn't changed
4. Check token expiration time is reasonable

---

## ✅ Success Criteria

Your deployment is successful when:

1. ✅ All services running without errors
2. ✅ User registration/login works
3. ✅ Can access all features
4. ✅ AI APIs respond correctly
5. ✅ Frontend ↔ Backend communication works
6. ✅ Logs show no ERROR messages
7. ✅ Performance is acceptable
8. ✅ Auto-deployment works

---

## 🎉 You're Done!

Your CareerPilot AI platform is now fully deployed and tested.

**Share with reviewers:**
- Frontend URL: `https://careerpilot-frontend-prod.railway.app`
- Backend API: `https://careerpilot-backend-prod.railway.app`
- GitHub: `https://github.com/selvakumar-2005/careerpilot-AI`

---

## 📚 Next Steps

1. Create screenshots for project report
2. Document the deployment process
3. Monitor the application for errors
4. Prepare demo for presentation
5. Gather user feedback (classmates, professors)
6. Plan future enhancements
