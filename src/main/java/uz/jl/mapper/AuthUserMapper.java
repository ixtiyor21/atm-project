package uz.jl.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 8:51 AM. 12/9/2021
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserMapper {
    private static AuthUserMapper mapper;

    public static AuthUserMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new AuthUserMapper();
        }
        return mapper;
    }
}
