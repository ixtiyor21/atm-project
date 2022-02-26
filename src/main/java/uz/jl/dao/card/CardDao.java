package uz.jl.dao.card;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.BaseDao;
import uz.jl.dao.db.FRWCard;
import uz.jl.models.card.Card;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CardDao extends BaseDao<Card> {
    private static CardDao dao;

    public static CardDao getInstance() {
        if (Objects.isNull(dao))
            dao = new CardDao();
        return dao;
    }
    private static final FRWCard frwcard = FRWCard.getInstance();

    public List<Card> cards = frwcard.getAll();

    public static Card getByCardNumber(String cardNumber) {
        for (Card card : CardDao.getInstance().cards) {
            if(card.getPan().equals(cardNumber))
                return card;
        }
        return null;
    }


    public void writeAll() {
        frwcard.writeAll(cards);
    }

}
