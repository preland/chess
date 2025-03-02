package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import service.*;

public class UserHandler {
    public static String RegisterHandler(String request) throws DataAccessException {
        var serializer = new Gson();
        RegisterRequest reg = serializer.fromJson(request, RegisterRequest.class);
        RegisterResult result = UserService.register(reg);
        return serializer.toJson(result);
    }
    public static String LoginHandler(String request) throws DataAccessException{
        var serializer = new Gson();
        LoginRequest reg = serializer.fromJson(request, LoginRequest.class);
        LoginResult result = UserService.login(reg);
        return serializer.toJson(result);
    }
    public static String LogoutHandler(String request) throws DataAccessException{
        var serializer = new Gson();
        LogoutRequest reg = serializer.fromJson(request, LogoutRequest.class);
        //UserService.logout(reg);
        return "idk what to do here yet";
    }
}
