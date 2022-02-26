package uz.jl.services.auth;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIException;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.IBaseCrudService;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;
import java.util.Objects;

import static uz.jl.ui.menus.MenuKey.*;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AuthService
        extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {
    private static AuthService service;

    public static AuthService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AuthService(repository, mapper);
        }
        return service;
    }

    protected AuthService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    Session session = Session.getInstance();
    public ResponseEntity<String> login(String username, String password) {
        AuthUser user = repository.findByUserName(username);
        if (Objects.isNull(user) || !user.getPassword().equals(password))
            return new ResponseEntity<>("Bad Credentials", HttpStatus.HTTP_400);
        if (user.getStatus().equals(UserStatus.BLOCKED)) {
            return new ResponseEntity<>("This user is blocked", HttpStatus.HTTP_401);
        }if(Objects.nonNull(user.getBranchId())){
            Branch branch=BranchDao.getById(user.getBranchId());
            if(Objects.nonNull(branch)&&branch.getStatus().equals(Status.BLOCKED)){
                return new ResponseEntity<>("This user's branch is blocked");
            }
        }
        user.setStatus(UserStatus.ACTIVE);
        session.setUser(user);
        return new ResponseEntity<>("success", HttpStatus.HTTP_200);
    }

    public void logout() {
        AuthUser user = session.getUser();
        user.setStatus(UserStatus.NON_ACTIVE);
        session.setUser(user);
    }

    public ResponseEntity<String> profile() {
        try {
            AuthUser authUser = session.getUser();
            println("Username : " + authUser.getUsername());
            println("Password : ******* ");
            println(RESET_USERNAME.get(session.getLanguage()) + " -> " + RESET_USERNAME);
            println(RESET_PASSWORD.get(session.getLanguage()) + " -> " + RESET_PASSWORD);
            println(RESET_LANGUAGE.get(session.getLanguage()) + " -> " + RESET_LANGUAGE);
            String choice = getStr("?:");
            MenuKey key = MenuKey.getByValue(choice);
            switch (key) {
                case RESET_USERNAME -> resetUsername(authUser);
                case RESET_PASSWORD -> resetPassword(authUser);
                case RESET_LANGUAGE -> resetLanguage(authUser);
            }
            return new ResponseEntity<>("success", HttpStatus.HTTP_200);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.getStatusByCode(e.getCode()));
        }
    }

    private void resetLanguage(AuthUser authUser) {
        Language language=Language.EN;
        String langChoice = Input.getStr(String.format("""
                Please choose language -> 
                1. %s
                2. %s
                3. %s
                ? : """, Language.UZ.getName(), Language.EN.getName(), Language.RU.getName()));
        if ("1".equals(langChoice)) {
            language = Language.UZ;
        }else if ("3".equals(langChoice)) {
            language= Language.RU;
        } else {
            Print.println(Color.RED, "Wrong choice");
            resetLanguage(authUser);
        }
        session.setLanguage(language);
    }


    private void resetUsername(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setUsername(getStr("Enter new username : "));
            return;
        }
        throw new APIException("Bad Credentials", HttpStatus.HTTP_400);
    }

    private void resetPassword(AuthUser authUser) throws APIException {
        String password1 = getStr("Enter old password : ");
        if (authUser.getPassword().equals(password1)) {
            authUser.setPassword(getStr("Enter new password : "));
            return;
        }
        throw new APIException("Bad Credentials", HttpStatus.HTTP_400);
    }
}
