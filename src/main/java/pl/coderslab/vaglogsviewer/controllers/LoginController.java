package pl.coderslab.vaglogsviewer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

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
    public String login(User user, HttpSession session) {
        boolean authenticated = userService.checkIsUserExist(user);
            if (authenticated){
                if (session.getAttribute("userName") == null){
                    session.setAttribute("userName", user.getName());
                }
                return "redirect:/user/home";
            }else {
                return "redirect:/login";
            }
    }

}
