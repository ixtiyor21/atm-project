package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.dao.Personal.PassportDao;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.card.Card;
import uz.jl.models.personal.Passport;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 12.12.2021 11:26
 * Project : ATMoraclechilar
 */
public final class FRWPassport extends FRWBase<Passport> {
    private static FRWPassport frwPassport;

    public static FRWPassport getInstance() {
        if (Objects.isNull(frwPassport)) {
            frwPassport = new FRWPassport();
        }
        return frwPassport;
    }

    public FRWPassport() {
        super(AppConfig.get("db.passport.path"));
    }

    @Override
    public List<Passport> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<Passport>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<Passport> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(Passport passport) {
        writeAll(Collections.singletonList(passport));
    }
}
