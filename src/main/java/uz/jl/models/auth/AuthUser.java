package uz.jl.models.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.jl.configs.AppConfig;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.Auditable;
import uz.jl.models.settings.Language;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author Elmurodov Javohir, Mon 11:40 AM. 12/6/2021
 */
@Getter
@Setter
public class AuthUser extends Auditable {
    private String username;
    private String password;
    private String bankId;
    private String branchId;
    private Role role;
    private UserStatus status;
    private String phoneNumber;
    private Language language;

    public AuthUser() {
        super();
        this.role = Role.ANONYMOUS;
        this.language = AppConfig.language;
    }

    @Builder(builderMethodName = "childBuilder", buildMethodName = "childBuild")
    public AuthUser(Date createdAt, String createdBy, Date updatedAt, String updatedBy, int deleted, String username, String password, String bankId, String branchId, Role role, UserStatus status, String phoneNumber, Language language) {
        super(createdAt, createdBy, updatedAt, updatedBy, deleted);
        this.username = username;
        this.password = password;
        this.bankId = bankId;
        this.branchId = branchId;
        this.role = role;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.language = language;
    }


    public String show() {
        return String.format("Username: %s\nPassword: %s\nPhone: %s\nId: %s", username, password, phoneNumber, getId());
    }
}
