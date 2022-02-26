package uz.jl.dao.Personal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.BaseDao;
import uz.jl.dao.db.FRWCard;
import uz.jl.dao.db.FRWPassport;
import uz.jl.models.card.Card;
import uz.jl.models.personal.Passport;

import java.util.List;
import java.util.Objects;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 12.12.2021 11:25
 * Project : ATMoraclechilar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PassportDao extends BaseDao<Card> {
    private static PassportDao dao;
    public static PassportDao getInstance() {
        if (Objects.isNull(dao))
            dao = new PassportDao();
        return dao;
        }
        private static FRWPassport frwPassport = FRWPassport.getInstance();

    public List<Passport> passports = frwPassport.getAll();

    public void writeAll() {
        frwPassport.writeAll(passports);
    }

}
