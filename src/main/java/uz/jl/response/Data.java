package uz.jl.response;

/**
 * @author Elmurodov Javohir, Thu 8:57 AM. 12/9/2021
 */
public class Data<T> {
    private T body;
    private Integer total;

    public Data(T body, Integer total) {
        this.body = body;
        this.total = total;
    }
}
