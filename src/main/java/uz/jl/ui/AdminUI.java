package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.branch.BranchDao;
import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.AdminService;
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
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class AdminUI {

    static AdminService adminService = new AdminService().getInstall();

    public static void create() {
        BranchUI.list();
        String branchName=getStr("Branch name:");
        Branch branch= BranchDao.getByname(branchName);
        String branchId="165137499323900mdUo3Po6fpoDzdUvpWqK";
        if(Objects.nonNull(branch))branchId=branch.getId();
        String name = Input.getStr("username :");
        String pass = Input.getStr("password :");
        String phonNumber = Input.getStr("Phone number :");
        AuthUser user = AuthUser.childBuilder()
                .username(name)
                .password(pass)
                .phoneNumber(phonNumber)
                .role(Role.HR)
                .createdBy(Session.getInstance().getUser().getUsername())
                .status(UserStatus.ACTIVE)
                .branchId(branchId)
                .childBuild();
        ResponseEntity<String> response = adminService.create(user);
        if (response.getStatus().equals(HttpStatus.HTTP_201.getCode())) {
            Print.println(Color.BLUE, response.getData());
        }
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode())) {
            Print.println(RED, response.getData());
        }
    }

    public static void delete() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = adminService.delete(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.GREEN, response.getData());
    }

    public static void list() {
        ResponseEntity<ArrayList<AuthUser>> response = adminService.list();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no HR");
        }
        int i = 1;
        for (AuthUser hr : response.getData()) {
            if(hr.getDeleted()!=-1){
            if(hr.getStatus().equals(UserStatus.BLOCKED)) {
                Print.println(String.format(RED+"""
                                %s -> Username: %s""",
                        i++, hr.getUsername()));
            }else{
                Print.println(String.format("""
                                %s -> Username: %s""",
                        i++, hr.getUsername()));
            }}}
    }

    public static void block() {
        list();
        String name = Input.getStr("name -> ");
        ResponseEntity<String> response = adminService.block(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.GREEN, response.getData());
    }

    public static void unBlock() {
        list();
        String name = Input.getStr("name -> :");
        ResponseEntity<String> response = adminService.unblock(name);
        if (response.getStatus().equals(HttpStatus.HTTP_400.getCode()))
            Print.println(RED, response.getData());
        else Print.println(Color.GREEN, response.getData());
    }

    public static void blockList() {
        ResponseEntity<ArrayList<AuthUser>> response = adminService.blockList();
        if (response.getStatus().equals(HttpStatus.HTTP_204.getCode())) {
            Print.println(RED, "There are no HR");
        }
        int i = 1;
        for (AuthUser hr : response.getData()) {
            if (hr.getStatus().equals(UserStatus.BLOCKED))
                Print.println(String.format(RED+"""
                                %s -> Username: %s""",
                        i++, hr.getUsername()));
        }
    }

}




