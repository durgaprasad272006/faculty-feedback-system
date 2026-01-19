import java.util.*;

/**
 * AdminDashboard Class - Comprehensive admin interface with analytics
 * Demonstrates OOP concepts: Composition, Methods, Data Presentation
 */
public class AdminDashboard {
    private FeedbackService feedbackService;
    private Scanner scanner;
    
    public AdminDashboard(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Display admin dashboard menu
     */
    public void displayDashboard(Admin admin) {
        while (true) {
            admin.displayMenu();
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        viewAllFeedback();
                        break;
                    case 2:
                        generateSubjectReports();
                        break;
                    case 3:
                        generateFacultyReports();
                        break;
                    case 4:
                        generateGraphicalAnalytics();
                        break;
                    case 5:
                        viewSemesterTrends();
                        break;
                    case 6:
                        exportReports();
                        break;
                    case 7:
                        filterFeedback();
                        break;
                    case 8:
                        System.out.println("\nLogging out from Admin Dashboard...");
                        return;
                    default:
                        System.out.println("\n❌ Invalid choice! Please try again.");
                }
                
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
                
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Invalid input! Please enter a number.");
                System.out.print("Press Enter to continue...");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * View all feedback in the system
     */
    private void viewAllFeedback() {
        FeedbackRepository repository = feedbackService.getRepository();
        List<Feedback> allFeedback = repository.getAllFeedback();
        
        if (allFeedback.isEmpty()) {
            System.out.println("\n📭 No feedback available in the system.");
            return;
        }
        
        System.out.println("\n========== ALL FEEDBACK ENTRIES ==========");
        System.out.println("Total Entries: " + allFeedback.size());
        System.out.println();
        
        for (int i = 0; i < allFeedback.size(); i++) {
            System.out.println("Entry #" + (i + 1) + ":");
            allFeedback.get(i).displayDetails();
        }
    }
    
    /**
     * Generate subject-wise reports
     */
    private void generateSubjectReports() {
        FeedbackRepository repository = feedbackService.getRepository();
        List<String> subjects = repository.getUniqueSubjects();
        
        if (subjects.isEmpty()) {
            System.out.println("\n📭 No subjects found in feedback data.");
            return;
        }
        
        System.out.println("\n========== SUBJECT-WISE REPORTS ==========");
        System.out.println("\nAvailable Subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.println("0. Generate report for all subjects");
        
        System.out.print("\nEnter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            if (choice == 0) {
                // Generate for all subjects
                for (String subject : subjects) {
                    feedbackService.generateSubjectReport(subject);
                    System.out.println();
                }
            } else if (choice > 0 && choice <= subjects.size()) {
                feedbackService.generateSubjectReport(subjects.get(choice - 1));
            } else {
                System.out.println("\n❌ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input!");
        }
    }
    
    /**
     * Generate faculty performance reports
     */
    private void generateFacultyReports() {
        FeedbackRepository repository = feedbackService.getRepository();
        List<String> facultyList = repository.getUniqueFaculty();
        
        if (facultyList.isEmpty()) {
            System.out.println("\n📭 No faculty data found.");
            return;
        }
        
        System.out.println("\n========== FACULTY PERFORMANCE REPORTS ==========");
        System.out.println("\nFaculty Members:");
        for (int i = 0; i < facultyList.size(); i++) {
            System.out.println((i + 1) + ". " + facultyList.get(i));
        }
        System.out.println("0. Generate report for all faculty");
        
        System.out.print("\nEnter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            if (choice == 0) {
                // Generate for all faculty
                for (String fac : facultyList) {
                    String facId = fac.substring(fac.lastIndexOf("(") + 1, fac.lastIndexOf(")"));
                    feedbackService.viewFacultyFeedback(facId);
                    System.out.println();
                }
            } else if (choice > 0 && choice <= facultyList.size()) {
                String fac = facultyList.get(choice - 1);
                String facId = fac.substring(fac.lastIndexOf("(") + 1, fac.lastIndexOf(")"));
                feedbackService.viewFacultyFeedback(facId);
            } else {
                System.out.println("\n❌ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input!");
        }
    }
    
    /**
     * Generate graphical analytics
     */
    private void generateGraphicalAnalytics() {
        FeedbackRepository repository = feedbackService.getRepository();
        
        System.out.println("\n========== GRAPHICAL ANALYTICS ==========");
        System.out.println("1. Subject-wise Rating Bar Chart");
        System.out.println("2. Overall Rating Distribution Pie Chart");
        System.out.println("3. Semester-wise Trend Analysis");
        System.out.println("4. All Console Graphs");
        System.out.println("5. Generate HTML Dashboard Report");
        System.out.println("0. Back");
        
        System.out.print("\nEnter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            switch (choice) {
                case 1:
                    GraphGenerator.generateSubjectRatingBarChart(repository, 
                                                                repository.getUniqueSubjects());
                    break;
                case 2:
                    Map<Integer, Integer> overallDist = calculateOverallDistribution(repository);
                    GraphGenerator.generateRatingDistributionPieChart(overallDist, 
                                                                      "OVERALL RATING DISTRIBUTION");
                    break;
                case 3:
                    GraphGenerator.generateSemesterTrendChart(repository);
                    break;
                case 4:
                    GraphGenerator.generateSubjectRatingBarChart(repository, 
                                                                repository.getUniqueSubjects());
                    Map<Integer, Integer> dist = calculateOverallDistribution(repository);
                    GraphGenerator.generateRatingDistributionPieChart(dist, 
                                                                      "OVERALL RATING DISTRIBUTION");
                    GraphGenerator.generateSemesterTrendChart(repository);
                    break;
                case 5:
                    System.out.print("\nEnter filename (e.g., feedback_report.html): ");
                    String filename = scanner.nextLine().trim();
                    if (filename.isEmpty()) {
                        filename = "feedback_analytics_report.html";
                    }
                    if (!filename.endsWith(".html")) {
                        filename += ".html";
                    }
                    GraphGenerator.generateHTMLReport(repository, filename);
                    System.out.println("\n✓ HTML report saved! Open '" + filename + "' in a browser to view interactive charts.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n❌ Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input!");
        }
    }
    
    /**
     * View semester-wise trends
     */
    private void viewSemesterTrends() {
        FeedbackRepository repository = feedbackService.getRepository();
        GraphGenerator.generateSemesterTrendChart(repository);
        
        // Additional detailed semester analysis
        System.out.println("\n--- Detailed Semester Analysis ---");
        for (int sem = 1; sem <= 8; sem++) {
            List<Feedback> semFeedback = repository.getFeedbackBySemester(sem);
            if (!semFeedback.isEmpty()) {
                double avgRating = semFeedback.stream()
                    .mapToInt(Feedback::getRating)
                    .average()
                    .orElse(0.0);
                System.out.printf("Semester %d: %d entries, Avg Rating: %.2f/5.0\n", 
                                 sem, semFeedback.size(), avgRating);
            }
        }
    }
    
    /**
     * Export reports to file
     */
    private void exportReports() {
        System.out.println("\n========== EXPORT REPORTS ==========");
        System.out.println("1. Export to HTML (with interactive graphs)");
        System.out.println("2. Export summary to text file");
        System.out.println("0. Back");
        
        System.out.print("\nEnter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            
            if (choice == 1) {
                FeedbackRepository repository = feedbackService.getRepository();
                GraphGenerator.generateHTMLReport(repository, "feedback_analytics_dashboard.html");
                System.out.println("\n✓ Report exported successfully!");
            } else if (choice == 2) {
                System.out.println("\n⚠ Text export feature coming soon!");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input!");
        }
    }
    
    /**
     * Filter feedback by various criteria
     */
    private void filterFeedback() {
        FeedbackRepository repository = feedbackService.getRepository();
        
        System.out.println("\n========== FILTER FEEDBACK ==========");
        System.out.println("1. By Semester");
        System.out.println("2. By Year");
        System.out.println("3. By Subject");
        System.out.println("4. By Faculty");
        System.out.println("5. By USN");
        System.out.println("0. Back");
        
        System.out.print("\nEnter choice: ");
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            List<Feedback> filtered = new ArrayList<>();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter semester (1-8): ");
                    int sem = Integer.parseInt(scanner.nextLine().trim());
                    filtered = repository.getFeedbackBySemester(sem);
                    break;
                case 2:
                    System.out.print("Enter year (1-4): ");
                    int year = Integer.parseInt(scanner.nextLine().trim());
                    filtered = repository.getFeedbackByYear(year);
                    break;
                case 3:
                    System.out.print("Enter subject name: ");
                    String subject = scanner.nextLine().trim();
                    filtered = repository.getFeedbackBySubject(subject);
                    break;
                case 4:
                    System.out.print("Enter faculty ID: ");
                    String facId = scanner.nextLine().trim();
                    filtered = repository.getFeedbackByFaculty(facId);
                    break;
                case 5:
                    System.out.print("Enter USN: ");
                    String usn = scanner.nextLine().trim();
                    filtered = repository.getFeedbackByUSN(usn);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\n❌ Invalid choice!");
                    return;
            }
            
            if (filtered.isEmpty()) {
                System.out.println("\n📭 No feedback found matching the criteria.");
            } else {
                System.out.println("\n========== FILTERED RESULTS ==========");
                System.out.println("Found " + filtered.size() + " entries:");
                for (Feedback fb : filtered) {
                    fb.displayDetails();
                }
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n❌ Invalid input!");
        }
    }
    
    /**
     * Calculate overall rating distribution
     */
    private Map<Integer, Integer> calculateOverallDistribution(FeedbackRepository repository) {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            distribution.put(i, 0);
        }
        
        for (Feedback fb : repository.getAllFeedback()) {
            int rating = fb.getRating();
            distribution.put(rating, distribution.get(rating) + 1);
        }
        
        return distribution;
    }
}
