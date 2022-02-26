package uz.jl.enums.auth;

/**
 * @author Elmurodov Javohir, Mon 12:00 PM. 12/6/2021
 */
public enum Role {
    SUPER_ADMIN,
    ADMIN,
    EMPLOYEE,
    HR,
    CLIENT,
    ANONYMOUS;

    public boolean in(Role... roles) {
        for (Role role : roles) {
            if (role.equals(this)) return true;
        }
        return false;
    }

    public boolean notIn(Role... roles) {
        return !in(roles);
    }
}
