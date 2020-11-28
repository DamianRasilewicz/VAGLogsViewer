package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;
import pl.coderslab.vaglogsviewer.services.VLVUserDetails;

import javax.servlet.http.HttpSession;


@SessionAttributes({"userName"})
@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserServiceImpl userService;

    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "landingPage/login";
    }

    @PostMapping("/login")
    public String login(HttpSession session) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrinciple(authentication.getPrincipal());
        User loggedInUser = ((VLVUserDetails) authentication.getPrincipal()).getUserDetails();

        session.setAttribute("userName", loggedInUser.getName());
        return "redirect:/user/home";
    }

    private void validatePrinciple(Object principal) {
        if (!(principal instanceof VLVUserDetails)) {
            throw new  IllegalArgumentException("Principal can not be null!");
        }
    }

}
