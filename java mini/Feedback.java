import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Feedback Class - Main model for storing feedback data
 * Demonstrates OOP concepts: Encapsulation, Data Modeling
 */
public class Feedback {
    private String feedbackId;
    private String usn;
    private String studentName;  // Optional for anonymity
    private int year;
    private int semester;
    private String subjectCode;
    private String subjectName;
    private String facultyId;
    private String facultyName;
    private int rating;  // 1-5 scale
    private String comments;
    private String timestamp;

    // Constructor
    public Feedback(String usn, String studentName, int year, int semester,
                   String subjectCode, String subjectName, String facultyId, 
                   String facultyName, int rating, String comments) {
        this.feedbackId = generateFeedbackId();
        this.usn = usn;
        this.studentName = studentName;
        this.year = year;
        this.semester = semester;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.rating = rating;
        this.comments = comments;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Generate unique feedback ID
    private String generateFeedbackId() {
        return "FB" + System.currentTimeMillis();
    }

    // Getters
    public String getFeedbackId() {
        return feedbackId;
    }

    public String getUsn() {
        return usn;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public int getRating() {
        return rating;
    }

    public String getComments() {
        return comments;
    }

    public String getTimestamp() {
        return timestamp;
    }

    // Setters
    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("Feedback[ID=%s, USN=%s, Subject=%s, Faculty=%s, Rating=%d/5, Date=%s]",
                           feedbackId, usn, subjectName, facultyName, rating, timestamp);
    }

    // Display detailed feedback
    public void displayDetails() {
        System.out.println("\n========== FEEDBACK DETAILS ==========");
        System.out.println("Feedback ID    : " + feedbackId);
        System.out.println("USN            : " + usn);
        System.out.println("Student Name   : " + (studentName.equals("Anonymous") ? "Anonymous" : studentName));
        System.out.println("Year/Semester  : " + year + " / " + semester);
        System.out.println("Subject        : " + subjectName + " (" + subjectCode + ")");
        System.out.println("Faculty        : " + facultyName + " (" + facultyId + ")");
        System.out.println("Rating         : " + rating + " / 5");
        System.out.println("Comments       : " + (comments.isEmpty() ? "No comments" : comments));
        System.out.println("Submitted On   : " + timestamp);
        System.out.println("======================================\n");
    }
}
