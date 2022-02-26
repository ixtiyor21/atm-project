package uz.jl.services;

import uz.jl.models.BaseEntity;
import uz.jl.response.ResponseEntity;

import java.util.List;

/**
 * @author Elmurodov Javohir, Tue 12:22 PM. 12/7/2021
 */
public interface IBaseCrudService<E extends BaseEntity> {
    ResponseEntity<String> create(E e);

    ResponseEntity<String> delete(String id);
}
