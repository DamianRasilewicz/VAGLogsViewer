package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {
    @GetMapping("/register")
    public String showRegister() {
        return "landingPage/register";
    }

    @PostMapping("/register")
    public String register() {
        return "redirect:/user/home";
    }
}
