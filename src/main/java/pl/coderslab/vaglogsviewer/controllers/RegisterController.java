package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String register(@ModelAttribute("newUser") @Valid User newUser, BindingResult result) {
        if (result.hasErrors()) {
            return "/landingPage/register";
        }

        userService.saveUser(newUser);
        return "redirect:/register?success";
    }
}
