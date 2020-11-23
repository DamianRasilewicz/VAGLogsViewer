package pl.coderslab.vaglogsviewer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsHtmlPages {

    @GetMapping("/header")
    public String getHeader() {
        return "header";
    }

    @GetMapping("/footer")
    public String getFoother() {
        return "footer";
    }
}
