package service;

import dataaccess.DataAccessException;
import dataaccess.MemoryDAO;
import dataaccess.SQLDao;
import model.AuthData;
import service.requestresult.*;

public class UserService {
    public static RegisterResult register(RegisterRequest registerRequest) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();

        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        try {
            SQLDao sqldb = new SQLDao();
            sqldb.createUser(username, password, email);
            return new RegisterResult(sqldb.getUser(username).username(), sqldb.createAuth(username, password).authToken());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
        //bad request
        //already taken
        //other error
        //throw new ServiceException("a");
    }
    public static LoginResult login(LoginRequest loginRequest) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();
        String username = loginRequest.username();
        String password = loginRequest.password();
        try {
            SQLDao sqldb = new SQLDao();
            AuthData auth = sqldb.createAuth(username, password);
            return new LoginResult(auth.username(), auth.authToken());
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
    public static void logout(LogoutRequest logoutRequest) throws ServiceException {
        //MemoryDAO memdb = MemoryDAO.getInstance();
        String auth = logoutRequest.authorization();
        try {
            SQLDao sqldb = new SQLDao();
            System.out.println(auth);
            AuthData verify = sqldb.getAuth(auth);
            sqldb.deleteAuth(auth);
        } catch (DataAccessException e) {
            throw new ServiceException(e.code, e.body);
        }
    }
}
