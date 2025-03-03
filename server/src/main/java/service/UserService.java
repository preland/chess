package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import model.AuthData;

public class UserService {
    public static RegisterResult register(RegisterRequest registerRequest) throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        try {
            memdb.createUser(username, password, email);
            return new RegisterResult(memdb.getUser(username).username(), memdb.createAuth(username, password).authToken());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
        //bad request
        //already taken
        //other error
        //throw new ServiceException("a");
    }
    public static LoginResult login(LoginRequest loginRequest) throws ServiceException {
        MemoryDAO memdb = MemoryDAO.getInstance();
        String username = loginRequest.username();
        String password = loginRequest.password();
        try {
            AuthData auth = memdb.createAuth(username, password);
            return new LoginResult(auth.username(), auth.authToken());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static void logout(LogoutRequest logoutRequest) {}
}
