package request.decorators.validation;

public class RequestValidationException extends Exception {
    public RequestValidationException(String message) {
        super(message);
    }
}
