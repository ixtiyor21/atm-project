package uz.jl.enums.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir, Mon 12:21 PM. 12/6/2021
 */

@Getter
@AllArgsConstructor
public enum CardType {
    UZCARD("8600"),
    HUMO("9860"),
    MASTER_CARD("4853"),
    VISA("4735"),
    COBAGE("6330"),
    UNION_PAY("6262"),
    UNDEFINED("0");
    private final String code;

    public static CardType getType(String type) {
        for (CardType value : values()) {
            if (type.equalsIgnoreCase(value.name()))
                return value;
        }
        return UNDEFINED;
    }
}
