package pl.coderslab.vaglogsviewer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.vaglogsviewer.controllers.LoginController;
import pl.coderslab.vaglogsviewer.entities.Role;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.repositories.RoleRepository;
import pl.coderslab.vaglogsviewer.repositories.UserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {


    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    @Override
    public User findByUserName(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(user.getPassword());
        user.setEnabled(1);
        Role userRole = roleRepository.findRoleByName("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public boolean checkIsUserExist(User loggingUser) {
        String loggingUserName = loggingUser.getName();
        String loggingUserPassword = loggingUser.getPassword();
        boolean Authenticated = false;
        try {
            User userFromBase = findByUserName(loggingUserName);
            if (userFromBase.getName().equals(loggingUserName) && userFromBase.getPassword().equals(loggingUserPassword)){
                Authenticated = true;
            }else {
                Authenticated = false;
            }
        }catch (Exception e){
            logger.error("Inncorect userName or password! Inputed values: " + "userName = " + loggingUserName +
                                                                        " userPassword = " + loggingUserPassword);
        }
        return Authenticated;
    }

    @Override
    public void updateUser(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Long id) {
        userRepository.updateUser(userName, userEmail, userFirstName, userLastName, userPassword, id);
    }
}
