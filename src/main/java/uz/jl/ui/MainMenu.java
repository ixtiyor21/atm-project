package uz.jl.ui;

import uz.jl.configs.AppConfig;
import uz.jl.configs.Loaders;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.card.CardDao;
import uz.jl.exceptions.APIException;
import uz.jl.ui.menus.Menu;
import uz.jl.ui.menus.MenuKey;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Wed 11:30 AM. 12/8/2021
 */
public class MainMenu {
    static {
        AppConfig.init();
    }

    public static void run() {
        Menu.show();
        String choice = Input.getStr("?:");
        MenuKey key = MenuKey.getByValue(choice);

        switch (key) {
            case LOGIN -> AuthUI.login();
            case ATM_SERVICE -> AtmUI.menu();
            case PROFILE -> AuthUI.profile();
            case LOGOUT -> AuthUI.logout();

            case CREATE_ADMIN -> SuperAdminUI.create();
            case DELETE_ADMIN -> SuperAdminUI.delete();
            case LIST_ADMIN -> SuperAdminUI.list();
            case BLOCK_ADMIN -> SuperAdminUI.block();
            case UN_BLOCK_ADMIN -> SuperAdminUI.unBlock();
            case BLOCK_LIST_ADMIN -> SuperAdminUI.blockList();

            case CREATE_HR -> AdminUI.create();
            case DELETE_HR -> AdminUI.delete();
            case LIST_HR -> AdminUI.list();
            case BLOCK_HR -> AdminUI.block();
            case UN_BLOCK_HR -> AdminUI.unBlock();
            case BLOCK_LIST_HR -> AdminUI.blockList();

            case CREATE_EMPLOYEE -> HrUI.create();
            case DELETE_EMPLOYEE -> HrUI.delete();
            case LIST_EMPLOYEE -> HrUI.list();
            case BLOCK_EMPLOYEE -> HrUI.block();
            case UN_BLOCK_EMPLOYEE -> HrUI.unBlock();
            case BLOCK_LIST_EMPLOYEE -> HrUI.blockList();

            case CREATE_CLIENT -> EmployeeUI.create();
            case DELETE_CLIENT -> EmployeeUI.delete();
            case LIST_CLIENT -> EmployeeUI.list();
            case BLOCK_CLIENT -> EmployeeUI.block();
            case UN_BLOCK_CLIENT -> EmployeeUI.unBlock();
            case BLOCK_LIST_CLIENT -> EmployeeUI.blockList();

            case CREATE_BRANCH -> BranchUI.create();
            case UPDATE_BRANCH -> BranchUI.update();
            case DELETE_BRANCH -> BranchUI.delete();
            case LIST_BRANCH -> BranchUI.list();
            case BLOCK_BRANCH -> BranchUI.block();
            case UN_BLOCK_BRANCH -> BranchUI.unblock();
            case BLOCK_LIST_BRANCH -> BranchUI.blockList();

            case CREATE_ATM -> AtmUI.create();
            case UPDATE_ATM -> AtmUI.update();
            case DELETE_ATM -> AtmUI.delete();
            case LIST_ATM -> AtmUI.list();
            case BLOCK_ATM -> AtmUI.block();
            case UN_BLOCK_ATM -> AtmUI.unblock();
            case BLOCK_LIST_ATM -> AtmUI.blockList();

            case EXIT -> {
                AuthUserDao.getInstance().writeAll();
                ATMDao.getInstance().writeAll();
                BranchDao.getInstance().writeAll();
                CardDao.getInstance().writeAll();
                PassportDao.getInstance().writeAll();
                Print.println(Color.YELLOW, "Good bye");
                return;
            }
            default -> // TODO: 12/8/2021 do translations here
                    Print.println(Color.RED, "Wrong Choice");
        }
        run();
    }
}
