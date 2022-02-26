package uz.jl.response;

/**
 * @author Elmurodov Javohir, Mon 12:36 PM. 12/6/2021
 */

import lombok.Getter;
import lombok.Setter;
import uz.jl.enums.http.HttpStatus;
import uz.jl.exceptions.APIRuntimeException;

/**
 * @param <T> -> Data Type
 * @param <T> -> Status HTTP Response
 */
@Getter
@Setter
public class ResponseEntity<T> {

    private T data;
    private Integer status;

    public ResponseEntity() {
        this(HttpStatus.HTTP_200);
    }

    public ResponseEntity(HttpStatus status) {
        this.status = status.getCode();
    }

    public ResponseEntity(T data) {
        this(HttpStatus.HTTP_200);
        this.data = data;
    }

    public ResponseEntity(T t, HttpStatus status) {
        this.data = t;
        this.status = status.getCode();
    }

    private void validateStatusCode(Integer code) {
        HttpStatus status = HttpStatus.getStatusByCode(code);
        if (status.equals(HttpStatus.UNDEFINED)) {
            throw new APIRuntimeException("Status Code is invalid", HttpStatus.HTTP_STATUS_CODE_NOT_FOUND.getCode());
        }
    }

}
