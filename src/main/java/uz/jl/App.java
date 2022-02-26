package uz.jl;


import uz.jl.configs.Loaders;
import uz.jl.dao.auth.AuthUserDao;
import uz.jl.exceptions.APIException;
import uz.jl.ui.MainMenu;

/**
 * @author Elmurodov Javohir, Mon 11:47 AM. 11/29/2021
 */

public class App {
    public static void main(String[] args) {
        try {
            Loaders.init();
        } catch (APIException e) {
            e.printStackTrace();
        }
        MainMenu.run();
    }
}
