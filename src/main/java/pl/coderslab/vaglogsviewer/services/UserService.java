package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.User;


public interface UserService {

    User findByUserName(String userName);

    void saveUser(User user);

    boolean checkIsUserExist(User loggingUser);
}
