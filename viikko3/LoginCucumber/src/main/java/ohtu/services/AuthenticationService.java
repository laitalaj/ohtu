package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;
    private static Pattern USERNAME_PATTERN = Pattern.compile("^[a-z]*$");
    private static Pattern PASSWORD_PATTERN = Pattern.compile("^.*[^a-zåäöA-ZÅÄÖ].*$");

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        return username.length() < 3 || password.length() < 8
                || !USERNAME_PATTERN.matcher(username).matches()
                || !PASSWORD_PATTERN.matcher(password).matches();
    }
}
