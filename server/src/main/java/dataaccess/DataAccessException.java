package dataaccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{
    public String code, body;
    public DataAccessException(String code, String body) {
        this.code = code;
        this.body = body;
    }
}
