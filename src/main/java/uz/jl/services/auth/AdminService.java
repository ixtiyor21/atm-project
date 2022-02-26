package uz.jl.services.auth;

import uz.jl.dao.db.FRWAuthUser;
import uz.jl.enums.auth.Role;
import uz.jl.enums.auth.UserStatus;
import uz.jl.enums.http.HttpStatus;
import uz.jl.models.auth.AuthUser;
import uz.jl.response.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminService {

    private static AdminService instance;

    public AdminService getInstall() {
        if (Objects.isNull(instance))
            instance = new AdminService();
        return instance;
    }

    List<AuthUser> users = FRWAuthUser.getInstance().getAll();

    public ResponseEntity<String> create(AuthUser admin) {
        ResponseEntity<String> response = new ResponseEntity<>("Succesfully created", HttpStatus.HTTP_201);
        if (Objects.nonNull(findByUsername(admin.getUsername()))) {
            response.setStatus(HttpStatus.HTTP_400.getCode());
            response.setData("This user is available");
            return response;
        }
        users.add(admin);
        FRWAuthUser.getInstance().writeAll(users);
        return response;
    }

    public ResponseEntity<String> delete(String username) {
        AuthUser hr = findByUsername(username);
        if (Objects.isNull(hr))
            return new ResponseEntity<>("Wrong choice", HttpStatus.HTTP_400);
        hr.setDeleted(-1);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> block(String username) {
        AuthUser hr = findByUsername(username);
        if (Objects.isNull(hr))
            return new ResponseEntity<>("Wrong choice", HttpStatus.HTTP_400);
        hr.setStatus(UserStatus.BLOCKED);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<String> unblock(String username) {
        AuthUser hr = findByUsername(username);
        if (Objects.isNull(hr))
            return new ResponseEntity<>("Wrong choice", HttpStatus.HTTP_400);
        hr.setStatus(UserStatus.ACTIVE);
        return new ResponseEntity<>("Successfully", HttpStatus.HTTP_202);
    }

    public ResponseEntity<ArrayList<AuthUser>> blockList() {
        ArrayList<AuthUser> blockedUsers = new ArrayList<>();
        for (AuthUser hr : getHr()) {
            if (hr.getStatus().equals(UserStatus.BLOCKED)) {
                blockedUsers.add(hr);
            }
        }
        if (blockedUsers.size() == 0) {
            return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(blockedUsers, HttpStatus.HTTP_200);
    }

    public ResponseEntity<ArrayList<AuthUser>> list() {
        if (getHr().size() == 0) {
            return new ResponseEntity<>(getHr(), HttpStatus.HTTP_204);
        }
        return new ResponseEntity<>(getHr(), HttpStatus.HTTP_200);
    }

    public ArrayList<AuthUser> getHr() {
        ArrayList<AuthUser> hr = new ArrayList<>();
        for (AuthUser Hr : users) {
            if (Hr.getRole().equals(Role.HR) && Hr.getDeleted() != -1) {
                hr.add(Hr);
            }
        }
        return hr;
    }

    private AuthUser findByUsername(String username) {
        for (AuthUser user : users) {
            if (user.getUsername().equalsIgnoreCase(username))
                return user;
        }
        return null;
    }
}
