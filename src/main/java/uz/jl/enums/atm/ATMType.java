package uz.jl.enums.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Mon 12:12 PM. 11/29/2021
 */
@Getter
@AllArgsConstructor
public enum ATMType {
    UZCARD("Uzcard"),
    HUMO("Humo"),
    VISA("Visa"),
    VISA_UZCARD("Visa -> Uzcard"),
    VISA_HUMO("Visa -> Humo"),
    UNDEFINED("undefined");
    private String description;
    public static void listType() {
        for (ATMType value : values()) {
            if (!value.getDescription().equals("undefined")) {
                Print.println(Color.PURPLE, value.getDescription());
            }
        }
    }

    public static ATMType get(String description) {
        for (ATMType value : values()) {
            if (value.getDescription().equalsIgnoreCase(description)) {
                return value;
            }
        }
        return UNDEFINED;
    }
}
