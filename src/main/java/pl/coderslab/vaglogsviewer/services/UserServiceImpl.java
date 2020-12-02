package pl.coderslab.vaglogsviewer.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.vaglogsviewer.controllers.LoginController;
import pl.coderslab.vaglogsviewer.entities.Role;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.repositories.RoleRepository;
import pl.coderslab.vaglogsviewer.repositories.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService  {


    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findRoleByName("USER");
        user.setRole(userRole);
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
    public void updateProfile(String userName, String userEmail, String userFirstName, String userLastName, String userPassword, Long id) {
        userRepository.updateProfile(userName, userEmail, userFirstName, userLastName, userPassword, id);
    }

    @Override
    public void updateUser(String userName, String email, String password, Boolean enabled, String firstName, String lastName, Long id) {
        userRepository.updateUser(userName, email, password, enabled, firstName, lastName, id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user email or password.");
        }
        logger.error("loadUserByUsername() : {}", userName);
        return new VLVUserDetails(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
