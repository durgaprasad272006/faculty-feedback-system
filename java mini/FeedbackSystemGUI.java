import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * FeedbackSystemGUI - Graphical User Interface for College Feedback System
 * Automatically runs with GUI - no command line needed!
 */
public class FeedbackSystemGUI extends JFrame {
    private FeedbackService feedbackService;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(102, 126, 234);
    private static final Color SECONDARY_COLOR = new Color(118, 75, 162);
    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color SUCCESS_COLOR = new Color(16, 185, 129);
    private static final Color ERROR_COLOR = new Color(239, 68, 68);
    
    public FeedbackSystemGUI() {
        feedbackService = new FeedbackService();
        setupUI();
    }
    
    private void setupUI() {
        setTitle("College Feedback Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND);
        
        // Add different screens
        mainPanel.add(createHomeScreen(), "HOME");
        mainPanel.add(createStudentLoginScreen(), "STUDENT_LOGIN");
        mainPanel.add(createFacultyLoginScreen(), "FACULTY_LOGIN");
        mainPanel.add(createAdminLoginScreen(), "ADMIN_LOGIN");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }
    
    /**
     * Home Screen
     */
    private JPanel createHomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        
        // Header
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(900, 120));
        
        JLabel title = new JLabel("College Feedback System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        header.add(title);
        
        panel.add(header, BorderLayout.NORTH);
        
        // Center content
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 20, 15, 20);
        
        // Welcome message
        JLabel welcome = new JLabel("Welcome! Please select your role:");
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 0;
        center.add(welcome, gbc);
        
        // Student button
        JButton studentBtn = createStyledButton("Student Login", PRIMARY_COLOR);
        studentBtn.setPreferredSize(new Dimension(300, 60));
        studentBtn.addActionListener(e -> cardLayout.show(mainPanel, "STUDENT_LOGIN"));
        gbc.gridy = 1;
        center.add(studentBtn, gbc);
        
        // Faculty button
        JButton facultyBtn = createStyledButton("Faculty Login", SECONDARY_COLOR);
        facultyBtn.setPreferredSize(new Dimension(300, 60));
        facultyBtn.addActionListener(e -> cardLayout.show(mainPanel, "FACULTY_LOGIN"));
        gbc.gridy = 2;
        center.add(facultyBtn, gbc);
        
        // Admin button
        JButton adminBtn = createStyledButton("Admin Dashboard", new Color(239, 68, 68));
        adminBtn.setPreferredSize(new Dimension(300, 60));
        adminBtn.addActionListener(e -> cardLayout.show(mainPanel, "ADMIN_LOGIN"));
        gbc.gridy = 3;
        center.add(adminBtn, gbc);
        
        // Statistics
        JLabel stats = new JLabel(getSystemStats());
        stats.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stats.setForeground(Color.GRAY);
        gbc.gridy = 4;
        gbc.insets = new Insets(30, 20, 15, 20);
        center.add(stats, gbc);
        
