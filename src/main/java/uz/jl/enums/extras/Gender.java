package uz.jl.enums.extras;

import uz.jl.enums.card.CardType;

/**
 * @author Elmurodov Javohir, Mon 12:30 PM. 12/6/2021
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER,
    UNDEFINED;
    public static Gender getType(String type) {
        for (Gender value : values()) {
            if (type.equalsIgnoreCase(value.name()))
                return value;
        }
        return UNDEFINED;
    }
}
