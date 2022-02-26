package uz.jl.ui;

import uz.jl.response.ResponseEntity;
import uz.jl.utils.Color;
import uz.jl.utils.Print;

/**
 * @author Elmurodov Javohir, Thu 10:49 AM. 12/9/2021
 */
public abstract class BaseUI {
    protected static void showResponse(ResponseEntity response) {
        if (response.getStatus().equals(200))
            Print.println(Color.GREEN, response.getData());
        else
            Print.println(Color.RED, response.getData());
    }
}
