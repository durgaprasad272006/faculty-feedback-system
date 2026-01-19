import java.util.*;

/**
 * FeedbackService Class - Business logic for feedback operations
 * Demonstrates OOP concepts: Service Layer Pattern, Methods, Exception Handling
 */
public class FeedbackService {
    private FeedbackRepository repository;
    private SubjectManager subjectManager;
    
    public FeedbackService() {
        this.repository = new FeedbackRepository();
        this.subjectManager = new SubjectManager();
    }
    
    /**
     * Submit feedback with validation
     */
    public void submitFeedback(String usn, String studentName, int year, int semester,
                              String subjectCode, String subjectName, String facultyId,
                              String facultyName, int rating, String comments) 
                              throws InvalidInputException, DataStorageException {
        
        // Validate inputs
        InputValidator.validateUSN(usn);
        InputValidator.validateYear(year);
        InputValidator.validateSemester(semester);
        InputValidator.validateSubjectName(subjectName);
        InputValidator.validateFacultyId(facultyId);
        InputValidator.validateRating(rating);
        
        // Sanitize inputs
        usn = InputValidator.sanitizeString(usn).toUpperCase();
        studentName = InputValidator.sanitizeString(studentName);
        subjectCode = InputValidator.sanitizeString(subjectCode).toUpperCase();
        subjectName = InputValidator.sanitizeString(subjectName);
        facultyId = InputValidator.sanitizeString(facultyId).toUpperCase();
        facultyName = InputValidator.sanitizeString(facultyName);
        comments = InputValidator.sanitizeString(comments);
        
        // Set default for anonymous
        if (studentName.isEmpty()) {
            studentName = "Anonymous";
        }
        
        // Create and save feedback
        Feedback feedback = new Feedback(usn, studentName, year, semester,
                                        subjectCode, subjectName, facultyId,
                                        facultyName, rating, comments);
        
        repository.addFeedback(feedback);
    }
    
    /**
     * Get subject manager
     */
    public SubjectManager getSubjectManager() {
        return subjectManager;
    }
    
    /**
     * Get repository
     */
    public FeedbackRepository getRepository() {
        return repository;
    }
    
    /**
     * View feedback for a student
     */
    public void viewStudentFeedback(String usn) {
        List<Feedback> feedbackList = repository.getFeedbackByUSN(usn);
        
        if (feedbackList.isEmpty()) {
            System.out.println("\nNo feedback found for USN: " + usn);
            return;
        }
        
        System.out.println("\n========== FEEDBACK HISTORY FOR " + usn + " ==========");
        for (Feedback fb : feedbackList) {
            fb.displayDetails();
        }
    }
    
    /**
     * View feedback for a faculty
     */
    public void viewFacultyFeedback(String facultyId) {
        List<Feedback> feedbackList = repository.getFeedbackByFaculty(facultyId);
        
        if (feedbackList.isEmpty()) {
            System.out.println("\nNo feedback found for Faculty ID: " + facultyId);
            return;
        }
        
        System.out.println("\n========== FEEDBACK FOR FACULTY " + facultyId + " ==========");
        
        // Display summary
        double avgRating = repository.getAverageRatingByFaculty(facultyId);
        System.out.println("Total Feedback Entries: " + feedbackList.size());
        System.out.println("Average Rating: " + String.format("%.2f", avgRating) + " / 5.0");
        System.out.println("=".repeat(60));
        
        // Display individual feedback
        for (Feedback fb : feedbackList) {
            fb.displayDetails();
        }
    }
    
    /**
     * Generate subject-wise report
     */
    public void generateSubjectReport(String subjectName) {
        List<Feedback> feedbackList = repository.getFeedbackBySubject(subjectName);
        
        if (feedbackList.isEmpty()) {
            System.out.println("\nNo feedback found for subject: " + subjectName);
            return;
        }
        
        System.out.println("\n========== SUBJECT REPORT: " + subjectName + " ==========");
        
        double avgRating = repository.getAverageRatingBySubject(subjectName);
        Map<Integer, Integer> distribution = repository.getRatingDistributionBySubject(subjectName);
        
        System.out.println("Total Feedback Entries: " + feedbackList.size());
        System.out.println("Average Rating: " + String.format("%.2f", avgRating) + " / 5.0");
        System.out.println("\nRating Distribution:");
        for (int rating = 5; rating >= 1; rating--) {
            int count = distribution.get(rating);
            double percentage = (count * 100.0) / feedbackList.size();
            System.out.printf("  %d Star: %3d (%5.1f%%) %s\n", 
                rating, count, percentage, getBar(count, feedbackList.size()));
        }
        System.out.println("=".repeat(60));
    }
    
    /**
     * Generate overall summary report
     */
    public void generateOverallReport() {
        List<Feedback> allFeedback = repository.getAllFeedback();
        
        if (allFeedback.isEmpty()) {
            System.out.println("\nNo feedback available in the system.");
            return;
        }
        
        System.out.println("\n========== OVERALL FEEDBACK SUMMARY ==========");
        System.out.println("Total Feedback Entries: " + allFeedback.size());
        
        List<String> subjects = repository.getUniqueSubjects();
        System.out.println("Number of Subjects: " + subjects.size());
        
        List<String> faculty = repository.getUniqueFaculty();
        System.out.println("Number of Faculty Members: " + faculty.size());
        
        System.out.println("\n--- Subject-wise Average Ratings ---");
        for (String subject : subjects) {
            double avgRating = repository.getAverageRatingBySubject(subject);
            int count = repository.getFeedbackBySubject(subject).size();
            System.out.printf("%-40s : %.2f / 5.0 (%d entries)\n", subject, avgRating, count);
        }
        
        System.out.println("=".repeat(60));
    }
    
    /**
     * Helper method to generate bar for visualization
     */
    private String getBar(int count, int total) {
        int barLength = (int) ((count * 30.0) / total);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < barLength; i++) {
            bar.append("█");
        }
        return bar.toString();
    }
}
