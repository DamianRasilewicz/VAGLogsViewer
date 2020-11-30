package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;


@Controller
public class RegisterController {

    private final UserServiceImpl userService;

    public RegisterController(UserServiceImpl userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new User());
        return "landingPage/register";
    }

    @PostMapping("/register")
    public String register(User newUser) {
        userService.saveUser(newUser);
        return "redirect:/";
    }
}
