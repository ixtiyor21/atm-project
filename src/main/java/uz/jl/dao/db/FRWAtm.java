package uz.jl.dao.db;

import com.google.gson.reflect.TypeToken;
import uz.jl.configs.AppConfig;
import uz.jl.models.atm.ATMEntity;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 18:49
 * Project : ATMoraclechilar
 */
public final class FRWAtm extends FRWBase<ATMEntity> {
    private static FRWAtm frwAuthUser;

    public static FRWAtm getInstance() {
        if (Objects.isNull(frwAuthUser)) {
            frwAuthUser = new FRWAtm();
        }
        return frwAuthUser;
    }

    public FRWAtm() {
        super(AppConfig.get("db.atm.path"));
    }

    @Override
    public List<ATMEntity> getAll() {
        if (list.isEmpty()) {
            try (FileReader fileReader = new FileReader(path);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String jsonDATA = bufferedReader.lines().collect(Collectors.joining());
                list = gson.fromJson(jsonDATA, new TypeToken<List<ATMEntity>>() {
                }.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void writeAll(List<ATMEntity> dataList) {
        try (FileWriter fileWriter = new FileWriter(path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String jsonDATA = gson.toJson(dataList);
            bufferedWriter.write(jsonDATA);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAll(ATMEntity atm) {
        writeAll(Collections.singletonList(atm));
    }
}
