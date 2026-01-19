/**
 * Custom Exception for Invalid Input
 * Demonstrates OOP concept: Exception Handling with custom exceptions
 */
public class InvalidInputException extends Exception {
    private String fieldName;
    private String invalidValue;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String fieldName, String invalidValue, String message) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String toString() {
        if (fieldName != null) {
            return String.format("InvalidInputException: Field '%s' has invalid value '%s'. %s", 
                               fieldName, invalidValue, getMessage());
        }
        return "InvalidInputException: " + getMessage();
    }
}
