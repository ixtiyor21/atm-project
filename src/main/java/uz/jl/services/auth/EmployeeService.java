package uz.jl.services.auth;

import uz.jl.configs.AppConfig;
import uz.jl.configs.Session;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.card.CardDao;
import uz.jl.enums.atm.Status;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.card.CardType;
import uz.jl.enums.extras.Gender;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Card;
import uz.jl.models.personal.Passport;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.ui.EmployeeUI;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.BaseUtils.getBig;
import static uz.jl.utils.BaseUtils.getBigInt;
import static uz.jl.utils.Color.*;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

public class EmployeeService extends BaseAbstractService<AuthUser, AuthUserDao, AuthUserMapper> {
    private static EmployeeService instance;

    protected EmployeeService(AuthUserDao repository, AuthUserMapper mapper) {
        super(repository, mapper);
    }

    public static EmployeeService getInstance(AuthUserDao repository, AuthUserMapper mapper) {
        if (Objects.isNull(instance))
            return new EmployeeService(repository, mapper);
        return instance;
    }

    public ResponseEntity<String> create(String username, String phoneNumber) {
        AuthUser temp = repository.findByUserName(username);
        if (Objects.nonNull(temp)) {
            return new ResponseEntity<>("Username already taken!");
        }
        AuthUser user = new AuthUser();
        user.setUsername(username);
        user.setStatus(UserStatus.NON_ACTIVE);
        user.setPhoneNumber(phoneNumber);
        user.setBranchId(Session.getInstance().getUser().getBranchId());
        user.setRole(Role.CLIENT);
        user.setCreatedAt(new Date());
        EmployeeUI.createPassport(user);
        Print.println(BLUE+"Would you create card ? (yes/no)");
        String choice=getStr("...");
        if(choice.startsWith("y")){
            EmployeeUI.createCard(user);
        }
        AuthUserDao.getInstance().users.add(user);
        return new ResponseEntity<>(BLUE + "Thank you for using our bank :)");
    }

    public ResponseEntity<String> delete(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT) && user.getDeleted() != -1 && user.getUsername().equals(username))
                user.setDeleted(-1);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Deleted!");
    }

    public ResponseEntity<String> block(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT)
                    && user.getDeleted() != -1
                    && user.getUsername().equals(username)
                    && !user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.BLOCKED);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Blocked!");
    }

    public ResponseEntity<String> unblock(String username) {
        for (AuthUser user : AuthUserDao.getInstance().users) {
            if (user.getRole().equals(Role.CLIENT)
                    && user.getDeleted() != -1
                    && user.getUsername().equals(username)
                    && user.getStatus().equals(UserStatus.BLOCKED))
                user.setStatus(UserStatus.ACTIVE);
            else
                return new ResponseEntity<>(RED + "User not found!", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(GREEN + "Unblocked!");
    }

    public ResponseEntity<String> blockList() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (user.getRole().equals(Role.CLIENT)
                    && user.getDeleted() != -1
                    && user.getStatus().equals(UserStatus.BLOCKED))
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }

    public ResponseEntity<String> list() {
        AuthUserDao.getInstance().users.forEach(user -> {
            if (user.getRole().equals(Role.CLIENT)
                    && user.getDeleted() != -1
                    && !user.getStatus().equals(UserStatus.BLOCKED))
                println(PURPLE, user.getUsername());
            else
                println(RED, user.getUsername() + " - (blocked)");
        });
        return new ResponseEntity<>("");
    }

    public ResponseEntity<String> createCard(CardType cardType, String password, AuthUser user, String pan) {
        Card card = new Card();
        card.setStatus(Status.ACTIVE);
        card.setPassword(password);
        card.setHolderId(user.getId());
        card.setType(cardType);
        card.setPan(pan);
        card.setBankId("165137499323900mdUo3Po6fpoDzdUvpWqK");
        Print.println("Your card number :" + pan);
        Print.println("Would you deposit money to the card (yes/no)");
        String choice=getStr("...");
        String currency="$";
        if(choice.startsWith("y")){
            choice=getStr("Chooce currency ($/sum) -> (1/2) : ");
            if(choice.equals("1")){
            currency="$";
            card.setCurType("$");
            }
            else if(choice.equals("2")){
            currency="sum";
            card.setCurType("sum");}
            else{
                Print.println(RED,"wrong choice");
                createCard(cardType,password,user,pan);
            }
            println("Enter amount of money ("+currency+"): ");
            BigInteger balance=getBigInt("...");
            card.setBalance(balance);
        }
        CardDao.getInstance().cards.add(card);
        return new ResponseEntity<>(PURPLE + "Success!");
    }

    public ResponseEntity<String> createPassport(String serial, String number, Gender gender1, String firstName, String lastName, String fatherName, AuthUser user) {
        Passport passport = Passport.childBuilder().serial(serial).
                number(number).gender(gender1).firstName(firstName).lastName(lastName)
                .fatherName(fatherName).ownerId(user.getId()).build();
        PassportDao.getInstance().passports.add(passport);
        return new ResponseEntity<>(PURPLE + "Success");
    }

    ;

}
