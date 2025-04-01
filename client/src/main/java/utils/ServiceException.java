package utils;

public class ServiceException extends RuntimeException {
    public String code;
    public String body;
    public ServiceException(String code, String body) {
        this.code = code;
        this.body = body;
    }
}
