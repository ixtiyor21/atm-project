package uz.jl.models.branch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.jl.enums.atm.Status;
import uz.jl.models.Auditable;

import java.util.Date;

@Getter
@Setter
@ToString(of = {"name","status"})
public class Branch extends Auditable {
    private String name;
    private Status status;
    private String bankId;

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")

    public Branch(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String name, Status status, String bankId) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.name = name;
        this.status = status;
        this.bankId = bankId;
    }
}
