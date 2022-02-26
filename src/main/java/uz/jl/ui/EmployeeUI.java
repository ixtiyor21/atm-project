package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.enums.auth.Role;
import uz.jl.enums.card.CardType;
import uz.jl.enums.extras.Gender;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.EmployeeService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.List;

import static uz.jl.ui.BaseUI.showResponse;
import static uz.jl.ui.MainMenu.run;
import static uz.jl.utils.Color.BLUE;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;


/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class EmployeeUI {
    static EmployeeService clientService = EmployeeService.getInstance(
            AuthUserDao.getInstance(),
            AuthUserMapper.getInstance());

    public static void create() {
        String username = getStr("Enter username:");
        String phoneNumber = getStr("Enter phone number:");
        ResponseEntity<String> response = clientService.create(username, phoneNumber);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Enter username: ");
        ResponseEntity<String> response = clientService.delete(username);
        showResponse(response);
    }

    public static void list() {
        ResponseEntity<String> response = clientService.list();
        showResponse(response);
    }

    public static void block() {
        String username = getStr("Enter username: ");
        ResponseEntity<String> response = clientService.block(username);
        showResponse(response);
    }

    public static void unBlock() {
        String username = getStr("Enter username: ");
        ResponseEntity<String> response = clientService.unblock(username);
        showResponse(response);
    }

    public static void blockList() {
        ResponseEntity<String> response = clientService.blockList();
        showResponse(response);
    }

    public static void createCard(AuthUser user) {
        String cardType = getStr(BLUE + "Enter cardType(Uzcard/Humo/Master_card/Cobage/Union_Pay/Visa): ");
        String password = getStr(BLUE + "Enter password for card: ");
        CardType cardType1 = CardType.getType(cardType);
        if (cardType1.equals(CardType.UNDEFINED)) {
            Print.println(RED, "Card type not found");
            createCard(user);
        }
        String pan = BaseUtils.createPan(cardType1);
        ResponseEntity<String> response = clientService.createCard(cardType1, password, user, pan);
        showResponse(response);
    }

    public static void createPassport(AuthUser user) {
        String serial = getStr(BLUE + "Passport serial : ");
        String number = getStr(BLUE + "Passport number : ");
        String gender = getStr(BLUE + "Gender :");
        Gender gender1=Gender.getType(gender);
        if (gender1.equals(Gender.UNDEFINED)) {
            Print.println(RED, "Gender not found");
            createPassport(user);
        }
        String firstName=getStr(BLUE + "First name : ");
        String lastName=getStr(BLUE + "Last name : ");;
        String fatherName=getStr(BLUE + "Father name : ");;
        ResponseEntity<String> response = clientService.createPassport(serial,number,gender1,firstName,lastName,fatherName,user);
        showResponse(response);
    }
}
