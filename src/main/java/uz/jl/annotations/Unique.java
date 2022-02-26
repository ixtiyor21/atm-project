package uz.jl.annotations;

import java.lang.annotation.*;

/**
 * @author Elmurodov Javohir, Mon 12:07 PM. 12/6/2021
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
@Inherited
public @interface Unique {
}
