import java.io.*;
import java.util.*;

/**
 * JSONHandler Class - Handles JSON file operations without external libraries
 * Demonstrates OOP concepts: File I/O, Exception Handling, Methods
 * Simple JSON implementation for reading/writing feedback data
 */
public class JSONHandler {
    
    /**
     * Save feedback list to JSON file
     */
    public static void saveFeedbackToJSON(List<Feedback> feedbackList, String filename) 
            throws DataStorageException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("[");
            
            for (int i = 0; i < feedbackList.size(); i++) {
                Feedback fb = feedbackList.get(i);
                writer.println("  {");
                writer.println("    \"feedbackId\": \"" + escapeJson(fb.getFeedbackId()) + "\",");
                writer.println("    \"usn\": \"" + escapeJson(fb.getUsn()) + "\",");
                writer.println("    \"studentName\": \"" + escapeJson(fb.getStudentName()) + "\",");
                writer.println("    \"year\": " + fb.getYear() + ",");
                writer.println("    \"semester\": " + fb.getSemester() + ",");
                writer.println("    \"subjectCode\": \"" + escapeJson(fb.getSubjectCode()) + "\",");
                writer.println("    \"subjectName\": \"" + escapeJson(fb.getSubjectName()) + "\",");
                writer.println("    \"facultyId\": \"" + escapeJson(fb.getFacultyId()) + "\",");
                writer.println("    \"facultyName\": \"" + escapeJson(fb.getFacultyName()) + "\",");
                writer.println("    \"rating\": " + fb.getRating() + ",");
                writer.println("    \"comments\": \"" + escapeJson(fb.getComments()) + "\",");
                writer.println("    \"timestamp\": \"" + escapeJson(fb.getTimestamp()) + "\"");
                writer.print("  }");
                
                if (i < feedbackList.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            
            writer.println("]");
            
        } catch (IOException e) {
            throw new DataStorageException("WRITE", filename, 
                "Failed to write feedback data to JSON file", e);
        }
    }
    
    /**
     * Load feedback list from JSON file
     */
    public static List<Feedback> loadFeedbackFromJSON(String filename) 
            throws DataStorageException {
        List<Feedback> feedbackList = new ArrayList<>();
        File file = new File(filename);
        
        if (!file.exists()) {
            return feedbackList; // Return empty list if file doesn't exist
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line).append("\n");
            }
            
            // Parse JSON manually (simple approach)
            String content = jsonContent.toString();
            String[] feedbackObjects = content.split("\\},\\s*\\{");
            
            for (String feedbackStr : feedbackObjects) {
                if (feedbackStr.trim().isEmpty() || !feedbackStr.contains("feedbackId")) {
                    continue;
                }
                
                try {
                    Feedback fb = parseFeedbackObject(feedbackStr);
                    if (fb != null) {
                        feedbackList.add(fb);
                    }
                } catch (Exception e) {
                    System.err.println("Warning: Failed to parse feedback entry: " + e.getMessage());
                }
            }
            
        } catch (IOException e) {
            throw new DataStorageException("READ", filename, 
                "Failed to read feedback data from JSON file", e);
        }
        
        return feedbackList;
    }
    
    /**
     * Parse a single feedback object from JSON string
     */
    private static Feedback parseFeedbackObject(String jsonStr) {
        try {
            String feedbackId = extractValue(jsonStr, "feedbackId");
            String usn = extractValue(jsonStr, "usn");
            String studentName = extractValue(jsonStr, "studentName");
            int year = Integer.parseInt(extractValue(jsonStr, "year"));
            int semester = Integer.parseInt(extractValue(jsonStr, "semester"));
            String subjectCode = extractValue(jsonStr, "subjectCode");
            String subjectName = extractValue(jsonStr, "subjectName");
            String facultyId = extractValue(jsonStr, "facultyId");
            String facultyName = extractValue(jsonStr, "facultyName");
            int rating = Integer.parseInt(extractValue(jsonStr, "rating"));
            String comments = extractValue(jsonStr, "comments");
            String timestamp = extractValue(jsonStr, "timestamp");
            
            Feedback fb = new Feedback(usn, studentName, year, semester, 
                                      subjectCode, subjectName, facultyId, 
                                      facultyName, rating, comments);
            fb.setFeedbackId(feedbackId);
            fb.setTimestamp(timestamp);
            
            return fb;
        } catch (Exception e) {
            System.err.println("Error parsing feedback: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Extract value from JSON string for a given key
     */
    private static String extractValue(String jsonStr, String key) {
        String pattern = "\"" + key + "\":\\s*";
        int startIndex = jsonStr.indexOf(pattern);
        
        if (startIndex == -1) {
            return "";
        }
        
        startIndex += pattern.length();
        
        // Check if value is a string (starts with quote)
        if (jsonStr.charAt(startIndex) == '"') {
            startIndex++; // Skip opening quote
            int endIndex = jsonStr.indexOf("\"", startIndex);
            if (endIndex == -1) {
                return "";
            }
            return unescapeJson(jsonStr.substring(startIndex, endIndex));
        } else {
            // Numeric value
            int endIndex = jsonStr.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = jsonStr.indexOf("}", startIndex);
            }
            if (endIndex == -1) {
                endIndex = jsonStr.indexOf("\n", startIndex);
            }
            if (endIndex == -1) {
                return "";
            }
            return jsonStr.substring(startIndex, endIndex).trim();
        }
    }
    
    /**
     * Escape special characters for JSON
     */
    private static String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * Unescape JSON special characters
     */
    private static String unescapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\\"", "\"")
                  .replace("\\\\", "\\")
                  .replace("\\n", "\n")
                  .replace("\\r", "\r")
                  .replace("\\t", "\t");
    }
    
    /**
     * Save subjects to JSON file
     */
    public static void saveSubjectsToJSON(List<Subject> subjects, String filename) 
            throws DataStorageException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("[");
            
            for (int i = 0; i < subjects.size(); i++) {
                Subject sub = subjects.get(i);
                writer.println("  {");
                writer.println("    \"subjectCode\": \"" + escapeJson(sub.getSubjectCode()) + "\",");
                writer.println("    \"subjectName\": \"" + escapeJson(sub.getSubjectName()) + "\",");
                writer.println("    \"semester\": " + sub.getSemester() + ",");
                writer.println("    \"department\": \"" + escapeJson(sub.getDepartment()) + "\"");
                writer.print("  }");
                
                if (i < subjects.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            
            writer.println("]");
            
        } catch (IOException e) {
            throw new DataStorageException("WRITE", filename, 
                "Failed to write subjects to JSON file", e);
        }
    }
}
