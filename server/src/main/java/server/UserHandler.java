package server;

import com.google.gson.Gson;
import service.*;
import service.requestresult.*;

public class UserHandler {
    public static String registerHandler(String request) throws ServiceException {
        var serializer = new Gson();
        System.out.println(request);
        RegisterRequest reg = serializer.fromJson(request, RegisterRequest.class);
        RegisterResult result = UserService.register(reg);
       //System.out.println(result);
        return serializer.toJson(result);
    }
    public static String loginHandler(String request) throws ServiceException{
        var serializer = new Gson();
        LoginRequest reg = serializer.fromJson(request, LoginRequest.class);
        LoginResult result = UserService.login(reg);
        return serializer.toJson(result);
    }
    public static String logoutHandler(String request) throws ServiceException{
        System.out.println("1 "+request);
        var serializer = new Gson();
        LogoutRequest reg = serializer.fromJson("{ authorization: \"" + request + "\" }", LogoutRequest.class);


        UserService.logout(reg);
        return "{}";
    }
}
