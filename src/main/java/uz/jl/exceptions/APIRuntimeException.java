package uz.jl.exceptions;

/**
 * @author Elmurodov Javohir, Thu 9:06 AM. 12/9/2021
 */
public class APIRuntimeException extends RuntimeException {
    private final Integer code;

    public APIRuntimeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public APIRuntimeException(String message) {
        super(message);
        this.code = 200;
    }
}
