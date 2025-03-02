package service;

public class UserService {
    public static RegisterResult register(RegisterRequest registerRequest) throws ServiceException {
        throw new ServiceException("a");
    }
    public static LoginResult login(LoginRequest loginRequest) throws ServiceException {
        throw new ServiceException("a");
    }
    public static void logout(LogoutRequest logoutRequest) {}
}
