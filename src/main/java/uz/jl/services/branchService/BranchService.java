package uz.jl.services.branchService;

import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.Status;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Input.getStr;

public class BranchService {

    private static BranchService install;

    public static BranchService getInstall() {
        if (Objects.isNull(install))
            install = new BranchService();
        return install;
    }
    BranchDao branchDao = BranchDao.getInstance();

    public ResponseEntity<String> create(Branch branch) {
        branch.setStatus(Status.ACTIVE);
        branch.setBankId("165137499323900mdUo3Po6fpoDzdUvpWqK");
        branch.setCreatedAt(new Date());
        branch.setCreatedBy(Session.getInstance().getUser().getId());
        branchDao.branches.add(branch);
        return new ResponseEntity<>("Successfully created branch", HttpStatus.HTTP_201);
    }

    public ResponseEntity<String> delete(String name) {
        Branch branch = findByName(name);
        if (Objects.isNull(branch)) {
            return new ResponseEntity<>("Branch not found", HttpStatus.HTTP_400);
        }
        branch.setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> update(String name) {
        Branch branch = findByName(name);
        if (Objects.isNull(branch)) {
            return new ResponseEntity<>("Branch not found", HttpStatus.HTTP_400);
        }
        name=getStr("Enter new branch name:");
        branch.setName(name);
        branch.setUpdatedAt(new Date());
        branch.setUpdatedBy(Session.getInstance().getUser().getId());
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> block(String str) {
        Branch branch = findByName(str);
        if (Objects.isNull(branch))
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        branch.setStatus(Status.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> unblock(String str) {
        Branch branch = findByName(str);
        if (Objects.isNull(branch))
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        branch.setStatus(Status.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);

    }

    public ResponseEntity<ArrayList<Branch>> list() {
        if (getBranch().size() == 0) return new ResponseEntity<>(getBranch(), HttpStatus.HTTP_204);
        return new ResponseEntity<>(getBranch(), HttpStatus.HTTP_200);
    }

    public ArrayList<Branch> getBranch() {
        return (ArrayList<Branch>) FRWBranch.getInstance().getAll();
    }

    private Branch findByName(String name) {
        for (Branch branch : branchDao.branches) {
            if (branch.getName().equalsIgnoreCase(name))
                return branch;
        }
        return null;
    }
    public boolean isAvailable(String name) {
        for (Branch branch : branchDao.branches) {
            if (branch.getName().equalsIgnoreCase(name))
                return false;
        }
        return true;
    }

    public ResponseEntity<String> getBranchID(String choice) {
        int choiceN = castRequest(choice);
        if (choiceN == -1) {
            return new ResponseEntity<>("Missmatch input", HttpStatus.HTTP_400);
        }
        return new ResponseEntity<>(getBranch().get(choiceN).getId(), HttpStatus.HTTP_202);
    }

    private int castRequest(String choice) {
        int choiceN;
        try {
            choiceN = Integer.parseInt(choice);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (choiceN > getBranch().size()) {
            return -1;
        }
        return choiceN - 1;
    }


}
