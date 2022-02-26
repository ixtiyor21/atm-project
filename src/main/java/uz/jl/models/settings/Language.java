package uz.jl.models.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Elmurodov Javohir, Tue 12:18 PM. 12/7/2021
 */
@Getter
@AllArgsConstructor
public enum Language {
    UZ("UZ", "Uzbek"),
    RU("RU", "Russian"),
    EN("EN", "English");

    private final String name;
    private final String code;


    public static Language findByCode(String code) {
        for (Language language : values()) {
            if (language.code.equalsIgnoreCase(code))
                return language;
        }
        return findByCode(EN.getCode());
    }

    @Override
    public String toString() {
        return name + " -> " + code;
    }
}
