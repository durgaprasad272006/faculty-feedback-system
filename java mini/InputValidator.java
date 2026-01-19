import java.util.regex.Pattern;

/**
 * InputValidator Class - Validates user inputs
 * Demonstrates OOP concepts: Encapsulation, Methods, Exception Handling
 */
public class InputValidator {
    
    // USN pattern: Typically like 1AB20CS001 (1 digit + 2 letters + 2 digits + 2 letters + 3 digits)
    private static final Pattern USN_PATTERN = Pattern.compile("^[0-9][A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{3}$");
    
    /**
     * Validate USN format
     */
    public static void validateUSN(String usn) throws InvalidInputException {
        if (usn == null || usn.trim().isEmpty()) {
            throw new InvalidInputException("USN", usn, "USN cannot be empty");
        }
        
        usn = usn.trim().toUpperCase();
        
        if (!USN_PATTERN.matcher(usn).matches()) {
            throw new InvalidInputException("USN", usn, 
                "Invalid USN format. Expected format: 1AB20CS001 (e.g., 1CS21CS001)");
        }
    }
    
    /**
     * Validate year (typically 1-4 for undergraduate)
     */
    public static void validateYear(int year) throws InvalidInputException {
        if (year < 1 || year > 4) {
            throw new InvalidInputException("Year", String.valueOf(year), 
                "Year must be between 1 and 4");
        }
    }
    
    /**
     * Validate semester (typically 1-8)
     */
    public static void validateSemester(int semester) throws InvalidInputException {
        if (semester < 1 || semester > 8) {
            throw new InvalidInputException("Semester", String.valueOf(semester), 
                "Semester must be between 1 and 8");
        }
    }
    
    /**
     * Validate rating (1-5 scale)
     */
    public static void validateRating(int rating) throws InvalidInputException {
        if (rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating", String.valueOf(rating), 
                "Rating must be between 1 and 5");
        }
    }
    
    /**
     * Validate subject name
     */
    public static void validateSubjectName(String subjectName) throws InvalidInputException {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new InvalidInputException("Subject Name", subjectName, 
                "Subject name cannot be empty");
        }
        
        if (subjectName.trim().length() < 3) {
            throw new InvalidInputException("Subject Name", subjectName, 
                "Subject name must be at least 3 characters long");
        }
    }
    
    /**
     * Validate faculty ID
     */
    public static void validateFacultyId(String facultyId) throws InvalidInputException {
        if (facultyId == null || facultyId.trim().isEmpty()) {
            throw new InvalidInputException("Faculty ID", facultyId, 
                "Faculty ID cannot be empty");
        }
    }
    
    /**
     * Sanitize string input
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return "";
        }
        return input.trim();
    }
    
    /**
     * Validate non-empty string
     */
    public static void validateNotEmpty(String fieldName, String value) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, value, 
                fieldName + " cannot be empty");
        }
    }
}
