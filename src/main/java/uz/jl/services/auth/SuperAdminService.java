package uz.jl.services.auth;

import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.services.EditEntity;
import uz.jl.services.IBaseCrudService;

import java.util.ArrayList;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;

/**
 * @author Bakhromjon Fri, 11:21 AM 12/10/2021
 */
public class SuperAdminService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper>
        implements IBaseCrudService<AuthUser>, EditEntity {
    private static SuperAdminService superAdminService;

    private SuperAdminService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static SuperAdminService getInstance() {
        if (Objects.isNull(superAdminService)) {
            superAdminService = new SuperAdminService(AuthUserDao.getInstance(), AuthUserMapper.getInstance());
        }
        return superAdminService;
    }

    public AuthUserDao authUserDao = AuthUserDao.getInstance();

    @Override
    public ResponseEntity<String> create(AuthUser admin) {
        authUserDao.users.add(admin);
        return new ResponseEntity<>("Succesfully created", HttpStatus.HTTP_201);
    }

    @Override
    public ResponseEntity<String> delete(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        getAdmins().get(choiceN).setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    @Override
    public ResponseEntity<String> block(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        getUnBlockedAdmins().get(choiceN).setStatus(UserStatus.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }
    @Override
    public ResponseEntity<String> unBlock(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        getBlockedAdmins().get(choiceN).setStatus(UserStatus.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<ArrayList<AuthUser>> list() {
        if (getAdmins().size() == 0) {
            return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getAdmins(), HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> blockedAdminList() {
        if (getBlockedAdmins().size() == 0) {
            return new ResponseEntity<>(getBlockedAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getBlockedAdmins(), HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> unBlockedAdminList() {
        if (getUnBlockedAdmins().size() == 0) {
            return new ResponseEntity<>(getUnBlockedAdmins(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getUnBlockedAdmins(), HttpStatus.HTTP_200);
    }

    /**
     * @param choice adminni raqami frontdan keladigan
     * @return -> Agar choice son bo'lmasa yoki adminlarni sonidan katta son bo'lsa, (-1) qaytadi.
     * Aks holda choiceni Integerga o'tkazib qaytaradi.
     */
    private int castRequest(String choice) {
        int choiceN;
        try {
            choiceN = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (choiceN > getAdmins().size()) {
            return -1;
        }
        return choiceN - 1;
    }

    private ArrayList<AuthUser> getAdmins() {
        ArrayList<AuthUser> admins = new ArrayList<>();
        for (AuthUser authUser : authUserDao.users) {
            if (authUser.getRole().equals(Role.ADMIN) && authUser.getDeleted() != -1) {
                admins.add(authUser);
            }
        }
        return admins;
    }

    private ArrayList<AuthUser> getBlockedAdmins() {
        ArrayList<AuthUser> blockedAdmins = new ArrayList<>();
        for (AuthUser user : getAdmins()) {
            if (user.getStatus().equals(UserStatus.BLOCKED)) {
                blockedAdmins.add(user);
            }
        }
        return blockedAdmins;
    }

    private ArrayList<AuthUser> getUnBlockedAdmins() {
        ArrayList<AuthUser> unBlockedAdmins = new ArrayList<>();
        for (AuthUser user : getAdmins()) {
            if (!user.getStatus().equals(UserStatus.BLOCKED)) {
                unBlockedAdmins.add(user);
            }
        }
        return unBlockedAdmins;
    }
}
