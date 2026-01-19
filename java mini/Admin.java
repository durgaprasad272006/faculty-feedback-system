/**
 * Admin Class - Represents an administrator with full system access
 * Demonstrates OOP concepts: Inheritance (implements User), Encapsulation
 */
public class Admin implements User {
    private String adminId;
    private String name;

    public Admin(String adminId, String name) {
        this.adminId = adminId;
        this.name = name;
    }

    @Override
    public String getUserId() {
        return adminId;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public String getUserType() {
        return "ADMIN";
    }

    @Override
    public void displayMenu() {
        System.out.println("\n===== ADMIN DASHBOARD =====");
        System.out.println("1. View All Feedback");
        System.out.println("2. Generate Subject-wise Reports");
        System.out.println("3. Generate Faculty Performance Reports");
        System.out.println("4. Generate Graphical Analytics");
        System.out.println("5. View Semester-wise Trends");
        System.out.println("6. Export Reports");
        System.out.println("7. Filter Feedback");
        System.out.println("8. Logout");
        System.out.println("===========================");
    }

    @Override
    public String toString() {
        return String.format("Admin[ID=%s, Name=%s]", adminId, name);
    }
}
