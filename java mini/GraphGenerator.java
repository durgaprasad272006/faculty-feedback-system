import java.io.*;
import java.util.*;

/**
 * GraphGenerator Class - Generates text-based and HTML graphs
 * Demonstrates OOP concepts: Graph Generation, File I/O
 * Creates visual representations of feedback data
 */
public class GraphGenerator {
    
    /**
     * Generate console-based bar chart for subject ratings
     */
    public static void generateSubjectRatingBarChart(FeedbackRepository repository, 
                                                     List<String> subjects) {
        System.out.println("\n========== SUBJECT-WISE RATING BAR CHART ==========");
        System.out.println();
        
        if (subjects.isEmpty()) {
            System.out.println("No subjects to display.");
            return;
        }
        
        // Find max subject name length for alignment
        int maxNameLength = subjects.stream()
            .mapToInt(String::length)
            .max()
            .orElse(20);
        
        for (String subject : subjects) {
            double avgRating = repository.getAverageRatingBySubject(subject);
            int count = repository.getFeedbackBySubject(subject).size();
            
            String subjectDisplay = String.format("%-" + maxNameLength + "s", 
                                                 truncate(subject, maxNameLength));
            int barLength = (int) (avgRating * 10); // Each star represents 0.1 rating
            
            System.out.printf("%s | %.2f ★ | %s (%d)\n", 
                subjectDisplay, avgRating, getBar(barLength, '█'), count);
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Note: Each block (█) represents 0.1 rating point");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Generate console-based pie chart for rating distribution
     */
    public static void generateRatingDistributionPieChart(Map<Integer, Integer> distribution, 
                                                          String title) {
        System.out.println("\n========== " + title + " ==========");
        
        int total = distribution.values().stream().mapToInt(Integer::intValue).sum();
        
        if (total == 0) {
            System.out.println("No data to display.");
            return;
        }
        
        System.out.println("\nRating Distribution:");
        for (int rating = 5; rating >= 1; rating--) {
            int count = distribution.getOrDefault(rating, 0);
            double percentage = (count * 100.0) / total;
            int pieLength = (int) (percentage / 2); // Each block represents 2%
            
            System.out.printf("%d ★ | %3d (%5.1f%%) %s\n", 
                rating, count, percentage, getBar(pieLength, '●'));
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Total Responses: " + total);
        System.out.println("=".repeat(60));
    }
    
    /**
     * Generate semester-wise trend line chart
     */
    public static void generateSemesterTrendChart(FeedbackRepository repository) {
        System.out.println("\n========== SEMESTER-WISE FEEDBACK TREND ==========");
        
        Map<Integer, Integer> semesterCounts = new HashMap<>();
        Map<Integer, Double> semesterRatings = new HashMap<>();
        
        for (int sem = 1; sem <= 8; sem++) {
            List<Feedback> semFeedback = repository.getFeedbackBySemester(sem);
            semesterCounts.put(sem, semFeedback.size());
            
            if (!semFeedback.isEmpty()) {
                double avgRating = semFeedback.stream()
                    .mapToInt(Feedback::getRating)
                    .average()
                    .orElse(0.0);
                semesterRatings.put(sem, avgRating);
            } else {
                semesterRatings.put(sem, 0.0);
            }
        }
        
        System.out.println("\nAverage Rating by Semester:");
        System.out.println("Sem | Rating | Graph");
        System.out.println("----|--------|" + "-".repeat(50));
        
        for (int sem = 1; sem <= 8; sem++) {
            double rating = semesterRatings.get(sem);
            int count = semesterCounts.get(sem);
            int graphLength = (int) (rating * 10);
            
            System.out.printf(" %d  | %.2f   | %s (%d entries)\n", 
                sem, rating, getBar(graphLength, '▓'), count);
        }
        
        System.out.println("\n" + "=".repeat(70));
    }
    
    /**
     * Generate HTML report with graphs
     */
    public static void generateHTMLReport(FeedbackRepository repository, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang='en'>");
            writer.println("<head>");
            writer.println("    <meta charset='UTF-8'>");
            writer.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            writer.println("    <title>College Feedback System - Analytics Dashboard</title>");
            writer.println("    <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>");
            writer.println("    <style>");
            writer.println("        * { margin: 0; padding: 0; box-sizing: border-box; }");
            writer.println("        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 20px; }");
            writer.println("        .container { max-width: 1400px; margin: 0 auto; }");
            writer.println("        h1 { text-align: center; color: white; margin-bottom: 30px; font-size: 2.5em; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); }");
            writer.println("        .dashboard { display: grid; grid-template-columns: repeat(auto-fit, minmax(500px, 1fr)); gap: 20px; }");
            writer.println("        .card { background: white; border-radius: 15px; padding: 20px; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }");
            writer.println("        .card h2 { color: #667eea; margin-bottom: 20px; border-bottom: 3px solid #667eea; padding-bottom: 10px; }");
            writer.println("        .stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 15px; margin-bottom: 20px; }");
            writer.println("        .stat-box { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 10px; text-align: center; }");
            writer.println("        .stat-box h3 { font-size: 2em; margin-bottom: 5px; }");
            writer.println("        .stat-box p { font-size: 0.9em; opacity: 0.9; }");
            writer.println("        canvas { max-height: 400px; }");
            writer.println("        table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            writer.println("        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
            writer.println("        th { background-color: #667eea; color: white; }");
            writer.println("        tr:hover { background-color: #f5f5f5; }");
            writer.println("    </style>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("    <div class='container'>");
            writer.println("        <h1>📊 College Feedback Analytics Dashboard</h1>");
            
            // Generate statistics
            List<Feedback> allFeedback = repository.getAllFeedback();
            List<String> subjects = repository.getUniqueSubjects();
            List<String> faculty = repository.getUniqueFaculty();
            
            writer.println("        <div class='stats'>");
            writer.println("            <div class='stat-box'>");
            writer.println("                <h3>" + allFeedback.size() + "</h3>");
            writer.println("                <p>Total Feedback</p>");
            writer.println("            </div>");
            writer.println("            <div class='stat-box'>");
            writer.println("                <h3>" + subjects.size() + "</h3>");
            writer.println("                <p>Subjects Evaluated</p>");
            writer.println("            </div>");
            writer.println("            <div class='stat-box'>");
            writer.println("                <h3>" + faculty.size() + "</h3>");
            writer.println("                <p>Faculty Members</p>");
            writer.println("            </div>");
            writer.println("        </div>");
            
            writer.println("        <div class='dashboard'>");
            
            // Subject-wise ratings chart
            writer.println("            <div class='card'>");
            writer.println("                <h2>Subject-wise Average Ratings</h2>");
            writer.println("                <canvas id='subjectChart'></canvas>");
            writer.println("            </div>");
            
            // Rating distribution pie chart
            writer.println("            <div class='card'>");
            writer.println("                <h2>Overall Rating Distribution</h2>");
            writer.println("                <canvas id='distributionChart'></canvas>");
            writer.println("            </div>");
            
            // Semester trends
            writer.println("            <div class='card'>");
            writer.println("                <h2>Semester-wise Feedback Trends</h2>");
            writer.println("                <canvas id='semesterChart'></canvas>");
            writer.println("            </div>");
            
            // Faculty ratings table
            writer.println("            <div class='card'>");
            writer.println("                <h2>Faculty Performance Summary</h2>");
            writer.println("                <table>");
            writer.println("                    <tr><th>Faculty</th><th>Avg Rating</th><th>Feedback Count</th></tr>");
            
            for (String fac : faculty) {
                String facId = fac.substring(fac.lastIndexOf("(") + 1, fac.lastIndexOf(")"));
                double avgRating = repository.getAverageRatingByFaculty(facId);
                int count = repository.getFeedbackByFaculty(facId).size();
                writer.printf("                    <tr><td>%s</td><td>%.2f ★</td><td>%d</td></tr>\n", 
                             fac, avgRating, count);
            }
            
            writer.println("                </table>");
            writer.println("            </div>");
            
            writer.println("        </div>");
            
            // Generate Chart.js scripts
            generateChartScripts(writer, repository, subjects);
            
            writer.println("    </div>");
            writer.println("</body>");
            writer.println("</html>");
            
            System.out.println("\n✓ HTML Report generated successfully: " + filename);
            
        } catch (IOException e) {
            System.err.println("Error generating HTML report: " + e.getMessage());
        }
    }
    
    /**
     * Generate Chart.js script tags
     */
    private static void generateChartScripts(PrintWriter writer, FeedbackRepository repository, 
                                            List<String> subjects) {
        writer.println("    <script>");
        
        // Subject-wise ratings bar chart
        writer.println("        const subjectCtx = document.getElementById('subjectChart').getContext('2d');");
        writer.print("        const subjectLabels = [");
        for (int i = 0; i < subjects.size(); i++) {
            writer.print("'" + subjects.get(i).replace("'", "\\'") + "'");
            if (i < subjects.size() - 1) writer.print(", ");
        }
        writer.println("];");
        
        writer.print("        const subjectData = [");
        for (int i = 0; i < subjects.size(); i++) {
            writer.print(String.format("%.2f", repository.getAverageRatingBySubject(subjects.get(i))));
            if (i < subjects.size() - 1) writer.print(", ");
        }
        writer.println("];");
        
        writer.println("        new Chart(subjectCtx, {");
        writer.println("            type: 'bar',");
        writer.println("            data: {");
        writer.println("                labels: subjectLabels,");
        writer.println("                datasets: [{");
        writer.println("                    label: 'Average Rating',");
        writer.println("                    data: subjectData,");
        writer.println("                    backgroundColor: 'rgba(102, 126, 234, 0.8)',");
        writer.println("                    borderColor: 'rgba(102, 126, 234, 1)',");
        writer.println("                    borderWidth: 2");
        writer.println("                }]");
        writer.println("            },");
        writer.println("            options: { scales: { y: { beginAtZero: true, max: 5 } } }");
        writer.println("        });");
        
        // Overall rating distribution pie chart
        Map<Integer, Integer> overallDist = new HashMap<>();
        for (int i = 1; i <= 5; i++) overallDist.put(i, 0);
        
        for (Feedback fb : repository.getAllFeedback()) {
            int rating = fb.getRating();
            overallDist.put(rating, overallDist.get(rating) + 1);
        }
        
        writer.println("        const distributionCtx = document.getElementById('distributionChart').getContext('2d');");
        writer.println("        new Chart(distributionCtx, {");
        writer.println("            type: 'doughnut',");
        writer.println("            data: {");
        writer.println("                labels: ['5 Stars', '4 Stars', '3 Stars', '2 Stars', '1 Star'],");
        writer.print("                datasets: [{ data: [");
        for (int i = 5; i >= 1; i--) {
            writer.print(overallDist.get(i));
            if (i > 1) writer.print(", ");
        }
        writer.println("],");
        writer.println("                    backgroundColor: ['#10b981', '#3b82f6', '#fbbf24', '#f97316', '#ef4444']");
        writer.println("                }]");
        writer.println("            }");
        writer.println("        });");
        
        // Semester trends
        writer.println("        const semesterCtx = document.getElementById('semesterChart').getContext('2d');");
        writer.print("        const semesterData = [");
        for (int sem = 1; sem <= 8; sem++) {
            List<Feedback> semFeedback = repository.getFeedbackBySemester(sem);
            double avg = semFeedback.isEmpty() ? 0 : 
                        semFeedback.stream().mapToInt(Feedback::getRating).average().orElse(0.0);
            writer.print(String.format("%.2f", avg));
            if (sem < 8) writer.print(", ");
        }
        writer.println("];");
        
        writer.println("        new Chart(semesterCtx, {");
        writer.println("            type: 'line',");
        writer.println("            data: {");
        writer.println("                labels: ['Sem 1', 'Sem 2', 'Sem 3', 'Sem 4', 'Sem 5', 'Sem 6', 'Sem 7', 'Sem 8'],");
        writer.println("                datasets: [{");
        writer.println("                    label: 'Average Rating',");
        writer.println("                    data: semesterData,");
        writer.println("                    borderColor: 'rgba(118, 75, 162, 1)',");
        writer.println("                    backgroundColor: 'rgba(118, 75, 162, 0.2)',");
        writer.println("                    tension: 0.4,");
        writer.println("                    fill: true");
        writer.println("                }]");
        writer.println("            },");
        writer.println("            options: { scales: { y: { beginAtZero: true, max: 5 } } }");
        writer.println("        });");
        
        writer.println("    </script>");
    }
    
    /**
     * Helper method to generate text bar
     */
    private static String getBar(int length, char symbol) {
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bar.append(symbol);
        }
        return bar.toString();
    }
    
    /**
     * Helper method to truncate string
     */
    private static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
