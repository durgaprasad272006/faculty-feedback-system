/**
 * Custom Exception for Data Storage Errors
 * Demonstrates OOP concept: Exception Handling
 */
public class DataStorageException extends Exception {
    private String operation;  // e.g., "READ", "WRITE", "DELETE"
    private String filePath;

    public DataStorageException(String message) {
        super(message);
    }

    public DataStorageException(String operation, String filePath, String message) {
        super(message);
        this.operation = operation;
        this.filePath = filePath;
    }

    public DataStorageException(String operation, String filePath, String message, Throwable cause) {
        super(message, cause);
        this.operation = operation;
        this.filePath = filePath;
    }

    public String getOperation() {
        return operation;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        if (operation != null && filePath != null) {
            return String.format("DataStorageException: %s operation failed on file '%s'. %s", 
                               operation, filePath, getMessage());
        }
        return "DataStorageException: " + getMessage();
    }
}
