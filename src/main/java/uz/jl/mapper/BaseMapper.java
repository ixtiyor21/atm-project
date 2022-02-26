package uz.jl.mapper;

/**
 * @author Elmurodov Javohir, Mon 11:14 AM. 12/6/2021
 */

/**
 * @param <E> E-> Entity
 * @param <D> D-> DTO (Data Transfer Object)
 */
public abstract class BaseMapper<E, D> {
    abstract E to(D d);

    abstract D from(E e);
}
