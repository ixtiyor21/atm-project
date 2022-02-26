package uz.jl.mapper;

import uz.jl.dto.atm.ATMDto;
import uz.jl.models.atm.ATMEntity;

import java.util.Objects;

/**
 * @author Elmurodov Javohir, Mon 11:18 AM. 12/6/2021
 */
public class ATMMapper {
    private static ATMMapper mapper;

    public static ATMMapper getInstance() {
        if (Objects.isNull(mapper)) {
            mapper = new ATMMapper();
        }
        return mapper;
    }
}

