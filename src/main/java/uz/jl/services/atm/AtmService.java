package uz.jl.services.atm;

import uz.jl.dao.atm.ATMDao;
import uz.jl.dao.db.FRWAtm;
import uz.jl.enums.atm.ATMStatus;
import uz.jl.enums.atm.CassetteStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.ATMMapper;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.atm.Cassette;
import uz.jl.response.ResponseEntity;
import uz.jl.services.BaseAbstractService;
import uz.jl.ui.AtmUI;
import uz.jl.utils.BaseUtils;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.BaseUtils.getBigInt;
import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Mon 10:46 AM. 12/6/2021
 */
public class AtmService extends BaseAbstractService<ATMEntity, ATMDao, ATMMapper> {
    private static AtmService service;

    public static AtmService getInstance(ATMDao repository, ATMMapper mapper) {
        if (Objects.isNull(service)) {
            service = new AtmService(repository, mapper);
        }
        return service;
    }

    protected AtmService(ATMDao repository, ATMMapper mapper) {
        super(repository, mapper);
    }

    public ResponseEntity<String> create(ATMEntity atmEntity) {
        List<ATMEntity> all = getAll();
        all.add(atmEntity);
        FRWAtm.getInstance().writeAll(all);
        return new ResponseEntity<>("success");
    }


