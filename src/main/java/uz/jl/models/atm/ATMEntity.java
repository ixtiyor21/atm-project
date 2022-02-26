package uz.jl.models.atm;

import lombok.*;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.models.Auditable;

import java.text.MessageFormat;

/**
 * @author Elmurodov Javohir, Mon 12:10 PM. 11/29/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ATMEntity extends Auditable {
    private String bankId;
    private ATMType type;
    private String name;
    private ATMStatus status;
    private double latitude;
    private double longitude;
    private Cassette cassette1;
    private Cassette cassette2;
    private Cassette cassette3;
    private Cassette cassette4;
    private String branchId;

    @Override
    public String toString() {
        return MessageFormat.format("name : {0} , type : {1} , status : {2}",name,type,status);
    }
}


