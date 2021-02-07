package ServerUtil.auth;

import UserData.User;

import java.sql.SQLException;

public interface AuthService {
    default void start() throws ClassNotFoundException, SQLException {};

    User getUserByLoginAndPassword(String login, String password);
    void updateUsername(User user, String newUsername);

    default void stop() throws SQLException {};
}
