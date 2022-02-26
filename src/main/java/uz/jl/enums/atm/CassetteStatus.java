package uz.jl.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Mon 12:07 PM. 11/29/2021
 */

@Getter
@AllArgsConstructor
public enum CassetteStatus {
    BLOCKED(-1,"blocked"),
    ACTIVE(0,"active");
    private final int code;
    private final String description;
    public static void listType() {
        for (CassetteStatus value : values()) {
            Print.println(Color.PURPLE, value.getDescription());
        }
    }

    public static CassetteStatus get(String description) {
        for (CassetteStatus value : values()) {
            if (value.getDescription().equalsIgnoreCase(description)) {
                return value;
            }
        }
        return null;
    }
}
