/**
 * Student Class - Represents a student user
 * Demonstrates OOP concepts: Inheritance (implements User), Encapsulation
 */
public class Student implements User {
    private String usn;
    private String name;  // Optional for anonymity
    private int year;
    private int semester;

    // Constructor with name
    public Student(String usn, String name, int year, int semester) {
        this.usn = usn;
        this.name = name;
        this.year = year;
        this.semester = semester;
    }

    // Constructor without name (anonymous)
    public Student(String usn, int year, int semester) {
        this.usn = usn;
        this.name = "Anonymous";
        this.year = year;
        this.semester = semester;
    }

    @Override
    public String getUserId() {
        return usn;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public String getUserType() {
        return "STUDENT";
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== STUDENT MENU =====");
        System.out.println("1. Submit Feedback");
        System.out.println("2. View My Submissions");
        System.out.println("3. Logout");
        System.out.println("========================");
    }

    // Getters
    public String getUsn() {
        return usn;
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public boolean isAnonymous() {
        return name.equals("Anonymous");
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Student[USN=%s, Name=%s, Year=%d, Semester=%d]", 
                           usn, name, year, semester);
    }
}
