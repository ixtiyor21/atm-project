package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWBranch;
import uz.jl.enums.atm.Status;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.Color;
import uz.jl.utils.Input;
import uz.jl.utils.Print;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static uz.jl.utils.Color.RED;
import static uz.jl.utils.Input.getStr;

/**
 * @author Elmurodov Javohir, Wed 12:11 PM. 12/8/2021
 */
public class BranchUI {

    static BranchService branchService = BranchService.getInstall();

    public static void create() {
        String name = getStr("Branch name :");
        if (!branchService.isAvailable(name)) {
            Print.println(RED, "This branch name is already taken. Please enter another branch name.");
            create();
        }
        Branch branch = Branch.childBuilder().name(name).childBuild();
        ResponseEntity<String> response = branchService.create(branch);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode()))
            Print.println(response.getData());
    }

    public static void update() {
        list();
        String branchName=getStr("Enter branch name : ");
        ResponseEntity<String> response = branchService.update(branchName);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }


    public static void delete() {
        list();
        String name = getStr("name -> ");
        ResponseEntity<String> response = branchService.delete(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void list() {
        ResponseEntity<ArrayList<Branch>> response = branchService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no branch");
        }
        int i = 1;
        for (Branch branch : response.getData()) {
            if(branch.getDeleted()!=-1){
            if(branch.getStatus().equals(Status.BLOCKED)){
                Print.println(String.format(RED+"""
                            %s -> name: %s""",
                        i++, branch.getName()));
            }else {
                Print.println(String.format("""
                            %s -> name: %s""",
                        i++, branch.getName()));
            }}
        }
    }

    public static void block() {
        list();
        String name = getStr("branch name : ");
        ResponseEntity<String> response = branchService.block(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void unblock() {
        blockList();
        String str = getStr("branch name : ");
        ResponseEntity<String> response = branchService.unblock(str);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.PURPLE, response.getData());
    }

    public static void blockList() {
        ResponseEntity<ArrayList<Branch>> response = branchService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no HR");
        }
        int i = 1;
        for (Branch branch : response.getData()) {
            if (branch.getStatus().equals(Status.BLOCKED))
                Print.println(String.format("""
                                %s -> Username: %s""",
                        i++, branch.getName()));
        }
    }

    public static void showBranch(ArrayList<Branch> branches) {
        int i = 1;
        for (Branch branch : branches) {
            if(branch.getDeleted()!=-1){
                if(branch.getStatus().equals(Status.BLOCKED)){
                    Print.println(String.format(RED+"""
                            %s -> name: %s""",
                            i++, branch.getName()));
                }else {
                    Print.println(String.format("""
                            %s -> name: %s""",
                            i++, branch.getName()));
                }
        }}
    }


}