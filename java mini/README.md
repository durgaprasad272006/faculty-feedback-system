# College Feedback System - User Guide

## Overview
A comprehensive Java-based College Feedback Management System with admin dashboard and graphical analytics.

## Features
- ✅ **Student Module**: Submit feedback for subjects with optional anonymity
- ✅ **Faculty Module**: View feedback and performance reports
- ✅ **Admin Dashboard**: Complete analytics with graphical visualizations
- ✅ **Data Storage**: JSON-based persistent storage
- ✅ **Graph Analytics**: Console charts + HTML reports with Chart.js
- ✅ **OOP Design**: Interfaces, inheritance, encapsulation, exception handling

## System Requirements
- Java Development Kit (JDK) 8 or higher
- Any Java IDE or command line compiler
- Web browser (for viewing HTML reports)

## Compilation & Running

### Option 1: Using Command Line
```bash
# Navigate to project directory
cd "c:\Users\gowda\OneDrive\Desktop\java mini"

# Compile all Java files
javac *.java

# Run the application
java FeedbackSystemApp
```

### Option 2: Using an IDE
1. Open your Java IDE (Eclipse, IntelliJ IDEA, NetBeans, VS Code)
2. Create a new Java project
3. Add all .java files to the project
4. Run `FeedbackSystemApp.java` as the main class

## Usage Guide

### 1. Student Login
- Enter USN (format: 1AB20CS001)
- Enter year (1-4) and semester (1-8)
- Choose to enter name or remain anonymous
- Submit feedback for subjects

**Feedback Submission:**
- Select from predefined subjects for your semester OR
- Enter custom subject details
- Provide faculty information
- Rate on a scale of 1-5
- Add optional comments

### 2. Faculty Login
- Enter Faculty ID and name
- View personal feedback
- See subject-wise reports
- Access feedback summaries

### 3. Admin Login
**Default Credentials:**
- Username: `admin`
- Password: `admin123`

**Admin Features:**
1. View all feedback in the system
2. Generate subject-wise reports
3. Generate faculty performance reports
4. **Graphical Analytics:**
   - Subject-wise rating bar charts
   - Rating distribution pie charts
   - Semester-wise trend analysis
   - HTML dashboard with interactive Chart.js graphs
5. Export reports to HTML
6. Filter feedback by semester, year, subject, faculty, or USN

## File Structure

### Core Models
- `User.java` - Base interface for all user types
- `Student.java` - Student implementation
- `Faculty.java` - Faculty implementation  
- `Admin.java` - Admin implementation
- `Feedback.java` - Feedback data model
- `Subject.java` - Subject data model

### Exception Handling
- `InvalidInputException.java` - Custom exception for input validation
- `DataStorageException.java` - Custom exception for storage errors

### Data Layer
- `JSONHandler.java` - Manual JSON serialization/deserialization
- `FeedbackRepository.java` - Data access with CRUD operations

### Business Logic
- `InputValidator.java` - Input validation utility
- `SubjectManager.java` - Subject management with predefined subjects
- `FeedbackService.java` - Business logic for feedback operations

### Admin & Visualization
- `AdminDashboard.java` - Comprehensive admin interface
- `GraphGenerator.java` - Console charts + HTML report generation

### Main Application
- `FeedbackSystemApp.java` - Main controller with all user flows

## Data Files (Auto-generated)
- `feedback_data.json` - Stores all submitted feedback
- `*.html` - Generated HTML reports with interactive graphs

## Sample USN Format
Valid USN examples:
- 1CS21CS001
- 2EC20EC045
- 3ME19ME123

## Graph Features

### Console-Based Graphs
- Text-based bar charts for subject ratings
- Pie charts for rating distribution
- Line charts for semester trends

### HTML Reports
The system generates interactive HTML dashboards with:
- **Chart.js Integration**: Professional, interactive charts
- **Responsive Design**: Beautiful gradient UI
- **Multiple Chart Types**: Bar, doughnut, and line charts
- **Summary Statistics**: Total feedback, subjects, faculty count
- **Faculty Performance Table**: Detailed faculty ratings

To view HTML reports:
1. Login as admin
2. Select "Generate Graphical Analytics"
3. Choose "Generate HTML Dashboard Report"
4. Open the generated .html file in any web browser

## OOP Concepts Demonstrated

1. **Interface**: `User` interface implemented by Student, Faculty, Admin
2. **Inheritance**: All user classes implement User interface
3. **Encapsulation**: Private fields with getters/setters
4. **Exception Handling**: Custom exceptions with try-catch blocks
5. **Methods**: Various methods for business logic
6. **Collections**: Lists, Maps for data management
7. **File I/O**: JSON file reading/writing
8. **Data Modeling**: Proper class design for domain objects

## Troubleshooting

### "javac is not recognized"
- Ensure JDK is installed
- Add Java bin directory to system PATH
- Or use an IDE instead

### "File not found" errors
- Ensure you're running from the correct directory
- Check file permissions

### Empty feedback list
- Normal on first run
- Submit feedback as student first
- Data persists in `feedback_data.json`

## Admin Dashboard Navigation

```
Main Menu → Admin Login → Admin Dashboard
  ├── 1. View All Feedback
  ├── 2. Subject-wise Reports
  ├── 3. Faculty Performance Reports
  ├── 4. Graphical Analytics
  │     ├── Subject Rating Bar Chart
  │     ├── Rating Distribution Pie Chart
  │     ├── Semester Trend Analysis
  │     ├── All Console Graphs
  │     └── Generate HTML Dashboard ⭐
  ├── 5. Semester-wise Trends
  ├── 6. Export Reports
  ├── 7. Filter Feedback
  └── 8. Logout
```

## Notes
- Student names are optional for anonymity
- All feedback is timestamped automatically
- Ratings are on a 1-5 scale
- HTML reports require a web browser to view
- Data persists between program runs in JSON format

## Project Highlights
✨ **Admin Dashboard with Graphs**: Complete analytics with visual representations  
✨ **No External Dependencies**: Pure Java with manual JSON handling  
✨ **Professional HTML Reports**: Chart.js integration for interactive graphs  
✨ **Anonymous Feedback**: Optional student names for privacy  
✨ **Flexible Subject Entry**: Predefined + manual subject entry support  
✨ **Comprehensive Exception Handling**: Robust error management  
✨ **OOP Best Practices**: Clean architecture with interfaces and inheritance  

---
**Developed as a Java Mini Project**  
**Features**: OOP Principles, Exception Handling, File I/O, Data Structures, Graph Generation
