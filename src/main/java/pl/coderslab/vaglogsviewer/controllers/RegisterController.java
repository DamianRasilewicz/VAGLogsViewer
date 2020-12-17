package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.vaglogsviewer.entities.Role;
import pl.coderslab.vaglogsviewer.entities.User;
import pl.coderslab.vaglogsviewer.services.UserServiceImpl;

import javax.validation.Valid;


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
    public String register(@ModelAttribute("newUser") @Valid User newUser, BindingResult result, Role role) {
        if (result.hasErrors()) {
            return "/landingPage/register";
        }
        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(12)));
        newUser.setEnabled(true);
        role.setId(2);
        role.setName("USER");
        newUser.setRole(role);
        userService.saveUser(newUser);
        return "redirect:/register?success";
    }
}
