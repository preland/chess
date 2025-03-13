package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import dataaccess.SQLDao;
import model.GameData;
import service.requestresult.*;

import java.util.List;

public class GameService {
    public static CreateGameResult createGame(CreateGameRequest request) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();

        String authorization = request.authorization();
        String gameName = request.gameName();
        try {
            SQLDao sqldb = new SQLDao();
            GameData game = sqldb.createGame(authorization, gameName);
            return new CreateGameResult(game.gameID());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static JoinGameResult joinGame(JoinGameRequest request) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();
        String authorization = request.authorization();
        String playerColor = request.playerColor();
        int gameID = request.gameID();
        try {
            SQLDao sqldb = new SQLDao();
            String username = sqldb.getAuth(authorization).username();
            sqldb.updateGame(authorization, username, playerColor, gameID);
            return new JoinGameResult();
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static ListGamesResult listGames(ListGamesRequest request) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();
        String authorization = request.authorization();
        try {
            SQLDao sqldb = new SQLDao();
            List<GameData> games = sqldb.listGames(authorization);
            return new ListGamesResult(games);
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
}
