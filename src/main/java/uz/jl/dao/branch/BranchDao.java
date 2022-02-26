package uz.jl.dao.branch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.jl.dao.BaseDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.models.branch.Branch;

import java.util.List;
import java.util.Objects;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 22:40
 * Project : ATMoraclechilar
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BranchDao extends BaseDao<Branch> {
    private static BranchDao dao;

    public static BranchDao getInstance() {
        if (Objects.isNull(dao))
            dao = new BranchDao();
        return dao;
    }

    private static FRWBranch frwBranch = FRWBranch.getInstance();

    public List<Branch> branches = frwBranch.getAll();

    public static Branch getByname(String branchName) {
        for (Branch branch : BranchDao.getInstance().branches) {
            if (branch.getName().equals(branchName))
                return branch;
        }
        return null;
    }

    public static Branch getById(String branchID) {
        for (Branch branch : BranchDao.getInstance().branches) {
            if (branch.getId().equals(branchID))
                return branch;
        }
        return null;
    }

    public void writeAll() {
        frwBranch.writeAll(branches);
    }

}

