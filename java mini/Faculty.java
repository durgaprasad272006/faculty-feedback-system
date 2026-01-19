/**
 * Faculty Class - Represents a faculty member
 * Demonstrates OOP concepts: Inheritance (implements User), Encapsulation
 */
public class Faculty implements User {
    private String facultyId;
    private String name;
    private String department;

    public Faculty(String facultyId, String name, String department) {
        this.facultyId = facultyId;
        this.name = name;
        this.department = department;
    }

    @Override
    public String getUserId() {
        return facultyId;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public String getUserType() {
        return "FACULTY";
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== FACULTY MENU =====");
        System.out.println("1. View My Feedback");
        System.out.println("2. View Subject-wise Feedback");
        System.out.println("3. View Feedback Summary");
        System.out.println("4. Logout");
        System.out.println("========================");
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return String.format("Faculty[ID=%s, Name=%s, Department=%s]", 
                           facultyId, name, department);
    }
}
