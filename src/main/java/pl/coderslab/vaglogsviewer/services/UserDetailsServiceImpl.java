//package pl.coderslab.vaglogsviewer.services;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import pl.coderslab.vaglogsviewer.entities.User;
//import pl.coderslab.vaglogsviewer.repositories.UserRepository;
//
//@Service("userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
//    @Autowired
//    private UserRepository userRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        User user = userRepository.findUserByName(userName);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found.");
//        }
//        log.info("loadUserByUsername() : {}", userName);
//        return new VLVUserDetails(user);
//    }
//}
