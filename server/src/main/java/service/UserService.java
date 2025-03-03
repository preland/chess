package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;

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
            throw new ServiceException("500", "{\"message\": \"Error: (description of error)\"}");
        }
        //bad request
        //already taken
        //other error
        //throw new ServiceException("a");
    }
    public static LoginResult login(LoginRequest loginRequest) throws ServiceException {
        throw new ServiceException("a","a");
    }
    public static void logout(LogoutRequest logoutRequest) {}
}
