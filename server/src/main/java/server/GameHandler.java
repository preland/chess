package server;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import service.*;

public class GameHandler {
    public static String ListGamesHandler(String request) {
        return "lol";
    }
    public static String GetGameHandler(String request) {
        return "lol";
    }
    public static String CreateGameHandler(String request) throws ServiceException{
        //System.out.println(authorization);
        var serializer = new Gson();
        CreateGameRequest reg = serializer.fromJson(request, CreateGameRequest.class);
        CreateGameResult result = GameService.createGame(reg);
        //System.out.println(result);
        return serializer.toJson(result);
    }
    public static String ClearDatabaseHandler() throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        try {
            memdb.clear();
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
        return "{}";
    }

}
