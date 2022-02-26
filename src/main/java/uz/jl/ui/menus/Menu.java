package uz.jl.ui.menus;

import jdk.jshell.Snippet;
import uz.jl.configs.Session;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.models.settings.Language;
import uz.jl.utils.Print;

import java.util.LinkedHashMap;
import java.util.Map;

import static uz.jl.utils.Color.GREEN;

/**
 * @author Elmurodov Javohir, Mon 11:30 AM. 12/6/2021
 */
public class Menu {
    public static Map<String, MenuKey> menus() {
        Role role = Session.getInstance().getUser().getRole();
        UserStatus userStatus = Session.getInstance().getUser().getStatus();
        Language language=Session.getInstance().getLanguage();
        Map<String, MenuKey> menus = new LinkedHashMap<>();
        // TODO: 12/8/2021 do translations here
        if (Role.SUPER_ADMIN.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.CREATE_BRANCH.get(language), MenuKey.CREATE_BRANCH);
            menus.put(MenuKey.CREATE_ADMIN.get(language), MenuKey.CREATE_ADMIN);

            menus.put(MenuKey.BLOCK_ADMIN.get(language), MenuKey.BLOCK_ADMIN);
            menus.put(MenuKey.BLOCK_BRANCH.get(language), MenuKey.BLOCK_BRANCH);

            menus.put(MenuKey.LIST_ADMIN.get(language), MenuKey.LIST_ADMIN);
            menus.put(MenuKey.LIST_BRANCH.get(language),MenuKey.LIST_BRANCH);

            menus.put(MenuKey.BLOCK_LIST_ADMIN.get(language), MenuKey.BLOCK_LIST_ADMIN);
            menus.put(MenuKey.UPDATE_BRANCH.get(language), MenuKey.UPDATE_BRANCH);

            menus.put(MenuKey.DELETE_ADMIN.get(language), MenuKey.DELETE_ADMIN);
            menus.put(MenuKey.DELETE_BRANCH.get(language), MenuKey.DELETE_BRANCH);
        } else if (Role.ADMIN.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.CREATE_BRANCH.get(language), MenuKey.CREATE_BRANCH);
            menus.put(MenuKey.DELETE_BRANCH.get(language), MenuKey.DELETE_BRANCH);

            menus.put(MenuKey.UPDATE_BRANCH.get(language), MenuKey.UPDATE_BRANCH);
            menus.put(MenuKey.CREATE_HR.get(language), MenuKey.CREATE_HR);

            menus.put(MenuKey.LIST_HR.get(language), MenuKey.LIST_HR);
            menus.put(MenuKey.BLOCK_HR.get(language),MenuKey.BLOCK_HR);

            menus.put(MenuKey.UN_BLOCK_HR.get(language),MenuKey.UN_BLOCK_HR);
            menus.put(MenuKey.BLOCK_LIST_HR.get(language), MenuKey.BLOCK_LIST_HR);

            menus.put(MenuKey.DELETE_HR.get(language), MenuKey.DELETE_HR);
            menus.put(MenuKey.CREATE_ATM.get(language), MenuKey.CREATE_ATM);

            menus.put(MenuKey.LIST_ATM.get(language), MenuKey.LIST_ATM);
            menus.put(MenuKey.DELETE_ATM.get(language), MenuKey.DELETE_ATM);

            menus.put(MenuKey.BLOCK_ATM.get(language), MenuKey.BLOCK_ATM);
            menus.put(MenuKey.UN_BLOCK_ATM.get(language), MenuKey.UN_BLOCK_ATM);
        } else if (role.in(Role.ADMIN, Role.HR) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.CREATE_EMPLOYEE.get(language), MenuKey.CREATE_EMPLOYEE);
            menus.put(MenuKey.LIST_EMPLOYEE.get(language), MenuKey.LIST_EMPLOYEE);

            menus.put(MenuKey.BLOCK_LIST_EMPLOYEE.get(language), MenuKey.BLOCK_LIST_EMPLOYEE);
            menus.put(MenuKey.DELETE_EMPLOYEE.get(language), MenuKey.DELETE_EMPLOYEE);

            menus.put(MenuKey.BLOCK_EMPLOYEE.get(language), MenuKey.BLOCK_EMPLOYEE);
            menus.put(MenuKey.UN_BLOCK_EMPLOYEE.get(language), MenuKey.UN_BLOCK_EMPLOYEE);
        } else if (Role.EMPLOYEE.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.BLOCK_LIST_ATM.get(language), MenuKey.BLOCK_LIST_ATM);
            menus.put(MenuKey.UPDATE_ATM.get(language), MenuKey.UPDATE_ATM);

            menus.put(MenuKey.LIST_ATM.get(language), MenuKey.LIST_ATM);
            menus.put(MenuKey.CREATE_CLIENT.get(language),MenuKey.CREATE_CLIENT);

            menus.put(MenuKey.LIST_CLIENT.get(language),MenuKey.LIST_CLIENT);
            menus.put(MenuKey.BLOCK_LIST_CLIENT.get(language),MenuKey.BLOCK_LIST_CLIENT);

            menus.put(MenuKey.DELETE_CLIENT.get(language),MenuKey.DELETE_CLIENT);
            menus.put(MenuKey.BLOCK_CLIENT.get(language),MenuKey.BLOCK_CLIENT);

            menus.put(MenuKey.UN_BLOCK_CLIENT.get(language),MenuKey.UN_BLOCK_CLIENT);
        } else if (Role.ANONYMOUS.equals(role) || userStatus.equals(UserStatus.NON_ACTIVE)) {
            menus.put(MenuKey.LOGIN.get(language), MenuKey.LOGIN);
            menus.put(MenuKey.ATM_SERVICE.get(language),MenuKey.ATM_SERVICE);
        }
        if (!Role.ANONYMOUS.equals(role) && userStatus.equals(UserStatus.ACTIVE)) {
            menus.put(MenuKey.PROFILE.get(language), MenuKey.PROFILE);
            menus.put(MenuKey.LOGOUT.get(language), MenuKey.LOGOUT);
        }
        menus.put(MenuKey.EXIT.get(language), MenuKey.EXIT);
        return menus;
    }

    public static void show() {
        Menu.menus().forEach((k, v) -> Print.println(GREEN,k + " -> " + v));
    }
}
