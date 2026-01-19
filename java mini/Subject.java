/**
 * Subject Class - Represents a subject/course
 * Demonstrates OOP concept: Encapsulation
 */
public class Subject {
    private String subjectCode;
    private String subjectName;
    private int semester;
    private String department;
    private String facultyId;
    private String facultyName;

    public Subject(String subjectCode, String subjectName, int semester, String department) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.department = department;
    }

    public Subject(String subjectCode, String subjectName, int semester, 
                   String department, String facultyId, String facultyName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.semester = semester;
        this.department = department;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getSemester() {
        return semester;
    }

    public String getDepartment() {
        return department;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    // Setters
    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (Semester %d)", subjectCode, subjectName, semester);
    }
}
