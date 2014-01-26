package by.epam.library.exceptions;

public class NotPositiveException extends Exception {
    String error = "Not positive number";

    public NotPositiveException(String err) {
        error = err;
    }

    public NotPositiveException() {
    }

    @Override
    public String toString() {
        return error;
    }
}
