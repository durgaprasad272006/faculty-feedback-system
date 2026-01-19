# College Feedback System - Quick Start Guide

## Compilation Commands

### Windows (PowerShell/CMD)
```powershell
cd "c:\Users\gowda\OneDrive\Desktop\java mini"
javac *.java
java FeedbackSystemApp
```

### If javac is not found:
1. **Check Java Installation:**
   ```powershell
   java -version
   ```

2. **Find JDK Installation:**
   ```powershell
   where java
   ```

3. **Add to PATH or use full path:**
   ```powershell
   "C:\Program Files\Java\jdk-XX\bin\javac.exe" *.java
   ```

### Alternative: Use an IDE
- **VS Code**: Install "Extension Pack for Java"
- **IntelliJ IDEA**: File → New Project → Import existing sources
- **Eclipse**: File → New → Java Project → Import files
- **NetBeans**: File → New Project → Java with Existing Sources

## First Run Demo Flow

### 1. Start Application
```bash
java FeedbackSystemApp
```

### 2. Create Sample Feedback (Student Flow)
```
Main Menu → 1 (Student Login)
USN: 1CS21CS001
Year: 3
Semester: 6
Name: [Enter] (anonymous) or enter name

Student Menu → 1 (Submit Feedback)
Select Subject: 1 (Web Technology)
Faculty ID: FAC001
Faculty Name: Dr. John Smith
Rating: 5
Comments: Excellent teaching!
```

### 3. View Analytics (Admin Flow)
```
Main Menu → 3 (Admin Login)
Admin ID: admin
Password: admin123

Admin Dashboard → 4 (Graphical Analytics)
→ 5 (Generate HTML Dashboard Report)
```

### 4. Open HTML Report
- Look for generated `feedback_analytics_dashboard.html`
- Double-click to open in browser
- View interactive charts!

## File Structure After Running
```
java mini/
├── *.java (14 source files)
├── *.class (compiled files)
├── feedback_data.json (auto-generated)
├── feedback_analytics_dashboard.html (when generated)
└── README.md
```

## Sample Data for Testing

### Student USNs:
- 1CS21CS001 (3rd year, 6th sem)
- 1CS21CS045 (3rd year, 6th sem)
- 1CS20CS012 (4th year, 8th sem)

### Faculty IDs:
- FAC001 (Dr. John Smith)
- FAC002 (Prof. Sarah Johnson)
- FAC003 (Dr. Michael Brown)

### Subjects (Semester 6):
- Web Technology and Applications
- Machine Learning  
- Cloud Computing
- Computer Graphics
- Mobile Application Development

## Admin Credentials
- **Username**: admin
- **Password**: admin123

## Tips
🎯 Submit 5-10 feedback entries to see meaningful graphs  
🎯 Use different semesters to see semester trends  
🎯 Try both anonymous and named feedback  
🎯 Generate HTML report for best visualization experience  

## Quick Compilation Test
```bash
# Test if Java works
java -version

# If version shows, compile:
javac FeedbackSystemApp.java

# This will auto-compile dependencies
# Then run:
java FeedbackSystemApp
```

## Common Issues

**Issue**: "Could not find or load main class"  
**Fix**: Make sure you're in the correct directory

**Issue**: No graphs showing  
**Fix**: Need feedback data first - submit as student first

**Issue**: HTML report blank  
**Fix**: Submit more feedback entries (need data for graphs)

---
💡 **Pro Tip**: Run as Admin first to see empty dashboard, then add data as Student, then refresh Admin views!
