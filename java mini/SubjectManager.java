import java.util.*;

/**
 * SubjectManager Class - Manages predefined subjects and manual entries
 * Demonstrates OOP concepts: Data Management, Collections
 */
public class SubjectManager {
    private List<Subject> predefinedSubjects;
    private static final String SUBJECTS_FILE = "subjects_config.json";
    
    public SubjectManager() {
        this.predefinedSubjects = new ArrayList<>();
        initializePredefinedSubjects();
    }
    
    /**
     * Initialize predefined subjects for each semester
     */
    private void initializePredefinedSubjects() {
        // Semester 1
        predefinedSubjects.add(new Subject("18CS11", "Problem Solving with C", 1, "CSE"));
        predefinedSubjects.add(new Subject("18MAT11", "Engineering Mathematics-I", 1, "Common"));
        predefinedSubjects.add(new Subject("18PHY12", "Engineering Physics", 1, "Common"));
        predefinedSubjects.add(new Subject("18CHE12", "Engineering Chemistry", 1, "Common"));
        predefinedSubjects.add(new Subject("18ELE14", "Elements of Electronics", 1, "Common"));
        
        // Semester 2
        predefinedSubjects.add(new Subject("18CS21", "Data Structures with C", 2, "CSE"));
        predefinedSubjects.add(new Subject("18MAT21", "Engineering Mathematics-II", 2, "Common"));
        predefinedSubjects.add(new Subject("18PHY22", "Engineering Physics Lab", 2, "Common"));
        predefinedSubjects.add(new Subject("18CHE22", "Engineering Chemistry Lab", 2, "Common"));
        predefinedSubjects.add(new Subject("18PCD23", "Professional Communication", 2, "Common"));
        
        // Semester 3
        predefinedSubjects.add(new Subject("18CS31", "Object Oriented Programming with Java", 3, "CSE"));
        predefinedSubjects.add(new Subject("18CS32", "Data Structures and Applications", 3, "CSE"));
        predefinedSubjects.add(new Subject("18CS33", "Computer Organization", 3, "CSE"));
        predefinedSubjects.add(new Subject("18CS34", "Database Management Systems", 3, "CSE"));
        predefinedSubjects.add(new Subject("18MAT31", "Engineering Mathematics-III", 3, "CSE"));
        
        // Semester 4
        predefinedSubjects.add(new Subject("18CS41", "Design and Analysis of Algorithms", 4, "CSE"));
        predefinedSubjects.add(new Subject("18CS42", "Operating Systems", 4, "CSE"));
        predefinedSubjects.add(new Subject("18CS43", "Microcontroller and Embedded Systems", 4, "CSE"));
        predefinedSubjects.add(new Subject("18CS44", "Software Engineering", 4, "CSE"));
        predefinedSubjects.add(new Subject("18MAT41", "Engineering Mathematics-IV", 4, "CSE"));
        
        // Semester 5
        predefinedSubjects.add(new Subject("18CS51", "Computer Networks", 5, "CSE"));
        predefinedSubjects.add(new Subject("18CS52", "Automata Theory and Compiler Design", 5, "CSE"));
        predefinedSubjects.add(new Subject("18CS53", "Application Development using Python", 5, "CSE"));
        predefinedSubjects.add(new Subject("18CS54", "Unix Programming", 5, "CSE"));
        predefinedSubjects.add(new Subject("18CS55", "Environmental Studies", 5, "CSE"));
        
        // Semester 6
        predefinedSubjects.add(new Subject("18CS61", "Web Technology and Applications", 6, "CSE"));
        predefinedSubjects.add(new Subject("18CS62", "Machine Learning", 6, "CSE"));
        predefinedSubjects.add(new Subject("18CS63", "Cloud Computing", 6, "CSE"));
        predefinedSubjects.add(new Subject("18CS64", "Computer Graphics", 6, "CSE"));
        predefinedSubjects.add(new Subject("18CS65", "Mobile Application Development", 6, "CSE"));
        
        // Semester 7
        predefinedSubjects.add(new Subject("18CS71", "Artificial Intelligence", 7, "CSE"));
        predefinedSubjects.add(new Subject("18CS72", "Big Data Analytics", 7, "CSE"));
        predefinedSubjects.add(new Subject("18CS73", "Internet of Things", 7, "CSE"));
        predefinedSubjects.add(new Subject("18CS74", "Information Security", 7, "CSE"));
        
        // Semester 8
        predefinedSubjects.add(new Subject("18CS81", "Project Work", 8, "CSE"));
        predefinedSubjects.add(new Subject("18CS82", "Internship/Seminar", 8, "CSE"));
    }
    
    /**
     * Get subjects for a specific semester
     */
    public List<Subject> getSubjectsForSemester(int semester) {
        List<Subject> semesterSubjects = new ArrayList<>();
        for (Subject subject : predefinedSubjects) {
            if (subject.getSemester() == semester) {
                semesterSubjects.add(subject);
            }
        }
        return semesterSubjects;
    }
    
    /**
     * Display subjects for a semester
     */
    public void displaySubjectsForSemester(int semester) {
        List<Subject> subjects = getSubjectsForSemester(semester);
        
        if (subjects.isEmpty()) {
            System.out.println("No predefined subjects for semester " + semester);
            return;
        }
        
        System.out.println("\n--- Predefined Subjects for Semester " + semester + " ---");
        for (int i = 0; i < subjects.size(); i++) {
            Subject sub = subjects.get(i);
            System.out.println((i + 1) + ". " + sub);
        }
        System.out.println("0. Enter subject manually");
    }
    
    /**
     * Get subject by index for a semester
     */
    public Subject getSubjectByIndex(int semester, int index) {
        List<Subject> subjects = getSubjectsForSemester(semester);
        if (index >= 0 && index < subjects.size()) {
            return subjects.get(index);
        }
        return null;
    }
    
    /**
     * Create custom subject
     */
    public Subject createCustomSubject(String subjectCode, String subjectName, 
                                      int semester, String department) {
        return new Subject(subjectCode, subjectName, semester, department);
    }
    
    /**
     * Get all predefined subjects
     */
    public List<Subject> getAllSubjects() {
        return new ArrayList<>(predefinedSubjects);
    }
}
