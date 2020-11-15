package pl.coderslab.vaglogsviewer.services;

import pl.coderslab.vaglogsviewer.entities.User;


public interface UserService {

    User findByUserName(String userName);

    void saveUser(User user);

    boolean checkIsUserExist(User loggingUser);

    void updateUser(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Long id);
}
