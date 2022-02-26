package uz.jl.ui;

import uz.jl.configs.Session;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.dao.branch.BranchDao;
import uz.jl.enums.auth.Role;
import uz.jl.enums.http.HttpStatus;
import uz.jl.mapper.AuthUserMapper;
import uz.jl.models.branch.Branch;
import uz.jl.response.ResponseEntity;
import uz.jl.services.auth.HRService;
import uz.jl.services.branchService.BranchService;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

import java.net.SocketOptions;
import java.util.ArrayList;
import java.util.Objects;

import static uz.jl.utils.Input.*;

/**
 * @author Elmurodov Javohir, Wed 12:10 PM. 12/8/2021
 */
public class HrUI extends BaseUI {
    static HRService service = HRService.getInstance(AuthUserDao.getInstance(), AuthUserMapper.getInstance());

    public static void create() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        String phoneNumber = getStr("PhoneNumber: ");
        String branchID;
        if(Session.getInstance().getUser().getRole().equals(Role.ADMIN)){
            BranchUI.list();
            String branchName=getStr("Branch name:");
            Branch branch= BranchDao.getByname(branchName);
            branchID="165137499323900mdUo3Po6fpoDzdUvpWqK";
            if(Objects.nonNull(branch))branchID=branch.getId();

        }else{
            branchID=Session.getInstance().getUser().getBranchId();
        }
        ResponseEntity<String> response = service.create(username, password, phoneNumber,branchID);
        showResponse(response);
    }

    public static void delete() {
        String username = getStr("Username: ");
        String password = getStr("Password:");
        ResponseEntity<String> response = service.delete(username, password);
        showResponse(response);
    }

    public static void list() {
        ResponseEntity<String> response = service.list();
        showResponse(response);
    }

    public static void blockList() {
        ResponseEntity<String> response = service.blockList();
        showResponse(response);
    }

    public static void block() {
        list();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.block(username);
        showResponse(response);

    }

    public static void unBlock() {
        blockList();
        String username = getStr("Username: ");
        ResponseEntity<String> response = service.unblock(username);
        showResponse(response);
    }
}
