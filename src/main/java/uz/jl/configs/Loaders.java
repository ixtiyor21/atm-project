package uz.jl.configs;

import uz.jl.exceptions.APIException;

/**
 * @author Elmurodov Javohir, Mon 12:21 PM. 11/29/2021
 */
public final class Loaders {
    private Loaders() {
    }

    public static void init() throws APIException {
        AppConfig.init();
        //LocaleConfig.init();
    }
}
