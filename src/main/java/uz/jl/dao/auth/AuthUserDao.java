package uz.jl.dao.auth;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.BaseDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.auth.AuthUser;

import java.util.List;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 8:50 AM. 12/9/2021
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthUserDao extends BaseDao<AuthUser> {
    //region SINGLETON
    private static AuthUserDao dao;

    public static AuthUserDao getInstance() {
        if (Objects.isNull(dao))
            dao = new AuthUserDao();
        return dao;
    }

    private static FRWAuthUser frwAuthUser = FRWAuthUser.getInstance();
    public List<AuthUser> users = frwAuthUser.getAll();
    public void writeAll() {
        frwAuthUser.writeAll(users);
    }

    public AuthUser findByUserName(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getDeleted() != -1 && user.getUsername().equals(username)) return user;
        }
        // TODO: 12/9/2021 translate please
        return null;
    }
}
