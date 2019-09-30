package Repository;

import java.util.HashMap;
import java.util.Map;

public class LoginsRepositoryImpl implements LoginRepository {
    private static LoginsRepositoryImpl instance;
    private Map<String,String>logins;

    private LoginsRepositoryImpl(){
        logins = new HashMap<>();
    }

    public static LoginsRepositoryImpl getInstance(){
        if(instance==null)
            instance = new LoginsRepositoryImpl();
        return instance;
    }

    @Override
    public Map<String, String> getLogins() {
        return logins;
    }

    @Override
    public String addLogin(String login, String pass) {
        if(isPresentLogin(login)&&isPresentValue(login,pass))
            return "You already registered";
        logins.put(login,pass);
        return "Registered successfully";
    }

    @Override
    public String getPassword(String login) {
        return logins.get(login);
    }

    @Override
    public boolean checkPassword(String login, String pass) {
        if(isPresentLogin(login)){
            String p = logins.get(login);
            return p.equals(pass);
        }
        return false;
    }

    public boolean isPresentLogin(String login){
        return logins.containsKey(login);
    }

    public boolean isPresentValue(String login, String pass){
        if(!isPresentLogin(login))
            return false;
        return logins.get(login).equals(pass);
    }
}
