package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.models.settings.Language;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AuthService;
import uz.jl.services.auth.SuperAdminService;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.nio.channels.Pipe;
import java.security.Provider;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:09 PM. 12/8/2021
 */
public class SuperAdminUI {

    public static SuperAdminService superAdminService = SuperAdminService.getInstance();
    public static AuthUser sessionUser = Session.getInstance().getUser();

    public static void create() {
        String username = getStr("Username: ");
        String password = getStr("Password: ");
        String phoneNumber = getStr("Phone number : ");
        String choice = getStr("""
                1. Submit
                2. Cancel
                 ? :  """);
        if (choice.equals("2")) {
            return;
        }
        AuthUser admin = AuthUser.childBuilder().username(username).password(password).
                status(UserStatus.ACTIVE).phoneNumber(phoneNumber).
                createdBy(Session.getInstance().getUser().getUsername())
                .createdAt(new Date())
                .role(Role.ADMIN).bankId("165137499323900mdUo3Po6fpoDzdUvpWqK").childBuild();
        // TODO: 12/10/2021 Bank id
        ResponseEntity<String> response1 = superAdminService.create(admin);
        if (response1.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.PURPLE, response1.getData());
        }
    }

    public static void delete() {
        if (!list()) {
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response = superAdminService.delete(choice);
        showResponse_400_202_(response);
    }

    public static boolean list() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no admins");
            return false;
        }
        showAdmins(response.getData());
        return true;
    }

    public static void block() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.unBlockedAdminList();
        if (!showResponse_204_200_(response)) {
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response1 = superAdminService.block(choice);
        showResponse_400_202_(response1);
    }


    public static void unBlock() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.blockedAdminList();
        if (!showResponse_204_200_(response)) {
            return;
        }
        String choice = getStr("Enter choice: ");
        ResponseEntity<String> response1 = superAdminService.unBlock(choice);
        showResponse_400_202_(response1);
    }


    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = superAdminService.blockedAdminList();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no blocked admins");
            return;
        }
        int i = 1;
        for (AuthUser admin : response.getData()) {
            Print.println(String.format(RED+"""
                    %s. %s""", i++, admin.getUsername()));
        }
    }

    /**
     * @return User tanlagan tilni qaytaradi
     */

    /**
     * @param response Agar response sifatida HTTP_400 va HTTP_202 kelsa, shu responseni datasini ko'rsatib beradi
     */
    private static void showResponse_400_202_(ResponseEntity<String> response) {
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(RED, response.getData());
        } else if (response.getStatus().equals(HttpStatus.HTTP_202.getCode())) {
            Print.println(Color.PURPLE, response.getData());
        }
    }

    /**
     * Agar databaseda requestga mos bo'lgan birorta ham element bo'lmasa qaytadigan HTTP_204(No content) va HTTP_200(OK)
     * Statusli responslarni chiqaruvchi funksiya
     *
     * @param response
     * @return
     */
    private static boolean showResponse_204_200_(ResponseEntity<ArrayList<AuthUser>> response) {
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no any item");
            return false;
        }
        showAdmins(response.getData());
        return true;
    }

    private static void showAdmins(ArrayList<AuthUser> authUsers) {
        int i = 1;
        for (AuthUser admin : authUsers) {
            if(admin.getStatus().equals(UserStatus.BLOCKED))
            {Print.println(String.format(RED+"""
                    %s ->
                    Username: %s
                    Phone Number: %s """, i++, admin.getUsername(), admin.getPhoneNumber()));
        }else {
                Print.println(String.format("""
                    %s ->
                    Username: %s
                    Phone Number: %s """, i++, admin.getUsername(), admin.getPhoneNumber()));
            }
        }
    }
}
