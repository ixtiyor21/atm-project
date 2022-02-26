package uz.jl.services;

import uz.jl.response.ResponseEntity;

import java.awt.geom.RectangularShape;

/**
 * @author Bakhromjon Sat, 7:48 PM 12/11/2021
 */
public interface EditEntity {
    ResponseEntity<String> block(String username);

    ResponseEntity<String> unBlock(String username);
}
