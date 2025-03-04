package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import model.GameData;

import java.security.Provider;

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
}
