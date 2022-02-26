package uz.jl.enums.auth;

/**
 * @author Elmurodov Javohir, Mon 11:57 AM. 12/6/2021
 */
public enum UserStatus {
    ACTIVE(0),
    NON_ACTIVE(-1),
    BLOCKED(-2);

    private final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
