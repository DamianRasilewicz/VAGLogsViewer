package pl.coderslab.vaglogsviewer.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.coderslab.vaglogsviewer.entities.Role;
import pl.coderslab.vaglogsviewer.entities.User;

import java.util.List;


public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void saveUser(User user);

    boolean checkIsUserExist(User loggingUser);

    void updateProfile(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Integer id);

    void updateUser(String userName, String email, String password, Boolean enabled, String firstName, String lastName, Integer id);

    User findUserById(Integer id);

    List<User> findAllUsers();

    void deleteById(Integer id);
}
