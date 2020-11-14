package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String landingPage() {
        return "landingPage/index";
    }


}