        panel.add(center, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Student Login Screen
     */
    private JPanel createStudentLoginScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        
        // Header
        JPanel header = createHeader("Student Login", PRIMARY_COLOR);
        panel.add(header, BorderLayout.NORTH);
        
        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // USN
        gbc.gridy = 0;
        form.add(new JLabel("USN:"), gbc);
        JTextField usnField = new JTextField(20);
        gbc.gridx = 1;
        form.add(usnField, gbc);
        
        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Name (Optional):"), gbc);
        JTextField nameField = new JTextField(20);
        gbc.gridx = 1;
        form.add(nameField, gbc);
        
        // Year
        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Year:"), gbc);
        JComboBox<Integer> yearCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        gbc.gridx = 1;
        form.add(yearCombo, gbc);
        
        // Semester
        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(new JLabel("Semester:"), gbc);
        JComboBox<Integer> semesterCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        gbc.gridx = 1;
        form.add(semesterCombo, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND);
        
        JButton loginBtn = createStyledButton("Login", SUCCESS_COLOR);
        loginBtn.addActionListener(e -> {
            try {
                String usn = usnField.getText().trim().toUpperCase();
                String name = nameField.getText().trim();
                int year = (Integer) yearCombo.getSelectedItem();
                int semester = (Integer) semesterCombo.getSelectedItem();
                
                InputValidator.validateUSN(usn);
                Student student = name.isEmpty() ? 
                    new Student(usn, year, semester) : 
                    new Student(usn, name, year, semester);
                
                showStudentDashboard(student);
            } catch (InvalidInputException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(loginBtn);
        
        JButton backBtn = createStyledButton("Back", Color.GRAY);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(buttonPanel, gbc);
        
        panel.add(form, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Faculty Login Screen
     */
    private JPanel createFacultyLoginScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        
        JPanel header = createHeader("Faculty Login", SECONDARY_COLOR);
        panel.add(header, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField deptField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Faculty ID:"), gbc);
        gbc.gridx = 1;
        form.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        form.add(nameField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        form.add(deptField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND);
        
        JButton loginBtn = createStyledButton("Login", SUCCESS_COLOR);
        loginBtn.addActionListener(e -> {
            Faculty faculty = new Faculty(idField.getText().trim(), 
                nameField.getText().trim(), deptField.getText().trim());
            showFacultyDashboard(faculty);
        });
        buttonPanel.add(loginBtn);
        
        JButton backBtn = createStyledButton("Back", Color.GRAY);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        form.add(buttonPanel, gbc);
        
        panel.add(form, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Admin Login Screen
     */
    private JPanel createAdminLoginScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        
        JPanel header = createHeader("Admin Login", ERROR_COLOR);
        panel.add(header, BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JTextField userField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        form.add(userField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        form.add(passField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(BACKGROUND);
        
        JButton loginBtn = createStyledButton("Login", SUCCESS_COLOR);
        loginBtn.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            
            if (user.equalsIgnoreCase("admin") && pass.equals("admin123")) {
                Admin admin = new Admin("admin", "System Administrator");
                showAdminDashboard(admin);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        buttonPanel.add(loginBtn);
        
        JButton backBtn = createStyledButton("Back", Color.GRAY);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(buttonPanel, gbc);
        
        panel.add(form, BorderLayout.CENTER);
        return panel;
    }
    
    /**
     * Student Dashboard
     */
    private void showStudentDashboard(Student student) {
        JFrame dashboard = new JFrame("Student Dashboard - " + student.getUserName());
        dashboard.setSize(800, 600);
        dashboard.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel header = createHeader("Welcome, " + student.getUserName() + "!", PRIMARY_COLOR);
        panel.add(header, BorderLayout.NORTH);
        
        JPanel content = new JPanel(new GridLayout(2, 1, 20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setBackground(BACKGROUND);
        
        JButton submitBtn = createStyledButton("Submit Feedback", SUCCESS_COLOR);
        submitBtn.setPreferredSize(new Dimension(300, 80));
        submitBtn.addActionListener(e -> showFeedbackForm(student, dashboard));
        content.add(submitBtn);
        
        JButton viewBtn = createStyledButton("View My Feedback", PRIMARY_COLOR);
        viewBtn.addActionListener(e -> showStudentFeedback(student));
        content.add(viewBtn);
        
        panel.add(content, BorderLayout.CENTER);
        
        JButton backBtn = createStyledButton("Logout", Color.GRAY);
        backBtn.addActionListener(e -> dashboard.dispose());
        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        
        dashboard.add(panel);
        dashboard.setVisible(true);
    }
    
    /**
     * Feedback Form
     */
    private void showFeedbackForm(Student student, JFrame parent) {
        JDialog dialog = new JDialog(parent, "Submit Feedback", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(parent);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Subject selection
        SubjectManager sm = feedbackService.getSubjectManager();
        List<Subject> subjects = sm.getSubjectsForSemester(student.getSemester());
        String[] subjectNames = new String[subjects.size() + 1];
        subjectNames[0] = "-- Enter Manually --";
        for (int i = 0; i < subjects.size(); i++) {
            subjectNames[i + 1] = subjects.get(i).getSubjectName();
        }
        
        JComboBox<String> subjectCombo = new JComboBox<>(subjectNames);
        JTextField subjectCodeField = new JTextField(15);
        JTextField subjectNameField = new JTextField(15);
        JTextField facultyIdField = new JTextField(15);
        JTextField facultyNameField = new JTextField(15);
        JSlider ratingSlider = new JSlider(1, 5, 3);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        JTextArea commentsArea = new JTextArea(4, 20);
        
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        panel.add(subjectCombo, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Subject Code:"), gbc);
        gbc.gridx = 1;
        panel.add(subjectCodeField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Subject Name:"), gbc);
        gbc.gridx = 1;
        panel.add(subjectNameField, gbc);
        
        subjectCombo.addActionListener(e -> {
            int idx = subjectCombo.getSelectedIndex();
            if (idx > 0) {
                Subject subj = subjects.get(idx - 1);
                subjectCodeField.setText(subj.getSubjectCode());
                subjectNameField.setText(subj.getSubjectName());
            }
        });
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Faculty ID:"), gbc);
        gbc.gridx = 1;
        panel.add(facultyIdField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Faculty Name:"), gbc);
        gbc.gridx = 1;
        panel.add(facultyNameField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Rating (1-5):"), gbc);
        gbc.gridx = 1;
        panel.add(ratingSlider, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Comments:"), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(commentsArea), gbc);
        
        JButton submitBtn = createStyledButton("Submit", SUCCESS_COLOR);
        submitBtn.addActionListener(e -> {
            try {
                feedbackService.submitFeedback(
                    student.getUsn(),
                    student.getUserName(),
                    student.getYear(),
                    student.getSemester(),
                    subjectCodeField.getText(),
                    subjectNameField.getText(),
                    facultyIdField.getText(),
                    facultyNameField.getText(),
                    ratingSlider.getValue(),
                    commentsArea.getText()
                );
                JOptionPane.showMessageDialog(dialog, "Feedback submitted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        row++;
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitBtn, gbc);
        
        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }
    
    /**
     * Show student feedback
     */
    private void showStudentFeedback(Student student) {
        List<Feedback> feedbackList = feedbackService.getRepository().getFeedbackByUSN(student.getUsn());
        
        StringBuilder text = new StringBuilder();
        text.append("Your Feedback History\n\n");
        
        if (feedbackList.isEmpty()) {
            text.append("No feedback submitted yet.");
        } else {
            for (Feedback fb : feedbackList) {
                text.append("Subject: ").append(fb.getSubjectName()).append("\n");
                text.append("Faculty: ").append(fb.getFacultyName()).append("\n");
                text.append("Rating: ").append(fb.getRating()).append("/5\n");
                text.append("Date: ").append(fb.getTimestamp()).append("\n");
                text.append("Comments: ").append(fb.getComments()).append("\n");
                text.append("---\n\n");
            }
        }
        
        JTextArea area = new JTextArea(text.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), 
            "My Feedback", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Faculty Dashboard
     */
    private void showFacultyDashboard(Faculty faculty) {
        JFrame dashboard = new JFrame("Faculty Dashboard - " + faculty.getUserName());
        dashboard.setSize(800, 600);
        dashboard.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeader("Faculty Dashboard", SECONDARY_COLOR), BorderLayout.NORTH);
        
        JPanel content = new JPanel(new GridLayout(2, 1, 20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton viewBtn = createStyledButton("View My Feedback", PRIMARY_COLOR);
        viewBtn.addActionListener(e -> showFacultyFeedback(faculty));
        content.add(viewBtn);
        
        JButton statsBtn = createStyledButton("View Statistics", SUCCESS_COLOR);
        statsBtn.addActionListener(e -> showFacultyStats(faculty));
        content.add(statsBtn);
        
        panel.add(content, BorderLayout.CENTER);
        
        JButton backBtn = createStyledButton("Logout", Color.GRAY);
        backBtn.addActionListener(e -> dashboard.dispose());
        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        
        dashboard.add(panel);
        dashboard.setVisible(true);
    }
    
    private void showFacultyFeedback(Faculty faculty) {
        List<Feedback> feedbackList = feedbackService.getRepository()
            .getFeedbackByFaculty(faculty.getUserId());
        
        StringBuilder text = new StringBuilder();
        text.append("Feedback for ").append(faculty.getUserName()).append("\n\n");
        
        if (feedbackList.isEmpty()) {
            text.append("No feedback received yet.");
        } else {
            double avg = feedbackService.getRepository().getAverageRatingByFaculty(faculty.getUserId());
            text.append("Total Feedbacks: ").append(feedbackList.size()).append("\n");
            text.append(String.format("Average Rating: %.2f/5.0\n\n", avg));
            text.append("---\n\n");
            
            for (Feedback fb : feedbackList) {
                text.append("Subject: ").append(fb.getSubjectName()).append("\n");
                text.append("Rating: ").append(fb.getRating()).append("/5\n");
                text.append("Comments: ").append(fb.getComments()).append("\n");
                text.append("Date: ").append(fb.getTimestamp()).append("\n\n");
            }
        }
        
        JTextArea area = new JTextArea(text.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(this, scroll, "My Feedback", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showFacultyStats(Faculty faculty) {
        FeedbackRepository repo = feedbackService.getRepository();
        Map<Integer, Integer> dist = repo.getRatingDistributionByFaculty(faculty.getUserId());
        
        StringBuilder text = new StringBuilder();
        text.append("Rating Distribution\n\n");
        for (int i = 5; i >= 1; i--) {
            text.append(i).append(" Stars: ").append(dist.get(i)).append("\n");
        }
        
        JOptionPane.showMessageDialog(this, text.toString(), "Statistics", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Admin Dashboard
     */
    private void showAdminDashboard(Admin admin) {
        JFrame dashboard = new JFrame("Admin Dashboard");
        dashboard.setSize(900, 700);
        dashboard.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createHeader("Admin Dashboard", ERROR_COLOR), BorderLayout.NORTH);
        
        JPanel content = new JPanel(new GridLayout(4, 2, 15, 15));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setBackground(BACKGROUND);
        
        JButton viewAllBtn = createStyledButton("View All Feedback", PRIMARY_COLOR);
        viewAllBtn.addActionListener(e -> showAllFeedback());
        content.add(viewAllBtn);
        
        JButton subjectBtn = createStyledButton("Subject Reports", SECONDARY_COLOR);
        subjectBtn.addActionListener(e -> showSubjectReports());
        content.add(subjectBtn);
        
        JButton facultyBtn = createStyledButton("Faculty Reports", SUCCESS_COLOR);
        facultyBtn.addActionListener(e -> showFacultyReports());
        content.add(facultyBtn);
        
        JButton graphBtn = createStyledButton("Generate HTML Report", new Color(249, 115, 22));
        graphBtn.addActionListener(e -> generateHTMLReport());
        content.add(graphBtn);
        
        JButton filterBtn = createStyledButton("Filter Feedback", new Color(59, 130, 246));
        filterBtn.addActionListener(e -> showFilterDialog());
        content.add(filterBtn);
        
        JButton statsBtn = createStyledButton("System Statistics", new Color(168, 85, 247));
        statsBtn.addActionListener(e -> showSystemStats());
        content.add(statsBtn);
        
        panel.add(content, BorderLayout.CENTER);
        
        JButton backBtn = createStyledButton("Logout", Color.GRAY);
        backBtn.addActionListener(e -> dashboard.dispose());
        JPanel bottom = new JPanel();
        bottom.add(backBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        
        dashboard.add(panel);
        dashboard.setVisible(true);
    }
    
    private void showAllFeedback() {
        List<Feedback> all = feedbackService.getRepository().getAllFeedback();
        StringBuilder text = new StringBuilder();
        text.append("All Feedback Entries (").append(all.size()).append(")\n\n");
        
        for (Feedback fb : all) {
            text.append("USN: ").append(fb.getUsn()).append("\n");
            text.append("Subject: ").append(fb.getSubjectName()).append("\n");
            text.append("Faculty: ").append(fb.getFacultyName()).append("\n");
            text.append("Rating: ").append(fb.getRating()).append("/5\n");
            text.append("Date: ").append(fb.getTimestamp()).append("\n");
            text.append("---\n\n");
        }
        
        JTextArea area = new JTextArea(text.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(700, 500));
        JOptionPane.showMessageDialog(this, scroll, "All Feedback", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showSubjectReports() {
        List<String> subjects = feedbackService.getRepository().getUniqueSubjects();
        if (subjects.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No subjects found");
            return;
        }
        
        String subject = (String) JOptionPane.showInputDialog(this, "Select Subject:", 
            "Subject Reports", JOptionPane.QUESTION_MESSAGE, null, 
            subjects.toArray(), subjects.get(0));
        
        if (subject != null) {
            double avg = feedbackService.getRepository().getAverageRatingBySubject(subject);
            int count = feedbackService.getRepository().getFeedbackBySubject(subject).size();
            
            String msg = String.format("Subject: %s\n\nTotal Feedback: %d\nAverage Rating: %.2f/5.0", 
                subject, count, avg);
            JOptionPane.showMessageDialog(this, msg, "Subject Report", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showFacultyReports() {
        List<String> faculty = feedbackService.getRepository().getUniqueFaculty();
        if (faculty.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No faculty found");
            return;
        }
        
        String fac = (String) JOptionPane.showInputDialog(this, "Select Faculty:", 
            "Faculty Reports", JOptionPane.QUESTION_MESSAGE, null, 
            faculty.toArray(), faculty.get(0));
        
        if (fac != null) {
            String facId = fac.substring(fac.lastIndexOf("(") + 1, fac.lastIndexOf(")"));
            double avg = feedbackService.getRepository().getAverageRatingByFaculty(facId);
            int count = feedbackService.getRepository().getFeedbackByFaculty(facId).size();
            
            String msg = String.format("Faculty: %s\n\nTotal Feedback: %d\nAverage Rating: %.2f/5.0", 
                fac, count, avg);
            JOptionPane.showMessageDialog(this, msg, "Faculty Report", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void generateHTMLReport() {
        String filename = "feedback_analytics_dashboard.html";
        GraphGenerator.generateHTMLReport(feedbackService.getRepository(), filename);
        
        int result = JOptionPane.showConfirmDialog(this, 
            "HTML report generated: " + filename + "\n\nOpen in browser?",
            "Success", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                Desktop.getDesktop().browse(new java.io.File(filename).toURI());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please open the file manually: " + filename);
            }
        }
    }
    
    private void showFilterDialog() {
        String[] options = {"By Semester", "By Year", "By Subject", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Filter by:", "Filter Feedback",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        
        if (choice == 0) { // Semester
            Integer sem = (Integer) JOptionPane.showInputDialog(this, "Select Semester:", 
                "Filter", JOptionPane.QUESTION_MESSAGE, null, 
                new Integer[]{1,2,3,4,5,6,7,8}, 1);
            if (sem != null) {
                List<Feedback> filtered = feedbackService.getRepository().getFeedbackBySemester(sem);
                showFilteredResults(filtered, "Semester " + sem);
            }
        }
    }
    
    private void showFilteredResults(List<Feedback> filtered, String filterName) {
        StringBuilder text = new StringBuilder();
        text.append("Filtered Results (").append(filterName).append(")\n\n");
        text.append("Total: ").append(filtered.size()).append(" entries\n\n");
        
        for (Feedback fb : filtered) {
            text.append(fb.toString()).append("\n");
        }
        
        JTextArea area = new JTextArea(text.toString());
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(700, 400));
        JOptionPane.showMessageDialog(this, scroll, "Filtered Results", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showSystemStats() {
        String stats = getSystemStats();
        JOptionPane.showMessageDialog(this, stats, "System Statistics", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Helper methods
     */
    private JPanel createHeader(String title, Color bgColor) {
        JPanel header = new JPanel();
        header.setBackground(bgColor);
        header.setPreferredSize(new Dimension(900, 80));
        
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        header.add(label);
        
        return header;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private String getSystemStats() {
        FeedbackRepository repo = feedbackService.getRepository();
        int total = repo.getFeedbackCount();
        int subjects = repo.getUniqueSubjects().size();
        int faculty = repo.getUniqueFaculty().size();
        
        return String.format("<html>System Statistics<br/>Total Feedback: %d | Subjects: %d | Faculty: %d</html>", 
            total, subjects, faculty);
    }
    
    /**
     * Main method - Application entry point
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            FeedbackSystemGUI gui = new FeedbackSystemGUI();
            gui.setVisible(true);
        });
    }
}
