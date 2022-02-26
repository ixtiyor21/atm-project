package uz.jl.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir, Mon 10:51 AM. 12/6/2021
 */
@Getter
@AllArgsConstructor
public enum Status {
    BLOCKED(-1),
    ACTIVE(0);
    private final int code;
}
