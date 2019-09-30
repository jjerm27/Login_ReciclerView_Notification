package Repository;

import java.util.Map;

public interface LoginRepository {
    Map<String,String> getLogins();
    String addLogin(String login, String pass);
    String getPassword (String login);
    boolean checkPassword(String login, String pass);
}