    public ResponseEntity<String> delete(String param) {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atmEntity : all) {
            if (atmEntity.getDeleted() != 1) if (atmEntity.getName().equals(param) || atmEntity.getId().equals(param)) {
                atmEntity.setDeleted(1);
                atmEntity.getCassette1().setDeleted(1);
                atmEntity.getCassette2().setDeleted(1);
                atmEntity.getCassette3().setDeleted(1);
                atmEntity.getCassette4().setDeleted(1);
                FRWAtm.getInstance().writeAll(all);
                return new ResponseEntity<>("success");
            }
        }
        return new ResponseEntity<>("Atm not found", HttpStatus.HTTP_404);
    }


    public ATMEntity get(String param) {
        List<ATMEntity> all = getAll();
        for (ATMEntity atm : all) {
            if (atm.getDeleted() != 1)
                if (atm.getId().equals(param) || atm.getName().equals(param))
                    if(atm.getStatus().equals(ATMStatus.BLOCKED)){
                        Print.println(RED,"Atm is blocked");
                    }else
                    return atm;
        }
        return null;
    }

    public List<ATMEntity> getAll() {
        return ATMDao.getInstance().atms;
    }


    public ResponseEntity<String> update(ATMEntity atmEntity) {
        Print.println(atmEntity.getName());
        Print.println(atmEntity.getType());
        Print.println(atmEntity.getStatus());
        Print.println(atmEntity.getCassette1());
        Print.println(atmEntity.getCassette2());
        Print.println(atmEntity.getCassette3());
        Print.println(atmEntity.getCassette4());

        Integer integer = AtmUI.updateMenu();
        if (integer.equals(1)) {
            return updateName(atmEntity);
        }
        if (integer.equals(2)) {
            return updateLatitude(atmEntity);
        }
        if (integer.equals(3)) {
            return updateLongitude(atmEntity);
        }
        if (integer.equals(4)) {
            return updateCassette(atmEntity.getCassette1());
        }
        if (integer.equals(5)) {
            return updateCassette(atmEntity.getCassette2());
        }
        if (integer.equals(6)) {
            return updateCassette(atmEntity.getCassette3());
        }
        if (integer.equals(7)) {
            return updateCassette(atmEntity.getCassette4());
        }
        return new ResponseEntity<>("Wrong menu", HttpStatus.HTTP_400);
    }

    public ResponseEntity<String> block(String param) {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atmEntity : all) {
            if (atmEntity.getDeleted() != 1 && !atmEntity.getStatus().equals(ATMStatus.BLOCKED))
                if (atmEntity.getName().equals(param) || atmEntity.getId().equals(param)) {
                    atmEntity.setStatus(ATMStatus.BLOCKED);
                    atmEntity.getCassette1().setStatus(CassetteStatus.BLOCKED);
                    atmEntity.getCassette2().setStatus(CassetteStatus.BLOCKED);
                    atmEntity.getCassette3().setStatus(CassetteStatus.BLOCKED);
                    atmEntity.getCassette4().setStatus(CassetteStatus.BLOCKED);
                    FRWAtm.getInstance().writeAll(all);
                    return new ResponseEntity<>("success");
                }
        }
        return new ResponseEntity<>("Atm not found", HttpStatus.HTTP_404);
    }

    public ResponseEntity<String> unBlock(String param) {
        List<ATMEntity> all = service.getAll();
        for (ATMEntity atmEntity : all) {
            if (atmEntity.getDeleted() != 1 && atmEntity.getStatus().equals(ATMStatus.BLOCKED))
                if (atmEntity.getName().equals(param) || atmEntity.getId().equals(param)) {
                    atmEntity.setStatus(ATMStatus.ACTIVE);
                    atmEntity.getCassette1().setStatus(CassetteStatus.ACTIVE);
                    atmEntity.getCassette2().setStatus(CassetteStatus.ACTIVE);
                    atmEntity.getCassette3().setStatus(CassetteStatus.ACTIVE);
                    atmEntity.getCassette4().setStatus(CassetteStatus.ACTIVE);
                    FRWAtm.getInstance().writeAll(all);
                    return new ResponseEntity<>("success");
                }
        }
        return new ResponseEntity<>("Atm not found", HttpStatus.HTTP_404);
    }

    private ResponseEntity<String> updateName(ATMEntity atmEntity) {
        String name = getStr("New name = ");
        atmEntity.setName(name);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateLatitude(ATMEntity atmEntity) {
        String latitude = getStr("New latitude = ");
        Double aDouble = BaseUtils.toDouble(latitude);
        if (Objects.isNull(aDouble)) {
            return updateLatitude(atmEntity);
        }
        atmEntity.setLatitude(aDouble);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateLongitude(ATMEntity atmEntity) {
        String longitude = getStr("longitude = ");
        Double bDouble = BaseUtils.toDouble(longitude);
        if (Objects.isNull(bDouble)) {
            return updateLongitude(atmEntity);
        }
        atmEntity.setLongitude(bDouble);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateCassette(Cassette cassette) {
        Print.println(Color.YELLOW, "1. currencyValue");
        Print.println(Color.YELLOW, "2. status");
        Print.println(Color.YELLOW, "3. currencyCount");
        Print.println(Color.YELLOW, "4. deleted");
        String choice = getStr("choice menu = ");
        Integer integer = BaseUtils.toInteger(choice);
        if (Objects.isNull(integer)) {
            return updateCassette(cassette);
        }
        if (integer < 1 || integer > 4) {
            return updateCassette(cassette);
        }
        if (integer.equals(1)) {
            return updateCurrencyValue(cassette);
        }
        if (integer.equals(2)) {
            return updateStatus(cassette);
        }
        if (integer.equals(3)) {
            return updateCurrencyCount(cassette);
        }
        if (integer.equals(4)) {
            return updateDeleted(cassette);
        }
        return new ResponseEntity<>("Wrong menu", HttpStatus.HTTP_400);
    }

    private ResponseEntity<String> updateCurrencyValue(Cassette cassette) {
        BigInteger currencyValue = BaseUtils.getBigInt("New CurrencyValue = ");
        cassette.setCurrencyValue(currencyValue);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateStatus(Cassette cassette) {
        CassetteStatus.listType();
        String description = getStr("New status = ");
        CassetteStatus cassetteStatus = CassetteStatus.get(description);
        if (Objects.isNull(cassetteStatus)) {
            Print.println(RED, "Status not found");
            return updateStatus(cassette);
        }
        cassette.setStatus(cassetteStatus);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateCurrencyCount(Cassette cassette) {
        BigInteger currencyCount = getBigInt("New CurrencyCount = ");
        cassette.setCurrencyCount(currencyCount);
        return new ResponseEntity<>("Successfully");
    }

    private ResponseEntity<String> updateDeleted(Cassette cassette) {
        if (cassette.getDeleted() == 0) {
            cassette.setDeleted(1);
            return new ResponseEntity<>("Successfully");
        }
        cassette.setDeleted(0);
        return new ResponseEntity<>("Successfully");
    }
}

