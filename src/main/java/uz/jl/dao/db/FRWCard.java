package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.models.card.Card;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class FRWCard extends FRWBase<Card> {
    private static FRWCard frwCard;

    public static FRWCard getInstance() {
        if (Objects.isNull(frwCard)) {
            frwCard = new FRWCard();
        }
        return frwCard;
    }

    public FRWCard() {
        super(AppConfig.get("db.cards.path"));
    }

    @Override
    public List<Card> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<Card>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<Card> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(Card card) {
        writeAll(Collections.singletonList(card));
    }
}
