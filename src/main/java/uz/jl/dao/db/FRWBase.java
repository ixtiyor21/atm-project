package uz.jl.dao.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Elmurodov Javohir, Thu 9:33 AM. 12/9/2021
 */
public sealed abstract class FRWBase<T> permits FRWAuthUser, FRWBranch, FRWCard,FRWAtm,FRWPassport {
    protected List<T> list = new ArrayList<>();
    protected static Gson gson;
    protected String path;

    public FRWBase(String path) {
        if (Objects.isNull(gson)) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        this.path = path;
    }

    public abstract List<T> getAll();

    public abstract void writeAll(List<T> dataList);
}
