import java.util.*;
import java.util.stream.Collectors;

/**
 * FeedbackRepository Class - Data access layer for feedback operations
 * Demonstrates OOP concepts: Data Access Pattern, Methods, Exception Handling
 */
public class FeedbackRepository {
    private static final String FEEDBACK_FILE = "feedback_data.json";
    private List<Feedback> feedbackList;
    
    public FeedbackRepository() {
        this.feedbackList = new ArrayList<>();
        loadFromFile();
    }
    
    /**
     * Load feedback data from JSON file
     */
    private void loadFromFile() {
        try {
            this.feedbackList = JSONHandler.loadFeedbackFromJSON(FEEDBACK_FILE);
            System.out.println("Loaded " + feedbackList.size() + " feedback entries from file.");
        } catch (DataStorageException e) {
            System.err.println("Warning: " + e.getMessage());
            System.err.println("Starting with empty feedback list.");
            this.feedbackList = new ArrayList<>();
        }
    }
    
    /**
     * Save feedback data to JSON file
     */
    private void saveToFile() throws DataStorageException {
        JSONHandler.saveFeedbackToJSON(feedbackList, FEEDBACK_FILE);
    }
    
    /**
     * Add new feedback
     */
    public void addFeedback(Feedback feedback) throws DataStorageException {
        feedbackList.add(feedback);
        saveToFile();
        System.out.println("Feedback saved successfully!");
    }
    
    /**
     * Get all feedback
     */
    public List<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbackList);
    }
    
    /**
     * Get feedback by USN
     */
    public List<Feedback> getFeedbackByUSN(String usn) {
        return feedbackList.stream()
            .filter(fb -> fb.getUsn().equalsIgnoreCase(usn))
            .collect(Collectors.toList());
    }
    
    /**
     * Get feedback by semester
     */
    public List<Feedback> getFeedbackBySemester(int semester) {
        return feedbackList.stream()
            .filter(fb -> fb.getSemester() == semester)
            .collect(Collectors.toList());
    }
    
    /**
     * Get feedback by subject
     */
    public List<Feedback> getFeedbackBySubject(String subjectName) {
        return feedbackList.stream()
            .filter(fb -> fb.getSubjectName().equalsIgnoreCase(subjectName))
            .collect(Collectors.toList());
    }
    
    /**
     * Get feedback by faculty
     */
    public List<Feedback> getFeedbackByFaculty(String facultyId) {
        return feedbackList.stream()
            .filter(fb -> fb.getFacultyId().equalsIgnoreCase(facultyId))
            .collect(Collectors.toList());
    }
    
    /**
     * Get feedback by year
     */
    public List<Feedback> getFeedbackByYear(int year) {
        return feedbackList.stream()
            .filter(fb -> fb.getYear() == year)
            .collect(Collectors.toList());
    }
    
    /**
     * Get feedback count
     */
    public int getFeedbackCount() {
        return feedbackList.size();
    }
    
    /**
     * Calculate average rating for a subject
     */
    public double getAverageRatingBySubject(String subjectName) {
        List<Feedback> subjectFeedback = getFeedbackBySubject(subjectName);
        if (subjectFeedback.isEmpty()) {
            return 0.0;
        }
        
        double sum = subjectFeedback.stream()
            .mapToInt(Feedback::getRating)
            .sum();
        
        return sum / subjectFeedback.size();
    }
    
    /**
     * Calculate average rating for a faculty
     */
    public double getAverageRatingByFaculty(String facultyId) {
        List<Feedback> facultyFeedback = getFeedbackByFaculty(facultyId);
        if (facultyFeedback.isEmpty()) {
            return 0.0;
        }
        
        double sum = facultyFeedback.stream()
            .mapToInt(Feedback::getRating)
            .sum();
        
        return sum / facultyFeedback.size();
    }
    
    /**
     * Get unique subjects
     */
    public List<String> getUniqueSubjects() {
        return feedbackList.stream()
            .map(Feedback::getSubjectName)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    /**
     * Get unique faculty members
     */
    public List<String> getUniqueFaculty() {
        return feedbackList.stream()
            .map(fb -> fb.getFacultyName() + " (" + fb.getFacultyId() + ")")
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    /**
     * Get rating distribution for a subject
     */
    public Map<Integer, Integer> getRatingDistributionBySubject(String subjectName) {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }
        
        List<Feedback> subjectFeedback = getFeedbackBySubject(subjectName);
        for (Feedback fb : subjectFeedback) {
            int rating = fb.getRating();
            distribution.put(rating, distribution.get(rating) + 1);
        }
        
        return distribution;
    }
    
    /**
     * Get rating distribution for a faculty
     */
    public Map<Integer, Integer> getRatingDistributionByFaculty(String facultyId) {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }
        
        List<Feedback> facultyFeedback = getFeedbackByFaculty(facultyId);
        for (Feedback fb : facultyFeedback) {
            int rating = fb.getRating();
            distribution.put(rating, distribution.get(rating) + 1);
        }
        
        return distribution;
    }
}
