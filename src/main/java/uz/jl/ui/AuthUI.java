package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.card.CardDao;
import uz.jl.enums.extras.Gender;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.utils.Input;

/**
 * @author Elmurodov Javohir, Wed 12:08 PM. 12/8/2021
 */
public class AuthUI extends BaseUI {
    static AuthService service = AuthService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void login() {
        String username = Input.getStr("username = ");
        String password = Input.getStr("password = ");
        ResponseEntity<String> response = service.login(username, password);
        showResponse(response);
    }

    public static void logout() {
        AuthUserDao.getInstance().writeAll();
        ATMDao.getInstance().writeAll();
        BranchDao.getInstance().writeAll();
        CardDao.getInstance().writeAll();
        PassportDao.getInstance().writeAll();
        service.logout();

    }

    public static void profile() {
        ResponseEntity<String> response = service.profile();
        showResponse(response);
    }
}
