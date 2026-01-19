import java.util.*;

/**
 * FeedbackSystemApp - Main application controller
 * Demonstrates OOP concepts: Main Application Flow, Exception Handling, User Interaction
 * College Feedback System with Admin Dashboard and Graph Analytics
 */
public class FeedbackSystemApp {
    private static FeedbackService feedbackService;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        feedbackService = new FeedbackService();
        scanner = new Scanner(System.in);
        
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║    COLLEGE FEEDBACK MANAGEMENT SYSTEM                     ║");
        System.out.println("║    OOP-Based Java Application with Admin Analytics        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        while (true) {
            displayMainMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        studentLogin();
                        break;
                    case 2:
                        facultyLogin();
                        break;
                    case 3:
                        adminLogin();
                        break;
                    case 4:
                        viewSystemStatistics();
                        break;
                    case 5:
                        System.out.println("\n✓ Thank you for using College Feedback System!");
                        System.out.println("  Goodbye!\n");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("\n❌ Invalid choice! Please try again.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input! Please enter a number.");
            } catch (Exception e) {
                System.err.println("\n❌ An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    /**
     * Display main menu
     */
    private static void displayMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║           MAIN MENU                           ║");
        System.out.println("╠═══════════════════════════════════════════════╣");
        System.out.println("║  1. Student Login                             ║");
        System.out.println("║  2. Faculty Login                             ║");
        System.out.println("║  3. Admin Login                               ║");
        System.out.println("║  4. View System Statistics                    ║");
        System.out.println("║  5. Exit                                      ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Student login and operations
     */
    private static void studentLogin() {
        System.out.println("\n========== STUDENT LOGIN ==========");
        
        try {
            System.out.print("Enter your USN: ");
            String usn = scanner.nextLine().trim().toUpperCase();
            
            InputValidator.validateUSN(usn);
            
            System.out.print("Enter your year (1-4): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            
            InputValidator.validateYear(year);
            
            System.out.print("Enter your semester (1-8): ");
            int semester = Integer.parseInt(scanner.nextLine().trim());
            
            InputValidator.validateSemester(semester);
            
            System.out.print("Enter your name (press Enter to remain anonymous): ");
            String name = scanner.nextLine().trim();
            
            Student student;
            if (name.isEmpty()) {
                student = new Student(usn, year, semester);
                System.out.println("\n✓ Logged in as Anonymous Student");
            } else {
                student = new Student(usn, name, year, semester);
                System.out.println("\n✓ Welcome, " + name + "!");
            }
            
            studentMenu(student);
            
        } catch (InvalidInputException e) {
            System.out.println("\n❌ " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input! Please enter valid numbers.");
        }
    }
    
    /**
     * Student menu and operations
     */
    private static void studentMenu(Student student) {
        while (true) {
            student.displayMenu();
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        submitFeedback(student);
                        break;
                    case 2:
                        feedbackService.viewStudentFeedback(student.getUsn());
                        break;
                    case 3:
                        System.out.println("\n✓ Logging out...");
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice!");
                }
                
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Submit feedback for a subject
     */
    private static void submitFeedback(Student student) {
        try {
            System.out.println("\n========== SUBMIT FEEDBACK ==========");
            
            SubjectManager subjectManager = feedbackService.getSubjectManager();
            subjectManager.displaySubjectsForSemester(student.getSemester());
            
            System.out.print("\nSelect subject (0 for manual entry): ");
            int subjectChoice = Integer.parseInt(scanner.nextLine().trim());
            
            String subjectCode;
            String subjectName;
            
            if (subjectChoice == 0) {
                // Manual subject entry
                System.out.print("Enter subject code: ");
                subjectCode = scanner.nextLine().trim().toUpperCase();
                
                System.out.print("Enter subject name: ");
                subjectName = scanner.nextLine().trim();
            } else {
                // Predefined subject
                Subject subject = subjectManager.getSubjectByIndex(student.getSemester(), 
                                                                   subjectChoice - 1);
                if (subject == null) {
                    System.out.println("\n❌ Invalid subject selection!");
                    return;
                }
                subjectCode = subject.getSubjectCode();
                subjectName = subject.getSubjectName();
            }
            
            System.out.print("Enter faculty ID: ");
            String facultyId = scanner.nextLine().trim().toUpperCase();
            
            System.out.print("Enter faculty name: ");
            String facultyName = scanner.nextLine().trim();
            
            System.out.print("Enter rating (1-5): ");
            int rating = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("Enter comments (optional): ");
            String comments = scanner.nextLine().trim();
            
            // Submit feedback
            feedbackService.submitFeedback(
                student.getUsn(),
                student.getUserName(),
                student.getYear(),
                student.getSemester(),
                subjectCode,
                subjectName,
                facultyId,
                facultyName,
                rating,
                comments
            );
            
            System.out.println("\n✓ Feedback submitted successfully!");
            
        } catch (InvalidInputException e) {
            System.out.println("\n❌ Validation Error: " + e.getMessage());
        } catch (DataStorageException e) {
            System.out.println("\n❌ Storage Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input! Please enter valid numbers.");
        }
    }
    
    /**
     * Faculty login and operations
     */
    private static void facultyLogin() {
        System.out.println("\n========== FACULTY LOGIN ==========");
        
        System.out.print("Enter Faculty ID: ");
        String facultyId = scanner.nextLine().trim().toUpperCase();
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter department: ");
        String department = scanner.nextLine().trim();
        
        Faculty faculty = new Faculty(facultyId, name, department);
        System.out.println("\n✓ Welcome, " + name + "!");
        
        facultyMenu(faculty);
    }
    
    /**
     * Faculty menu and operations
     */
    private static void facultyMenu(Faculty faculty) {
        while (true) {
            faculty.displayMenu();
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        feedbackService.viewFacultyFeedback(faculty.getUserId());
                        break;
                    case 2:
                        viewSubjectFeedback();
                        break;
                    case 3:
                        feedbackService.generateOverallReport();
                        break;
                    case 4:
                        System.out.println("\n✓ Logging out...");
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice!");
                }
                
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input!");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * View subject-specific feedback
     */
    private static void viewSubjectFeedback() {
        System.out.print("\nEnter subject name: ");
        String subjectName = scanner.nextLine().trim();
        
        feedbackService.generateSubjectReport(subjectName);
    }
    
    /**
     * Admin login and operations
     */
    private static void adminLogin() {
        System.out.println("\n========== ADMIN LOGIN ==========");
        
        System.out.print("Enter Admin ID: ");
        String adminId = scanner.nextLine().trim();
        
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();
        
        // Simple authentication (in real system, use proper authentication)
        if (!adminId.equalsIgnoreCase("admin") || !password.equals("admin123")) {
            System.out.println("\n❌ Invalid credentials!");
            return;
        }
        
        Admin admin = new Admin(adminId, "System Administrator");
        System.out.println("\n✓ Welcome to Admin Dashboard, Administrator!");
        
        AdminDashboard dashboard = new AdminDashboard(feedbackService);
        dashboard.displayDashboard(admin);
    }
    
    /**
     * View system statistics
     */
    private static void viewSystemStatistics() {
        FeedbackRepository repository = feedbackService.getRepository();
        
        System.out.println("\n========== SYSTEM STATISTICS ==========");
        System.out.println("Total Feedback Entries: " + repository.getFeedbackCount());
        System.out.println("Number of Subjects: " + repository.getUniqueSubjects().size());
        System.out.println("Number of Faculty: " + repository.getUniqueFaculty().size());
        
        if (repository.getFeedbackCount() > 0) {
            double avgRating = repository.getAllFeedback().stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);
            System.out.printf("Overall Average Rating: %.2f / 5.0\n", avgRating);
        }
        
        System.out.println("=======================================");
    }
}
