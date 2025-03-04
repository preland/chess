package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import model.GameData;

import java.security.Provider;
import java.util.List;

public class GameService {
    public static CreateGameResult createGame(CreateGameRequest request) throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        String authorization = request.authorization();
        String gameName = request.gameName();
        try {
            GameData game = memdb.createGame(authorization, gameName);
            return new CreateGameResult(game.gameID());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static JoinGameResult joinGame(JoinGameRequest request) throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        String authorization = request.authorization();
        String playerColor = request.playerColor();
        int gameID = request.gameID();
        try {
            String username = memdb.getAuth(authorization).username();
            memdb.updateGame(authorization, username, playerColor, gameID);
            return new JoinGameResult();
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static ListGamesResult listGames(ListGamesRequest request) throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        String authorization = request.authorization();
        try {
            List<GameData> games = memdb.listGames(authorization);
            return new ListGamesResult(games);
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
}
