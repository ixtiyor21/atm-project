package uz.jl.ui;

import uz.jl.dao.Personal.PassportDao;
import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.card.CardDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.ATMType;
import uz.jl.enums.atm.CassetteStatus;
import uz.jl.enums.atm.Status;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.atm.Cassette;
import uz.jl.models.branch.Branch;
import uz.jl.models.card.Card;
import uz.jl.response.ResponseEntity;
import uz.jl.services.atm.AtmOperations;
import uz.jl.services.atm.AtmService;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.CYAN;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class AtmUI extends BaseUI {
    static AtmService service = AtmService.getInstance(ATMDao.getInstance(), ATMMapper.getInstance());

    public static void menu() {
        showAll();
        String name = getStr("Atm name =");
        ATMEntity atm = service.get(name);
        if (Objects.isNull(atm)) {
            Print.println(RED, "ATM Not found");
            return;
        }
        if (atm.getStatus().equals(ATMStatus.BLOCKED)) {
            Print.println(RED, "ATM is Blocked");
            return;
        }
        String branchID=atm.getBranchId();
        Branch branch=BranchDao.getById(branchID);
        if (Objects.nonNull(branch)&&branch.getStatus().equals(Status.BLOCKED)) {
            Print.println(RED, "ATM's branch is blocked");
            return;
        }
        uzMenu(atm);
        }

    public static void create() {
        BranchUI.list();
        String branchName=getStr("Branch name:");
        Branch branch=BranchDao.getByname(branchName);
        String branchId="165137499323900mdUo3Po6fpoDzdUvpWqK";
        if(Objects.nonNull(branch))branchId=branch.getId();
        String atmName = getStr("Atm name = ");
        ATMEntity atm = service.get(atmName);
        if (Objects.nonNull(atm)) {
            Print.println(RED, "Atm name is available");
            return;
        }
        ATMType.listType();

        String type = getStr("type = ");
        ATMType atmType = ATMType.get(type);
        if (atmType.equals(ATMType.UNDEFINED)) {
            Print.println(RED+"Wrong choice");
            create();
            return;
        }

        String latitude = getStr("latitude = ");
        Double aDouble = BaseUtils.toDouble(latitude);
        if (Objects.isNull(aDouble)) {
            Print.println(RED+"Wrong choice");
            create();
            return;
        }

        String longitude = getStr("longitude = ");
        Double bDouble = BaseUtils.toDouble(longitude);
        if (Objects.isNull(bDouble)) {
            Print.println(RED+"Wrong choice");
            create();
            return;
        }

        Cassette cassette1;
        Cassette cassette2;
        Cassette cassette3;
        Cassette cassette4;

        if (atmType.equals(ATMType.UZCARD) || atmType.equals(ATMType.HUMO)) {
            cassette1 = new Cassette(BigInteger.valueOf(100000), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette2 = new Cassette(BigInteger.valueOf(50000), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette3 = new Cassette(BigInteger.valueOf(10000), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette4 = new Cassette(BigInteger.valueOf(5000), CassetteStatus.ACTIVE, BigInteger.valueOf(1000), 0);
        } else {
            cassette1 = new Cassette(BigInteger.valueOf(100), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette2 = new Cassette(BigInteger.valueOf(50), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette3 = new Cassette(BigInteger.valueOf(10), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
            cassette4 = new Cassette(BigInteger.valueOf(1), CassetteStatus.ACTIVE, BigInteger.valueOf(100), 0);
        }
        Print.println("Cassette1 = "+cassette1);
        Print.println("Cassette2 = "+cassette2);
        Print.println("Cassette3 = "+cassette3);
        Print.println("Cassette4 = "+cassette4);

        ATMEntity newAtm = new ATMEntity("165137499323900mdUo3Po6fpoDzdUvpWqK", atmType, atmName, ATMStatus.ACTIVE, aDouble, bDouble, cassette1, cassette2, cassette3, cassette4,branchId);
        newAtm.setId(BaseUtils.genId());
        ResponseEntity<String> atmEntityResponseEntity = service.create(newAtm);
        showResponse(atmEntityResponseEntity);
    }

    public static void update() {
        String name = getStr("name = ");
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1) if (atm.getName().equalsIgnoreCase(name)) {
                ResponseEntity<String> update = service.update(atm);
                FRWAtm.getInstance().writeAll(all);
                showResponse(update);
            }
        }
    }

    public static void delete() {
        list();
        String name = getStr("name = ");
        ResponseEntity<String> delete = service.delete(name);
        showResponse(delete);
    }

    public static void list() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1){
                if(atm.getStatus().equals(ATMStatus.BLOCKED) )
                    Print.println(RED, atm);
                else
                    Print.println(CYAN, atm);
        }}
    }

    public static void block() {
        list();
        String name = getStr("name = ");
        ResponseEntity<String> block = service.block(name);
        showResponse(block);
    }

    public static void unblock() {
        blockList();
        String name = getStr("name = ");
        ResponseEntity<String> unBlock = service.unBlock(name);
        showResponse(unBlock);
    }

    public static void blockList() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1 && atm.getStatus().equals(ATMStatus.BLOCKED)) Print.println(RED, atm);
        }
    }

    public static Integer updateMenu() {
        Print.println(Color.BLUE, "1. Name");
        Print.println(Color.BLUE, "2. Latitude");
        Print.println(Color.BLUE, "3. Longitude");
        Print.println(Color.BLUE, "4. cassette1");
        Print.println(Color.BLUE, "5. cassette2");
        Print.println(Color.BLUE, "6. cassette3");
        Print.println(Color.BLUE, "7. cassette4");
        String choice = getStr("choice menu = ");
        Integer integer = BaseUtils.toInteger(choice);
        if (Objects.isNull(integer)) {
            return updateMenu();
        }
        if (integer < 1 || integer > 7) {
            return -1;
        }
        return integer;
    }

    public static void showAll() {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1 && atm.getStatus().equals(ATMStatus.BLOCKED)) Print.println(RED, atm);
            else Print.println(Color.CYAN, atm);
        }
    }

    private static void uzMenu(ATMEntity atm) {
        String cardNumber = getStr("Enter card number : ");
        Card card = CardDao.getByCardNumber(cardNumber);
        if (card == null) {
            Print.println(RED, "Card not found ");
            menu();
        }
        String password = getStr("Enter card password : ");
        if(!card.getPassword().equals(password)){
            Print.println(RED,"Bad credentials");
            menu();
        }
        boolean changed=false;
        if(atm.getType().equals(ATMType.VISA)){
            if(card.getCurType().equals("sum")){
                card.setBalance(card.getBalance().divide(BigInteger.valueOf(10700)));
                changed=true;
            }
        }else {
            if(card.getCurType().equals("$")){
                card.setBalance(card.getBalance().multiply(BigInteger.valueOf(10700)));
                changed=true;
            }
        }
        uzMenuAtm(card,atm,changed);
        }
    public static void uzMenuAtm(Card card,ATMEntity atm,boolean changed){
        Print.println(Color.PURPLE, "1. Show balance");
        Print.println(Color.PURPLE, "2. Turn on sms-service");
        Print.println(Color.PURPLE, "3. Change pin code");
        Print.println(Color.PURPLE, "4. Withdraw money");
        Print.println(Color.PURPLE, "5. Put money on the card");
        Print.println(Color.PURPLE, "6. Back");
        String choice = getStr("choice menu =");
        switch (choice) {
            case "1" -> AtmOperations.showBalance(card,atm);
            case "2" -> AtmOperations.messageOn(card);
            case "3" -> AtmOperations.changePin(card);
            case "4" -> AtmOperations.withdraw(card,atm);
            case "5" -> AtmOperations.putMoney(card,atm);
            case "6" -> {
                if(changed){
                   if(card.getCurType().equals("$")){
                       card.setBalance(card.getBalance().divide(BigInteger.valueOf(10700)));
                       }else {
                       card.setBalance(card.getBalance().multiply(BigInteger.valueOf(10700)));
                   }
                }
                Print.println(Color.BLUE, "Thank you for using our atm");
                AuthUserDao.getInstance().writeAll();
                ATMDao.getInstance().writeAll();
                BranchDao.getInstance().writeAll();
                CardDao.getInstance().writeAll();
                PassportDao.getInstance().writeAll();
                return;
            }
            default -> {
                Print.println(RED, "Wrong menu");
                uzMenuAtm(card,atm,changed);
            }
        }
        uzMenuAtm(card,atm,changed);
    }
}
